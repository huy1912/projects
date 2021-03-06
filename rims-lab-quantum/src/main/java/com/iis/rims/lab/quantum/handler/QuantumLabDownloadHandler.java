package com.iis.rims.lab.quantum.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.log4j.Logger;

import com.iis.rims.common.RIMSConstants.LabOrderDetailType;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.RIMSConstants.LabResult;
import com.iis.rims.common.RIMSConstants.OrderReportStatus;
import com.iis.rims.common.RIMSConstants.ResultInput;
import com.iis.rims.domain.LabOrder;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.domain.LabRTTestCodeNote;
import com.iis.rims.hibernate.dao.LabOrderDAO;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.hibernate.dao.LabRTTestCodeNoteDAO;
import com.iis.rims.hibernate.dao.LabRtTestCodeDAO;
import com.iis.rims.hibernate.dao.LabTestCodeDAO;
import com.iis.rims.lab.quantum.message.DecodeMessage;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest.Observation.OBX;

public class QuantumLabDownloadHandler {
	private static final Logger LOGGER = Logger.getLogger(QuantumLabDownloadHandler.class);
	private static final Map<String, String> POST_TEST_CODE_MAPPING = new HashMap<String, String>();
	private static final String PDF_REPORT_FOLDER = "pdf";
	private static final Integer LAB_USER_ID = null;

	static {
		POST_TEST_CODE_MAPPING.put("CRE", "CREPOST");
	}

	public static void processResults(MSG msg, String messageText, String internalOrderNumber,
			String orderFile, String pdfFile, byte[] pdfContent) {
		try {
			LabOrderDAO labOrderDAO = new LabOrderDAO();
			LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
			LabTestCodeDAO labTestCodeDAO = new LabTestCodeDAO();
			LabRtTestCodeDAO labRtTestCodeDAO = new LabRtTestCodeDAO();
			LabRTTestCodeNoteDAO labRTTestCodeNoteDAO = new LabRTTestCodeNoteDAO();
			MutableObject<LabOrderDetailType> detailTypeObject = new MutableObject<LabOrderDetailType>();

			List<String> processPdfFiles = new ArrayList<String>();
			List<LabProcessResult> successfulList = new ArrayList<LabProcessResult>();
			List<LabProcessResult> missingErrorCodeList = new ArrayList<LabProcessResult>();
			List<LabProcessResult> cannotProcessList = new ArrayList<LabProcessResult>();

			LOGGER.info("Process lab order " + msg.getORC().getORCPlacerOrderNumber());
			String nricFinNumber = msg.getPID().getPatientIdInt();
			Date currentDate = new Date();
			for (ObservationRequest observationRequest : msg.getObservationRequest()) {
				String accessionNumber = observationRequest.getOBR().getOBRPlacerOrderNumber();
				Map<String, String> testCodeResults = new LinkedHashMap<String, String>();
				Map<String, String> testCodeNotes = new LinkedHashMap<String, String>();
				LabOrderDetail labOrderDetail = labOrderDetailDAO.getLabOrderDetail(internalOrderNumber, accessionNumber);
				LabOrder labOrder = labOrderDAO.findById(labOrderDetail.getLabOrderId());
				String labNricFinNumber = labOrder.getNricFinNumber().replaceAll("-", "");
				if (labOrder != null && labNricFinNumber.equals(nricFinNumber)) {
					int organizationId = labOrder.getOrganizationId();
					// Insert into the result.
					for (OBX obx : observationRequest.getObservation().getOBX()) {
						String[] oi = obx.getObservationIdentifier().split("\\^");
						String testCode = oi[0];
						// FIXME review later
						if (detailTypeObject.getValue() == LabOrderDetailType.POST) {
							if (POST_TEST_CODE_MAPPING.containsKey(testCode)) {
								testCode = POST_TEST_CODE_MAPPING.get(testCode);
							}
						}
						String testName = oi[1];
						String application = "";
						String value = obx.getObservationValue();
						String unit = obx.getUnits() == null ? "" : obx.getUnits();
						String referenceRange = obx.getReferenceRange();
						String abnormalFlag = obx.getIsAbnormalflag() == null ? "" : obx.getIsAbnormalflag();
						String resultStatus = obx.getObservationResultsStatus() == null ? "" : obx.getObservationResultsStatus();
						String observationIdentifier = StringUtils.join(Arrays.asList(testCode, testName, application), ',');
						String[] values = { value, unit, referenceRange, abnormalFlag, resultStatus };
						String insertValue = StringUtils.join(values, '|');
						testCodeResults.put(testCode, insertValue);
						if (!StringUtils.isEmpty(obx.getMedicalRemarks())) {
							testCodeNotes.put(testCode, obx.getMedicalRemarks());
						}
					}
					if (!testCodeResults.isEmpty()) {
						boolean okProcessed = true;
						Set<String> testCodeKeys = testCodeResults.keySet();
						// The mapping will return 1 to 1 mapping between
						// test code and RT test code.
						List<List<String>> rtTestCodeMapping = labTestCodeDAO.getRtTestCodes(organizationId,
								labOrderDetail.getLabCustomerId(), testCodeKeys);
						List<String> insertTestCodes = rtTestCodeMapping.get(1);
						List<String> testCodeValues = new ArrayList<String>();
						for (String testCodeKey : insertTestCodes) {
							testCodeValues.add(testCodeResults.get(testCodeKey));
						}

						Collection<String> missingTestCodes = CollectionUtils.subtract(testCodeKeys, insertTestCodes);
						if (!missingTestCodes.isEmpty()) {
							String missingTestCodesValue = StringUtils.join(missingTestCodes, ", ");
							LOGGER.error(String.format("Missing test code %s", missingTestCodesValue));
							labOrderDetail.setErrorMessage(missingTestCodesValue);
							labOrderDetail.setOrderHl7status(LabResult.FAILED.ordinal());

							// Don't need to create the failed order file
							// again if the file already existed.
							if (labOrderDetail.getFailedOrderPath() == null) {
//								String failedFileName = orderFile;
//								String failedOrderPath = String.format("%s/%s", FAIL_ORDER_FOLDER, failedFileName);
								labOrderDetail.setFailedOrderPath(orderFile);
							}
							okProcessed = false;
						}
						else {
							List<String> rtTestCodes = rtTestCodeMapping.get(0);
							int labRtTestCodeId = labRtTestCodeDAO.updateLabResult(organizationId,
									labOrder.getBranchId(), labOrderDetail.getLabCustomerId(),
									labOrderDetail.getLabOrderDetailId(), labOrder.getPatientId(),
									labOrder.getNricFinNumber(), rtTestCodes, testCodeValues);
							for (Entry<String, String> entry : testCodeNotes.entrySet()) {
								String testCode = entry.getKey();
								int testCodeIndex = insertTestCodes.indexOf(testCode);
								if (testCodeIndex != -1) {
									String rtTestCode = rtTestCodes.get(testCodeIndex);
									LabRTTestCodeNote note = labRTTestCodeNoteDAO.getNote(labRtTestCodeId, rtTestCode);
									if (note == null) {
										note = new LabRTTestCodeNote();
										note.setCreatedBy(LAB_USER_ID);
										note.setCreatedDate(currentDate);
										note.setRtTestCodeColumn(rtTestCode);
										note.setLabRtTestCodeId(labRtTestCodeId);
									}
									note.setEntityStatus(true);
									note.setLastUpdatedBy(LAB_USER_ID);
									note.setLastUpdatedDate(currentDate);
									String noteValue = entry.getValue();
									noteValue = noteValue.replace("\\.br\\", "\r\n");
									note.setNote(noteValue);
									labRTTestCodeNoteDAO.saveOrUpdate(note);
								}
							}
							labOrderDetail.setErrorMessage(null);
							labOrderDetail.setOrderHl7status(LabResult.DONE.ordinal());
							labOrderDetail.setOrderStatus(LabOrderStatus.PS.ordinal());
						}
						// Update the pdf report if it's existed.
						if (pdfContent != null) {
							updateOrderReport(labOrderDetail, pdfContent);
							processPdfFiles.add(pdfFile);
						}
						labOrderDetail.setResultInput(ResultInput.LAB_AUTOMATION.ordinal());
						labOrderDetail.setLastUpdatedBy(LAB_USER_ID);
						labOrderDetail.setLastUpdatedDate(currentDate);
						labOrderDetail.setLabResultDate(currentDate); // FIXME visit later
						labOrderDetail.setOrderHl7message(messageText);
						labOrderDetailDAO.update(labOrderDetail);
						if (okProcessed) {
							successfulList.add(new LabProcessResult().build(internalOrderNumber,
									labOrderDetail.getOrderNumberRef(), orderFile, pdfFile));
						} else {
							missingErrorCodeList.add(new LabProcessResult().build(internalOrderNumber,
									labOrderDetail.getOrderNumberRef(), orderFile, pdfFile));
						}

					}
				} else {
					LOGGER.error(String.format("Invalid lab result %s. Please contact the lab.", internalOrderNumber));
					cannotProcessList.add(new LabProcessResult().buildOruFileName(internalOrderNumber));
				}
			}

			// Send email notification
			Map<String, Object> mergeObject = new HashMap<String, Object>();
			mergeObject.put("successfulList", successfulList);
			mergeObject.put("missingErrorCodeList", missingErrorCodeList);
			mergeObject.put("cannotProcessList", cannotProcessList);
			// sendEmailNotification(mergeObject);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private static void updateOrderReport(LabOrderDetail labOrderDetail, byte[] pdfContent) {
		labOrderDetail.setOrderReportAttached(pdfContent);
		labOrderDetail.setOrderReportStatus(OrderReportStatus.RECEIVED.ordinal());
		
//		File moveDir = new File(localPdfDir + "/" + ARCHIVED_ORDER_FOLDER);
//		FileUtils.copyFileToDirectory(pdfFile, moveDir);
//		FileUtils.forceDelete(pdfFile);
//		return true;
	}
}
