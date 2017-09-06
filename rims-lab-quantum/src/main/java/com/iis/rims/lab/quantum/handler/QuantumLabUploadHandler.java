package com.iis.rims.lab.quantum.handler;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import com.iis.rims.common.DateTimeFormatUtils;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.RIMSConstants.RunningNumber;
import com.iis.rims.domain.Address;
import com.iis.rims.domain.Branch;
import com.iis.rims.domain.ContactInfo;
import com.iis.rims.domain.Doctor;
import com.iis.rims.domain.LabOrder;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.domain.LabProfile;
import com.iis.rims.domain.LookupDetail;
import com.iis.rims.domain.LookupHeader;
import com.iis.rims.domain.Patient;
import com.iis.rims.domain.SystemUser;
import com.iis.rims.domain.Visit;
import com.iis.rims.domain.dto.RunningNumberDTO;
import com.iis.rims.hibernate.dao.AddressDAO;
import com.iis.rims.hibernate.dao.BranchDAO;
import com.iis.rims.hibernate.dao.ContactInfoDAO;
import com.iis.rims.hibernate.dao.DoctorDAO;
import com.iis.rims.hibernate.dao.LabOrderDAO;
import com.iis.rims.hibernate.dao.LabProfileDAO;
import com.iis.rims.hibernate.dao.LookupDetailDAO;
import com.iis.rims.hibernate.dao.LookupHeaderDAO;
import com.iis.rims.hibernate.dao.PatientDAO;
import com.iis.rims.hibernate.dao.RunningNumberDAO;
import com.iis.rims.hibernate.dao.SystemUserDAO;
import com.iis.rims.hibernate.dao.VisitDAO;
import com.iis.rims.lab.quantum.exception.LabOrderException;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.MSG.EVN;
import com.iis.rims.lab.quantum.orm.MSG.MSH;
import com.iis.rims.lab.quantum.orm.MSG.ORC;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest.OBR;
import com.iis.rims.lab.quantum.orm.MSG.PID;
import com.iis.rims.lab.quantum.orm.MSG.PV1;

public class QuantumLabUploadHandler {
	private static final String ORDER_MESSAGE_TYPE = "ORM";
	private static final String RECEIVING_APPLICATION_NAME = "LIS";
	private static final String RECEIVING_APPLICATION_FACILITY = "LIS";
	private static final String SENDING_APPLICATION_NAME = "MYRENALTEAM";
	private static final String SENDING_APPLICATION_FACILITY = "MYRENALTEAM";
	private static final FastDateFormat LAB_DATE_TIME_FORMATTER = FastDateFormat.getInstance("yyyyMMddHHmmss");
	
	private static final BranchDAO BRANCH_DAO = new BranchDAO();
	private static final PatientDAO PATIENT_DAO = new PatientDAO();
	private static final AddressDAO ADDRESS_DAO = new AddressDAO();
	private static final ContactInfoDAO CONTACT_INFO_DAO = new ContactInfoDAO();
	private static final SystemUserDAO SYSTEM_USER_DAO = new SystemUserDAO();
	private static final LabOrderDAO LAB_ORDER_DAO = new LabOrderDAO();
	private static final LabProfileDAO LAB_PROFILE_DAO = new LabProfileDAO();
//	private static final CountryDAO COUNTRY_DAO = new CountryDAO();
	private static final VisitDAO VISIT_DAO = new VisitDAO();
//	private static final RegistrationDAO REGISTRATION_DAO = new RegistrationDAO();
	private static final DoctorDAO DOCTOR_DAO = new DoctorDAO();
	private static final LookupHeaderDAO LOOKUP_HEADER_DAO = new LookupHeaderDAO();
	private static final LookupDetailDAO LOOKUP_DETAIL_DAO = new LookupDetailDAO();
	
	// Formater
	private static final DecimalFormat PATIENT_ID_INT_FORMATTER = new DecimalFormat("00000000");
	
	public static MSG convertToOrderMessage(Integer labOrderId, LabOrderDetail labOrderDetail, 
			LabOrderStatus labOrderStatus) throws LabOrderException {
		return convertToOrderMessage(labOrderId, Arrays.asList(labOrderDetail), labOrderStatus);
	}
	/**
	 * @param labOrderDetailId
	 * @return
	 * TODO: At the moment, only supports for V2.3.1
	 */
	public static MSG convertToOrderMessage(Integer labOrderId, List<LabOrderDetail> labOrderDetails, 
			LabOrderStatus labOrderStatus) throws LabOrderException	 {
		if (!labOrderDetails.isEmpty()) {
			LabOrder labOrder = LAB_ORDER_DAO.findById(labOrderId);
			Patient patient = PATIENT_DAO.findById(labOrder.getPatientId());
			Branch branch = BRANCH_DAO.findById(labOrder.getBranchId());
			Visit visit = VISIT_DAO.findById(labOrder.getVisitId());
			Doctor doctor = DOCTOR_DAO.findById(branch.getDefaultDoctorId());
			SystemUser systemUser = SYSTEM_USER_DAO.findById(branch.getDefaultDoctorId());
			MSG orderMessage = new MSG();
			Date today = new Date();
			String labOrderNumber = null;
			for (LabOrderDetail detail : labOrderDetails) {
				if (!StringUtils.isEmpty(detail.getLabOrderNumber())) {
					labOrderNumber = detail.getLabOrderNumber();
					break;
				}
			}
			String dateTime = DateTimeFormatUtils.toDateTime(today, LAB_DATE_TIME_FORMATTER);
			MSH messageHeader = createMessageHeader(patient.getOrganizationId(), visit, today, labOrderNumber);
			orderMessage.setMSH(messageHeader);
			orderMessage.setEVN(createEventType(dateTime));
			orderMessage.setPID(createPatient(patient));
			orderMessage.setPV1(createVisit(branch, visit, doctor, systemUser, dateTime));
			orderMessage.setORC(createOrder(branch, labOrder, labOrderDetails, labOrderStatus, today, messageHeader));
			orderMessage.getObservationRequest().add(createRequest(branch, labOrderDetails, dateTime));
			return orderMessage;
		}
		return null;
	}
	
	private static MSH createMessageHeader(int organizationId, Visit visit, Date today, String labOrderNumber) {
		MSH h = new MSH();
		h.setSendingApplication(SENDING_APPLICATION_NAME);
		h.setSendingFacility(SENDING_APPLICATION_FACILITY);
		h.setMessageType(ORDER_MESSAGE_TYPE);
		h.setReceivingApplication(RECEIVING_APPLICATION_NAME);
		h.setReceivingFacility(RECEIVING_APPLICATION_FACILITY);
		if (labOrderNumber == null) {
			String messageControlId = createOrderNumber(visit.getVisitNumber(), organizationId);
			h.setMessageControlId(messageControlId);
		}
		else {
			h.setMessageControlId(labOrderNumber);
		}
		String datetimeOfMessage = DateTimeFormatUtils.toDateTime(today, LAB_DATE_TIME_FORMATTER);
		h.setMessageDateTime(datetimeOfMessage);
		return h;
	}
	
	private static EVN createEventType(String datetime) {
		EVN evn = new EVN();
		evn.setEventTypeCode("O01");
		evn.setRecordedDateTime(datetime);
		return evn;
	}
	
	public synchronized static String createOrderNumber(String visitNumber, int organizationId) {
		RunningNumberDAO dao = new RunningNumberDAO();
		RunningNumberDTO o = dao.getRunningNumberUpdate(organizationId, 0, RunningNumber.QUANUMLAB_NUMBER);			
		String sequentialNumber = o.getRunningNumberCode();
		return sequentialNumber;
	}
	
	private static PID createPatient(Patient p) {
		PID o = new PID();
		o.setPatientIdExt("");
		o.setPatientIdInt("RNL" + PATIENT_ID_INT_FORMATTER.format(p.getPatientId()));
		o.setPatientIdType("NEW IC");
		o.setPatientIdNumber(p.getNricFinNumber());
		o.setPatientName(p.getPatientName());
		o.setDateOfBirth(DateTimeFormatUtils.toDateTime(p.getDateOfBirth(), DateTimeFormatUtils.FAST_YYYYMMDD_FORMATTER));
		o.setGenderCode(p.getGender() == 1 ? "M" : "F");
		int organizationId = p.getOrganizationId();
		o.setRaceCode(getLookupDetailName(organizationId, "Race", p.getRace()));
		o.setNationalityCode(getLookupDetailName(organizationId, "Nationality", p.getNationality()));
		
		// Address
		Address address = ADDRESS_DAO.findPatientAddress(p.getPatientId());
		String address1 = null;
		String address2 = null;
		String postalCode = null;
		if (address != null) {
			address1 = address.getAddress1();
			address2 = address.getAddress2();
			postalCode = address.getPostalCode();
		}
		o.setAddress1(address1 != null ? address1 : "NA");
		o.setAddress2(address2 != null ? address2 : "NA");
		o.setAddress3("");
		o.setCity("");
		o.setState("");
		o.setPostalCode(postalCode != null ? postalCode : "NA");
		
		// Contact
		List<ContactInfo> contactInfos = CONTACT_INFO_DAO.findByPatientId(p.getPatientId());
		o.setPhoneHome("0");
		o.setPhoneMobile("0");
		o.setPhoneBusiness("0");
		for (ContactInfo co : contactInfos) {
			if (co.getContactTypeId() == 1) {
				o.setPhoneHome(co.getContactDetail());
			}
			else if (co.getContactTypeId() == 2) {
				o.setPhoneMobile(co.getContactDetail());
			}
			else if (co.getContactTypeId() == 3) {
				o.setPhoneBusiness(co.getContactDetail());
			}
		}
		
		o.setPrimaryLanguage("");
		o.setMaritalStatusCode(getLookupDetailName(organizationId, "Marital Status", p.getMaritalStatus()));
		o.setReligionCode(getLookupDetailName(organizationId, "Religion", p.getReligion()));
		return o;
	}
	
	private static String getLookupDetailName(int organizationId, String lookupHeaderName, Integer lookupValue) {
		LookupHeader lookupHeader = LOOKUP_HEADER_DAO.getLookupHeaderByName(organizationId, lookupHeaderName);
		if (lookupHeader != null) {
			if (lookupValue != null && lookupValue != 0) {
				LookupDetail detail = LOOKUP_DETAIL_DAO.getLookupDetailById(lookupHeader.getLookupHeaderId(), lookupValue);
				return detail.getLookupDetailName().toUpperCase();
			}
		}
		return "NA";
	}
	
	private static PV1 createVisit(Branch branch, Visit visit, Doctor doctor, SystemUser systemUser, String datetime) {
		PV1 o = new PV1();
		o.setPatientTypeCode("OP");
		o.setWardCode("");
		o.setAdmissionType("");
		o.setPreadmitNumber("");
		o.setSpecialtyCode("SP_20");
		o.setAttendingDoctorCode("");
		o.setAttendingDoctorDesc("");
		o.setReferringDoctorCode("");
		o.setReferringDoctorDesc("");
		o.setConsultingDoctorCode("");
		o.setConsultingDoctorDesc("");
		o.setAdmittingDoctorCode("");
		o.setAdmittingDoctorDesc("");
		o.setPatientClass("");
		o.setVisitNumber(visit.getVisitNumber());
		o.setFinancialClassCode("NA");
		o.setAdmitDateTime(datetime);
		return o;
	}
	
	private static ORC createOrder(Branch branch, LabOrder labOrder, List<LabOrderDetail> labOrderDetails, 
			LabOrderStatus labOrderStatus, Date today, MSH msh) 
		throws LabOrderException {
		ORC o = new ORC();
		o.setOrderControl("NW");
		o.setORCPlacerOrderNumber(msh.getMessageControlId());
		o.setORCFillerOrderNumber("");
		o.setPlacerGroupNumber("");
		o.setOrderStatusCode("");
		o.setResponseFlag("");
		o.setORCQuantityTiming("");
		o.setParent("");
		o.setDateTimeOfTransaction("");
		o.setEnteredByID("");
		o.setEnteredByName("");
		o.setVerifiedByID("");
		o.setVerifiedByName("");
		o.setORCOrderingProviderID("RPJ92");
		o.setORCOrderingProviderName(branch.getBranchName());
		o.setEnterLocation("");
		o.setCallBackPhoneNumber("");
		o.setOrderEffectiveDateTime("");
		o.setOrderControlCodeReason("");
		o.setEnteringOrganization("");
		o.setEnteringDevice("");
		o.setActionBy("");
		o.setAdvancedBeneficiaryNoticeCode("");
		o.setOrderingFacilityCode("");
		o.setOrderingFacilityName("");
		return o;
	}

	private static ObservationRequest createRequest(Branch branch, List<LabOrderDetail> labOrderDetails, String datetime) {
//		ObservationRequest o = new ObservationRequest();
//		o.setSid(String.valueOf(sequenceNumber));
//		UniversalServiceIdentifier identifier = new UniversalServiceIdentifier();
//		identifier.setOrderServiceCode(labOrderDetail.getProfileName());
//		identifier.setOrderDescription(labOrderDetail.getProcedureDescription());
//		identifier.setRadiologyClass(SENDING_APPLICATION_NAME);
//		o.setUniversalServiceId(identifier);
//		// TODO Need to change the way to convert it from adapter.
//		String requestedDatetime = DateTimeFormatUtils.toDateTime(today, LAB_DATE_TIME_FORMATTER);
//		o.setRequestedDatetime(requestedDatetime);
//		o.setSpecimenActionCode(ESpecimenActionCode.A.name());
//		o.setResultCopiesTo(branch.getBranchCode());
		ObservationRequest observationRequest = new ObservationRequest();
		
		for (int index = 0; index < labOrderDetails.size(); index++) {
			LabOrderDetail labOrderDetail = labOrderDetails.get(index);
			OBR obr = new OBR();
			obr.setSetIdOBR(String.valueOf(index + 1));
			obr.setOBRPlacerOrderNumber(labOrderDetail.getAccessionNumber());
			obr.setOBRFillerOrderNumber("");
			obr.setPackageCode("");
			obr.setPackageDescription("");
			LabProfile labProfile = LAB_PROFILE_DAO.getLabProfile(labOrderDetail.getLabCustomerId(), labOrderDetail.getProcedureId());
			if (labProfile != null) {
				obr.setTestCode(labProfile.getProfileName());
				obr.setTestName(labProfile.getProfileDescription());
			}
			else {
				throw new RuntimeException("No mapping between procedure and profile.");
			}
			
			obr.setPriorityCode("");
			obr.setPackageDescription("");
			obr.setRequestedDateTime(datetime);
			obr.setObservationDateTime("");
			obr.setObservationEndDateTime("");
			obr.setCollectionVolume("");
			obr.setCollectorIdentifier("");
			obr.setSpecimenActionCode("");
			obr.setDangerCode("");
			obr.setRelevantClinicalInfo("");
			obr.setSpecimenReceivedDateTime("");
			obr.setSpecimenSourceCode("");
			obr.setSpecimenSourceDesc("");
			obr.setOrderCallbackPhoneNumber("");
			obr.setPlacerField1("GF_LAB");
			obr.setPlacerField2("GF_LAB");
			obr.setFillerField1("");
			obr.setFillerField2("");
			obr.setStatusChangeDateTime("");
			obr.setChargeToPractice("");
			obr.setDiagnosticServSectCode("NA");
			obr.setDiagnosticServSectDesc("NA");
			obr.setResultStatus("");
			obr.setParentResult("");
			obr.setOBRQuantityTiming("1.00");
			obr.setUnitCode("UN");
			obr.setUnitDesc("");
			obr.setResultCopiesTo("");
			obr.setTransportationMode("");
			obr.setReasonForStudy("");
			obr.setPrincipalResultInterpreter("");
			obr.setAssistantResultInterpreter("");
			obr.setTechnician("");
			obr.setTranscriptionist("");
			obr.setScheduledDateTime("");
			obr.setNumberOfSampleContainers("");
			obr.setTransportLogisticsOfCollectedSample("");
			obr.setCollectorComment("");
			obr.setTransportArrangementResponsibility("");
			obr.setTransportArranged("");
			obr.setPlannedPatientTransportComment("");
			obr.setProcedureCode("");
			obr.setProcedureCodeModifier("");
			obr.setPlacerSupplementalService("");
			obr.setFillerSupplementalService("");
			obr.setCancelReasonCode("");
			obr.setCancelReasonDesc("");
//			obr.setTestCode(labOrderDetailUpload.getProfileName());
//			obr.setTestName(labOrderDetailUpload.getProcedureDescription());
			observationRequest.setOBR(obr);
		}
		return observationRequest;
	}
}
