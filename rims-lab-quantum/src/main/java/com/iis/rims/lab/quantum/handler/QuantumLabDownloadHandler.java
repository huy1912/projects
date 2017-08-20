package com.iis.rims.lab.quantum.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.log4j.Logger;

import com.iis.rims.common.RIMSConstants.LabOrderDetailType;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.RIMSConstants.LabResult;
import com.iis.rims.common.RIMSConstants.ResultInput;
import com.iis.rims.domain.LabOrder;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.hibernate.dao.LabOrderDAO;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.hibernate.dao.LabRtTestCodeDAO;
import com.iis.rims.hibernate.dao.LabTestCodeDAO;
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

	protected void processResults(MSG msg, String messageText) {
		try {
			LabOrderDAO labOrderDAO = new LabOrderDAO();
			LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
			LabTestCodeDAO labTestCodeDAO = new LabTestCodeDAO();
			LabRtTestCodeDAO labRtTestCodeDAO = new LabRtTestCodeDAO();
			MutableObject<LabOrderDetailType> detailTypeObject = new MutableObject<LabOrderDetailType>();

			List<String> processPdfFiles = new ArrayList<String>();
			List<LabProcessResult> successfulList = new ArrayList<LabProcessResult>();
			List<LabProcessResult> missingErrorCodeList = new ArrayList<LabProcessResult>();
			List<LabProcessResult> cannotProcessList = new ArrayList<LabProcessResult>();

			LOGGER.info("Process lab order " + msg.getORC().getORCPlacerOrderNumber());
			String nricFinNumber = msg.getPID().getPatientIdInt();
			Date currentDate = new Date();
			for (ObservationRequest observationRequest : msg.getObservationRequest()) {
				String orderNumber = observationRequest.getOBR().getOBRPlacerOrderNumber();
				Map<String, String> testCodeResults = new LinkedHashMap<String, String>();
				Map<String, String> testCodeNotes = new LinkedHashMap<String, String>();
				LabOrderDetail labOrderDetail = labOrderDetailDAO.getLabOrderDetailByOrderNumber(orderNumber);
				LabOrder labOrder = labOrderDAO.findById(labOrderDetail.getLabOrderId());
				if (labOrder != null && labOrder.getNricFinNumber().equals(nricFinNumber)) {
					int organizationId = labOrder.getOrganizationId();
					// Insert into the result.
					for (OBX obx : observationRequest.getObservation().getOBX()) {
						String[] oi = obx.getObservationIdentifier().split("^");
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
					}
					if (!testCodeResults.isEmpty()) {
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
								String failedFileName = file; // String.format("%s.%s",
																// file,
																// DateTimeFormatUtils.toDate(currentDate));
								String failedOrderPath = String.format("%s/%s", FAIL_ORDER_FOLDER, failedFileName);
								labOrderDetail.setFailedOrderPath(failedOrderPath);
							}
							failedOrderFile = orderFile;
						}
						else {
							List<String> rtTestCodes = rtTestCodeMapping.get(0);
							int labRtTestCodeId = labRtTestCodeDAO.updateLabResult(organizationId,
									labOrder.getBranchId(), labOrderDetail.getLabCustomerId(),
									labOrderDetail.getLabOrderDetailId(), labOrder.getPatientId(),
									labOrder.getNricFinNumber(), rtTestCodes, testCodeValues);
							labOrderDetail.setErrorMessage(null);
							labOrderDetail.setOrderHl7status(LabResult.DONE.ordinal());
							labOrderDetail.setOrderStatus(LabOrderStatus.PS.ordinal());
						}
						// Update the pdf report if it's existed.
						String pdfFile = getPdfReportFile(orderNumberRef, nricFinNumber);
						boolean pdfProcessed = updateOrderReport(labOrderDetail, pdfFile);
						if (pdfProcessed) {
							processPdfFiles.add(pdfFile);
						} else {
							pdfFile = "";
						}
						labOrderDetail.setResultInput(ResultInput.LAB_AUTOMATION.ordinal());
						labOrderDetail.setLastUpdatedBy(LAB_USER_ID);
						labOrderDetail.setLastUpdatedDate(currentDate);
						labOrderDetail.setLabResultDate(labResultDate);
						labOrderDetail.setOrderHl7message(messageText);
						labOrderDetailDAO.update(labOrderDetail);
						if (failedOrderFile == null) {
							successfulList.add(new LabProcessResult().build(labOrderDetail.getLabOrderNumber(),
									labOrderDetail.getOrderNumberRef(), file, pdfFile));
						} else {
							missingErrorCodeList.add(new LabProcessResult().build(labOrderDetail.getLabOrderNumber(),
									labOrderDetail.getOrderNumberRef(), file, pdfFile));
						}

					}
				} else {
					LOGGER.error(String.format("Invalid result HL7 lab order %s. Please contact the lab.", file));
					cannotProcessList.add(new LabProcessResult().buildOruFileName(file));
					failedOrderFile = orderFile;
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

	private boolean isPdfReport(String file) {
		String reportFile = file.toLowerCase();
		return reportFile.lastIndexOf(".pdf") == reportFile.length() - 4;
	}

	private String getPdfReportFile(String orderNumberRef, String nricFinNumber) {
		String orderNumber = orderNumberRef.replace("/", "_");
		return String.format("%s-%s.PDF", orderNumber, nricFinNumber);
	}
}
