package com.iis.rims.lab.quantum.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.iis.rims.common.DateTimeFormatUtils;
import com.iis.rims.common.RIMSConstants.LabOrderDetailType;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.RIMSConstants.LabResult;
import com.iis.rims.common.RIMSConstants.OrderReportStatus;
import com.iis.rims.common.RIMSConstants.ResultInput;
import com.iis.rims.domain.LabOrder;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.domain.LabProcessResult;
import com.iis.rims.hibernate.dao.LabOrderDAO;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.MSG.ORC;

	
public class QuantumLabDownloadHandlerBackup {
//	private static final Logger LOGGER = Logger.getLogger(QuantumLabDownloadHandlerBackup.class);
//	private static final Map<String, String> POST_TEST_CODE_MAPPING = new HashMap<String, String>();
//	private static final String PDF_REPORT_FOLDER = "pdf";
//	private static final Integer LAB_USER_ID = null;
//	
//	static {
//		POST_TEST_CODE_MAPPING.put("CRE", "CREPOST");
//	}
//	protected void processResults(MSG msg, String messageText) {
//		try {
//			LabOrderDAO labOrderDAO = new LabOrderDAO();
//			LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
//			LabTestCodeDAO labTestCodeDAO = new LabTestCodeDAO();
//			LabRtTestCodeDAO labRtTestCodeDAO = new LabRtTestCodeDAO();
//			MutableObject<LabOrderDetailType> detailTypeObject = new MutableObject<LabOrderDetailType>();
//			
//			List<String> processPdfFiles = new ArrayList<String>();
//			List<LabProcessResult> successfulList = new ArrayList<LabProcessResult>();
//			List<LabProcessResult> missingErrorCodeList = new ArrayList<LabProcessResult>();
//			List<LabProcessResult> cannotProcessList = new ArrayList<LabProcessResult>();
//			
//			LOGGER.info("Process lab order " + msg.getORC().getORCPlacerOrderNumber());
//			for (LabOrder labOrder : labOrders) {
//				File orderFile = null;
//				File failedOrderFile = null;
//				if (isPdfReport(file)) {
//					if (!processPdfFiles.contains(file)) {
//						int pos = file.indexOf("-");
//						if (pos != -1) {
//							String nricFinNumber = file.substring(pos + 1, file.length() - 4);
//							int secondPos = nricFinNumber.indexOf("-");
//							if (secondPos != -1) {
//								nricFinNumber = nricFinNumber.substring(0, secondPos);
//							}
//							String orderNumberRef = file.substring(0, pos);
//							orderNumberRef = orderNumberRef.replace("_", "/");
//							LabOrderDetail labOrderDetail = labOrderDetailDAO.getLabOrderDetailByOrderNumberRef(orderNumberRef);
//							LabOrder labOrder = null;
//							if (labOrderDetail != null) {
//								labOrder = labOrderDAO.findById(labOrderDetail.getLabOrderId());
//							}
//							if (labOrder != null && labOrder.getNricFinNumber().equals(nricFinNumber)) {
//								boolean ret = updateOrderReport(labOrderDetail, file);
//								if (ret) {
//									labOrderDetail.setLastUpdatedBy(LAB_USER_ID);
//									labOrderDetail.setLastUpdatedDate(new Date());
//									labOrderDetailDAO.update(labOrderDetail);
//									successfulList.add(new LabProcessResult().build(labOrderDetail.getLabOrderNumber(), 
//											labOrderDetail.getOrderNumberRef(), "", file));
//								}
//								else {
//									cannotProcessList.add(new LabProcessResult().buildPdfFileName(file));
//								}
//							}
//							else {
//								LOGGER.error(String.format("Invalid result lab report order %s. Please contact the lab.", file));
//								cannotProcessList.add(new LabProcessResult().buildPdfFileName(file));
//							}
//						}
//					}
//					continue;
//				}
//				else {
//					String nricFinNumber = msg.getPID().getPatientIdInt();
//					for (ORC order : msg.getObservationRequest()) {
//						boolean unlinked = false;
//						Map<String, String> testCodeResults = new LinkedHashMap<String, String>();
//						Map<String, String> testCodeNotes = new LinkedHashMap<String, String>();
//						String orderNumber = order.getORCPlacerOrderNumber();
//						String orderNumberRef = order.getFillerOrderNumber();
//						int pos = orderNumberRef.indexOf('^');
//						if (pos != -1) {
//							orderNumberRef = orderNumberRef.substring(0, pos); 
//						}
//						String orderNumber = LabAdapter.getOrderNumber(placerOrderNumber, detailTypeObject);
//						int detailType = detailTypeObject.getValue() != null ? detailTypeObject.getValue().ordinal() : 0;
//						LabOrderDetail labOrderDetail;
//						String observationDateTime = order.getObservationRequest().getObservationDatetime();
//						Date labResultDate = null;
//						if (!StringUtils.isEmpty(observationDateTime)) {
//							// yyyyMMdd
//							if (observationDateTime.length() == 8) {
//								labResultDate = DateTimeFormatUtils.formatDateTime(observationDateTime, "yyyyMMdd");
//							}
//							else if (observationDateTime.length() == 12) {
//								labResultDate = DateTimeFormatUtils.formatDateTime(observationDateTime, "yyyyMMddHHmm");
//							}
//						}
//						// Use the order number if the type is NORMAL, otherwise accession number.
//						// The ORM check is only for backward compatible.
//						if (orderNumber.startsWith("ORM")) {
//							if (detailTypeObject.getValue() == LabOrderDetailType.NORMAL) {
//								labOrderDetail = labOrderDetailDAO.getLabOrderDetailByOrderNumber(orderNumber);
//							}
//							else {
//								labOrderDetail = labOrderDetailDAO.getLabOrderDetail(orderNumber, detailType);
//							}
//						}
//						else {
//							labOrderDetail = labOrderDetailDAO.getLabOrderDetailByOrderNumberRef(orderNumberRef);
//						}
//						
//						LabOrder labOrder = null;
//						Date currentDate = new Date();
//						if (labOrderDetail != null) {
//							labOrder = labOrderDAO.findById(labOrderDetail.getLabOrderId());
//						}
//						// Try to create the new lab order detail whose lab order date is similar to message lab order date.
//						else {
//							labOrder = labOrderDAO.getLabOrder(labResultDate, nricFinNumber);
//							// Create the new lab order detail.
//							// TODO Can we override the existed unlink order detail? Need to check with Thiru.
//							if (labOrder != null) {
//								unlinked = true;
//								labOrderDetail = labOrderDetailDAO.getLabOrderDetailByOrderNumberRef(orderNumberRef); //new LabOrderDetail();
//								if (labOrderDetail == null) {
//									labOrderDetail = new LabOrderDetail();
//									labOrderDetail.setLabOrderId(labOrder.getLabOrderId());
//									labOrderDetail.setLabCustomerId(labOrder.getLabCustomerId());
//									labOrderDetail.setLabPackageId(0);
//									labOrderDetail.setLabPackageName("");
//									labOrderDetail.setAccessionNumber("NA");
//									labOrderDetail.setProcedureId(0);
//									labOrderDetail.setProcedureName("NA");
//									labOrderDetail.setNetAmount(BigDecimal.ZERO);
//									labOrderDetail.setNetAmountSign(1);
//									labOrderDetail.setEntityStatus(true);
//									labOrderDetail.setCreatedBy(LAB_USER_ID);
//									labOrderDetail.setCreatedDate(currentDate);
//									labOrderDetail.setLastUpdatedBy(LAB_USER_ID);
//									labOrderDetail.setLastUpdatedDate(currentDate);
//									labOrderDetailDAO.save(labOrderDetail);
//								}
//							}
//						}
//						if (labOrder != null && labOrder.getNricFinNumber().equals(nricFinNumber)) {
//							int organizationId = labOrder.getOrganizationId();
//							// Insert into the result.
//							for (ObservationResult result : order.getObservationResult()) {
//								CodedElementIdentifier oi = result.getObservationIdentifier();
//								String testCode = oi.getIdentifier();
//								if (detailTypeObject.getValue() == LabOrderDetailType.POST) {
//									if (POST_TEST_CODE_MAPPING.containsKey(testCode)) {
//										testCode = POST_TEST_CODE_MAPPING.get(testCode);
//									}
//								}
//								String testName = oi.getText();
//								String application = oi.getNameOfCodingSystem();
//								String value = result.getObservationValue();
//								String unit = result.getUnits() == null ? "" : result.getUnits();
//								String referenceRange = result.getReferencesRange();
//								String abnormalFlag = result.getAbnormalFlags() == null ? "" : result.getAbnormalFlags();
//								String resultStatus = result.getObservationResultStatus() == null ? "" : result.getObservationResultStatus();
//								String observationIdentifier = StringUtils.join(Arrays.asList(testCode, testName, application), ',');
//								String[] values = {value, unit, referenceRange, abnormalFlag, resultStatus};
//								String insertValue = StringUtils.join(values, '|');
//								testCodeResults.put(testCode, insertValue);
//								if (result.getNoteAndComments() != null && !result.getNoteAndComments().isEmpty()) {
//									List<String> noteList = new ArrayList<String>();
//									for (NoteAndComment noteAndComment : result.getNoteAndComments()) {
//										noteList.add(noteAndComment.getComment());
//									}
//									testCodeNotes.put(testCode, StringUtils.join(noteList, "|"));
//								}
//							}
//							if (!testCodeResults.isEmpty()) {
//								Set<String> testCodeKeys = testCodeResults.keySet();
//								// The mapping will return 1 to 1 mapping between test code and RT test code.
//								List<List<String>> rtTestCodeMapping = labTestCodeDAO.getRtTestCodes(organizationId, labOrderDetail.getLabCustomerId(), testCodeKeys);
//								List<String> insertTestCodes = rtTestCodeMapping.get(1);
//								List<String> testCodeValues = new ArrayList<String>();
//								for (String testCodeKey : insertTestCodes) {
//									testCodeValues.add(testCodeResults.get(testCodeKey));
//								}
//								
//								if (labResultDate == null) {
//									labResultDate = currentDate;
//								}
//								Collection<String> missingTestCodes = CollectionUtils.subtract(testCodeKeys, insertTestCodes);
//								if (!missingTestCodes.isEmpty()) {
//									String missingTestCodesValue = StringUtils.join(missingTestCodes, ", ");
//									LOGGER.error(String.format("Missing test code %s", missingTestCodesValue));
//									labOrderDetail.setErrorMessage(missingTestCodesValue);
//									labOrderDetail.setOrderHl7status(LabResult.FAILED.ordinal());
//									
//									// Don't need to create the failed order file again if the file already existed.
//									if (labOrderDetail.getFailedOrderPath() == null) {
//										String failedFileName = file; //String.format("%s.%s", file, DateTimeFormatUtils.toDate(currentDate));
//										String failedOrderPath = String.format("%s/%s", FAIL_ORDER_FOLDER, failedFileName);
//										labOrderDetail.setFailedOrderPath(failedOrderPath);
//									}
//									failedOrderFile = orderFile;
//								}
//								else {
//									List<String> rtTestCodes = rtTestCodeMapping.get(0);
//									int labRtTestCodeId = labRtTestCodeDAO.updateLabResult(organizationId, labOrder.getBranchId(), labOrderDetail.getLabCustomerId(), 
//											labOrderDetail.getLabOrderDetailId(), labOrder.getPatientId(), labOrder.getNricFinNumber(), 
//											rtTestCodes, testCodeValues);
//									// Update the note if present.
//									for (Entry<String, String> entry : testCodeNotes.entrySet()) {
//										String testCode = entry.getKey();
//										int testCodeIndex = insertTestCodes.indexOf(testCode);
//										if (testCodeIndex != -1) {
//											String rtTestCode = rtTestCodes.get(testCodeIndex);
//											LabRTTestCodeNote note = labRTTestCodeNoteDAO.getNote(labRtTestCodeId, rtTestCode);
//											if (note == null) {
//												note = new LabRTTestCodeNote();
//												note.setCreatedBy(LAB_USER_ID);
//												note.setCreatedDate(currentDate);
//												note.setRtTestCodeColumn(rtTestCode);
//												note.setLabRtTestCodeId(labRtTestCodeId);
//											}
//											note.setEntityStatus(true);
//											note.setLastUpdatedBy(LAB_USER_ID);
//											note.setLastUpdatedDate(currentDate);
//											String noteValue = entry.getValue();
//											noteValue = noteValue.replace("\\.br\\", "\r\n");
//											note.setNote(noteValue);
//											labRTTestCodeNoteDAO.saveOrUpdate(note);
//										}
//									}
//									labOrderDetail.setErrorMessage(null);
//									labOrderDetail.setOrderHl7status(LabResult.DONE.ordinal());
//									labOrderDetail.setOrderStatus(LabOrderStatus.PS.ordinal());
//								}
//								if (unlinked) {
//									labOrderDetail.setOrderStatus(LabOrderStatus.UL.ordinal());
//								}
//								// Update the pdf report if it's existed.
//								String pdfFile = getPdfReportFile(orderNumberRef, nricFinNumber);
//								boolean pdfProcessed = updateOrderReport(labOrderDetail, pdfFile);
//								if (pdfProcessed) {
//									processPdfFiles.add(pdfFile);
//								}
//								else {
//									pdfFile = "";
//								}
//								labOrderDetail.setResultInput(ResultInput.LAB_AUTOMATION.ordinal());
//								labOrderDetail.setLastUpdatedBy(LAB_USER_ID);
//								labOrderDetail.setLastUpdatedDate(currentDate);
//								labOrderDetail.setLabResultDate(labResultDate);
//								labOrderDetail.setOrderNumberRef(orderNumberRef);
//								labOrderDetail.setOrderHl7message(messageText);
//								labOrderDetailDAO.update(labOrderDetail);
//								if (failedOrderFile == null) {
//									successfulList.add(new LabProcessResult().build(labOrderDetail.getLabOrderNumber(), 
//											labOrderDetail.getOrderNumberRef(), file, pdfFile));
//								}
//								else {
//									missingErrorCodeList.add(new LabProcessResult().build(labOrderDetail.getLabOrderNumber(), 
//											labOrderDetail.getOrderNumberRef(), file, pdfFile));
//								}
//								
//							}
//						}
//						else {
//							LOGGER.error(String.format("Invalid result HL7 lab order %s. Please contact the lab.", file));
//							cannotProcessList.add(new LabProcessResult().buildOruFileName(file));
//							failedOrderFile = orderFile;
//						}
//					}
////				}
//					
////				File moveDir = null;
////				if (failedOrderFile != null) {
////					moveDir = failedFolderFile;
////				}
////				else {
////					moveDir = archivedFolderFile;
////				}
////				FileUtils.copyFileToDirectory(orderFile, moveDir);
////				FileUtils.forceDelete(orderFile);
////			}
//			
//			// Send email notification
//			Map<String,Object> mergeObject = new HashMap<String,Object>();
//			mergeObject.put("successfulList", successfulList);
//			mergeObject.put("missingErrorCodeList", missingErrorCodeList);
//			mergeObject.put("cannotProcessList", cannotProcessList);
//			sendEmailNotification(mergeObject);
//		}
//		catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//		}
//	}
//	
//	private boolean isPdfReport(String file) {
//		String reportFile = file.toLowerCase();
//		return reportFile.lastIndexOf(".pdf") == reportFile.length() - 4;
//	}
//	
//	private String getPdfReportFile(String orderNumberRef, String nricFinNumber) {
//		String orderNumber = orderNumberRef.replace("/", "_");
//		return String.format("%s-%s.PDF", orderNumber, nricFinNumber);
//	}
}
