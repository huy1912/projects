package com.iis.rims.common;

import java.util.Date;

import com.iis.rims.domain.RunningNumberSet;

public interface RIMSConstants {
	int INACTIVE_STATUS = 0;
	int ACTIVE_STATUS = 1;
	boolean ACTIVE_FLAG_STATUS = true;
	boolean INACTIVE_FLAG_STATUS = false;
	
	public static String tNO = "tNO";
	public static String tYES = "tYES";
	
	public enum ContactType {
		NONE(0, "None"),
		HOME(1, "HomeTelephone-Patient"),
		WORK(2, "WorkTelephone-Patient"),
		MOBILE(3, "MobileNumber"),
		FAX(4, "FaxNumber"),
		EMAIL(5, "E-Mail"),
		OFFICE(6, "TelephoneNumber"),
		OTHERS(7, "Others");
		
		private final Integer contactTypeId;
		private final String remarks;
		
		ContactType(Integer contactTypeId, String remarks) {
			this.contactTypeId = contactTypeId;
			this.remarks = remarks;
		}
		
		public String getRemarks() {
			return this.remarks;
		}
		
		public Integer getContactTypeId() {
			return this.contactTypeId;
		}
		
		public static ContactType findByContactTypeId(Integer contactTypeId) {
			for(ContactType c : ContactType.values()) {
				if(c.getContactTypeId() == contactTypeId) {
					return c;
				}
			}
			return null;
		}
	}
	
	public enum ComponentType {
		RIMS,
		RIMSSAP
	}
	
	public enum RegistrationStatus {
		NONE,
		REGISTERED,
		CANCEL
	}
	
	public enum PaymentMode {
		NONE,
		VISA,
		MASTER,
		AMEX,
		CASH,
		NETS,
		CHEQUE,
		ADVANCE,
		GIRO,
		DINERS;
		
		public static PaymentMode getPaymentModel(int mode) {
			if (mode >= 0 && mode < PaymentMode.values().length) {
				return PaymentMode.values()[mode];
			}
			return null;
		}
	}
	
	public enum SAPB1PaymentMode {
		NONE,
		VISA,
		MASTER,
		AMEX,
		DINERS,
		NETS,
		CASH,
		CHEQUE,
		GIRO,
		ADVANCE	,
		MEPS
	}
	
	public enum VisitType {
		NONE,
		MANUAL,
		AUTO_READING
	}
	
	public enum VisitStatus {
		NONE,
		REGISTERED,
		PROCEDURE_STARTED,
		PROCEDURE_ENDED,
		SUSPENDED,
		CANCELLED,
		HOSPITALIZED,
		MISDIALYSIS,
		INVOICED,
		PAID
	}
	
	public enum PaymentStatus {
		NONE,
		UNPAID,
		PARTIALLY_PAID,
		FULLY_PAID
	}
	
	public enum MultiplePaymentMode {
		NONE,
		SINGLE,
		MULTIPLE
	}
	
	public enum RunningNumber {
		CUSTOMER_CODE("Customer Code", 										true,  true,  false, false, false, false, true,  false, false, false, true,  1, 2, false, true,  false, true,  false),
		CUSTOMER_GL_CODE("Customer GL Code", 								true,  true,  false, false, false, false, true,  false, false, false, true,  1, 2, false, true,  false, true,  false),
		DOCTOR_CODE("Doctor Code", 											true,  true,  false, false, false, false, true,  false, false, false, false, 1, 4, false, false, false, true,  false),
		PATIENT_CODE("Patient Code", 										true,  true,  false, false, false, false, true,  false, false, false, true,  1, 4, false, true,  false, true,  false),
		SERVICE_CLASS_CODE("Service Class Code", 							true,  true,  true,  true,  false, false, false, false, false, false, true,  1, 2, false, false, false, true,  false),
		SERVICE_CODE("Service Code", 										true,  true,  false, false, true,  false, false, false, false, false, true,  1, 2, false, false, false, true,  false),
		SERVICE_PREFIX_CODE("Service Prefix Code", 							true,  true,  false, false, true,  false, false, false, false, false, true,  1, 2, false, false, false, true,  false),
		EXAMINATIONPROCEDURE_CODE("ExaminationProcedure Code", 				true,  true,  false, false, true,  true,  false, false, false, false, true,  1, 3, false, false, false, true,  false),
		REGISTRATION_NUMBER("Registration Number", 							false, false, true,  false, false, false, true,  false, false, false, true,  1, 3, false, false, false, true,  true),
		VISIT_NUMBER("Visit Number", 										false, false, true,  true,  false, false, true,  true,  true,  false, true,  1, 3, true,  false, false, false, true),
		VISIT_DETAIL_ACCESSION_NUMBER("Visit Detail Accession Number", 		false, false, true,  false, true,  true,  true,  false, false, false, true,  1, 3, false, false, false, true,  true),
		HAEMODIALYSIS_CHART_NUMBER("Haemodialysis Chart Number", 			false, false, true,  false, true,  false, true,  true,  true,  false, true,  1, 2, true,  false, false, true,  true),
		CAREPLAN_NUMBER("Careplan Number", 									false, false, true,  false, true,  false, true,  true,  true,  false, true,  1, 2, false, false, false, true,  true),
		PRESCRIPTION_NUMBER("Prescription Number", 							false, false, true,  false, false, false, true,  true,  true,  false, true,  1, 3, false, false, false, true,  true),
		TRANSCRIPT_REPORT_NUMBER("Transcript Report Number", 				false, false, true,  false, false, false, true,  true,  true,  false, true,  1, 3, false, false, false, true,  true),
		NOTIFICATION_NUMBER("Notification Number", 							true,  true,  true,  false, false, false, false, false, false, false, false, 1, 4, false, true,  false, true,  true),
		INVOICE_NUMBER("Invoice Number", 									true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, true,  true,  true),
		RECEIPT_NUMBER("Receipt Number", 									true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		CREDIT_NOTE_NUMBER("Credit Note Number", 							true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		DEBIT_NOTE_NUMBER("Debit Note Number", 								true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		REFUND_NUMBER("Refund Number", 										true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		PAYMENT_STATEMENT_NUMBER("Payment Statement Number", 				false, false, true,  true,  false, false, true,  true,  true,  false, true,  1, 3, true,  false, true,  true,  true),
		PAYMENT_STATEMENT_DETAIL_NUMBER("Payment Statement Detail Number", 	false, false, true,  false, true,  false, true,  false, false, false, true,  1, 3, false, false, false, true,  true),
		STATEMENT_INVOICE_NUMBER("Statement Invoice Number", 				true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, true,  true,  true),
		STATEMENT_CREDIT_NOTE_NUMBER("Statement Credit Note Number", 		true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		STATEMENT_DEBIT_NOTE_NUMBER("Statement Debit Note Number", 			true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		STATEMENT_RECEIPT_NUMBER("Statement Receipt Number", 				true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		STATEMENT_REFUND_NUMBER("Statement Refund Number", 					true,  false, true,  false, false, false, true,  false, false, false, true,  1, 2, false, false, false, true,  true),
		MEDINET_NUMBER("Medinet Number", 									false, false, false, false, false, false, false, true,  false, false, true,  1, 3, false, false, false, true,  true, true, "MM"),
		INNOVATIVE_NUMBER("Innovative Number", 								false, false, false, false, false, false, false, false,  false, false, false,  1, 3, false, false, false, true,  false),
		PATHLAB_NUMBER("Pathlab Number", 								false, false, false, false, false, false, false, false,  false, false, false,  1, 3, false, false, false, true,  false),
		QUANUMLAB_NUMBER("Quantumlab Number", 								false, false, false, false, false, false, false, false,  false, false, false,  1, 3, false, false, false, true,  false);
		//STATEMENT_RECEIPT_REFERENCE_NUMBER("Statement Receipt Reference Number", true, false, true, false, false, true, false, false, false, true, 1, 2, false, false, false);
		
		private final String runningNumberType;
		private final Boolean isCountryCodeSet;
		private final Boolean isOrganizationCodeSet;
		private final Boolean isBranchCodeSet;
		private final Boolean isServiceClassCodeSet;
		private final Boolean isServiceCodeSet;
		private final Boolean isServicePrefixCodeSet;
		private final Boolean isYearSet;
		private final Boolean isMonthSet;
		private final Boolean isDateSet;
		private final Boolean isStartTimeSet;
		private final Boolean setStartRunningNumber;
		private final Integer startRunningNumber;
		private final Integer startDigits;
		private final Boolean isDaySet;
		private final Boolean isSeparatorCharSet;
		private final Boolean isYearRollSet;
		private final Boolean isOrganizationBased;
		private final Boolean isBranchBased;
		private final String monthFormat;
		private final Boolean isMonthRollSet;
		
		private RunningNumber(String runningNumberType, Boolean isCountryCodeSet, Boolean isOrganizationCodeSet,
				Boolean isBranchCodeSet, Boolean isServiceClassCodeSet, 
				Boolean isServiceCodeSet, Boolean isServicePrefixCodeSet,
				Boolean isYearSet, Boolean isMonthSet,
				Boolean isDateSet, Boolean isStartTimeSet,
				Boolean setStartRunningNumber, Integer startRunningNumber, Integer startDigits,
				Boolean isDaySet, Boolean isSeparatorCharSet, Boolean isYearRollSet,
				Boolean isOrganizationBased, Boolean isBranchBased) {
			this(runningNumberType, isCountryCodeSet, isOrganizationCodeSet,
				isBranchCodeSet, isServiceClassCodeSet, 
				isServiceCodeSet, isServicePrefixCodeSet,
				isYearSet, isMonthSet,
				isDateSet, isStartTimeSet,
				setStartRunningNumber, startRunningNumber, startDigits,
				isDaySet, isSeparatorCharSet, isYearRollSet,
				isOrganizationBased, isBranchBased, false, "MMM");
		}
		
		private RunningNumber(String runningNumberType, Boolean isCountryCodeSet, Boolean isOrganizationCodeSet,
				Boolean isBranchCodeSet, Boolean isServiceClassCodeSet, 
				Boolean isServiceCodeSet, Boolean isServicePrefixCodeSet,
				Boolean isYearSet, Boolean isMonthSet,
				Boolean isDateSet, Boolean isStartTimeSet,
				Boolean setStartRunningNumber, Integer startRunningNumber, Integer startDigits,
				Boolean isDaySet, Boolean isSeparatorCharSet, Boolean isYearRollSet,
				Boolean isOrganizationBased, Boolean isBranchBased, Boolean isMonthRollSet, String monthFormat) {
			this.runningNumberType = runningNumberType;
			this.isCountryCodeSet = isCountryCodeSet;
			this.isOrganizationCodeSet = isOrganizationCodeSet;
			this.isBranchCodeSet = isBranchCodeSet;
			this.isServiceClassCodeSet = isServiceClassCodeSet;
			this.isServiceCodeSet = isServiceCodeSet;
			this.isServicePrefixCodeSet = isServicePrefixCodeSet;
			this.isYearSet = isYearSet;
			this.isMonthSet = isMonthSet;
			this.isDateSet = isDateSet;
			this.isStartTimeSet = isStartTimeSet;
			this.setStartRunningNumber = setStartRunningNumber;
			this.startRunningNumber = startRunningNumber;
			this.startDigits = startDigits;
			this.isDaySet = isDaySet;
			this.isSeparatorCharSet = isSeparatorCharSet;
			this.isYearRollSet = isYearRollSet;
			this.isOrganizationBased = isOrganizationBased;
			this.isBranchBased = isBranchBased;
			this.isMonthRollSet = isMonthRollSet;
			this.monthFormat = monthFormat;
		}
		
		public boolean isYearRollSet() {
			return isYearRollSet;
		}
		
		public String getRunningNumberType() {
			return runningNumberType;
		}
		
		public boolean isOrganizationBased() {
			return isOrganizationBased;
		}
		
		public boolean isBranchBased() {
			return isBranchBased;
		}
		
		public RunningNumberSet getRunningNumberSet(int organizationId, int branchId) {
			RunningNumberSet obj = new RunningNumberSet();
			obj.setOrganizationId(organizationId);
			obj.setBranchId(branchId);
			obj.setIsOrganizationBased(isOrganizationBased);
			obj.setIsBranchBased(isBranchBased);
			obj.setRunningNumberType(runningNumberType);
			obj.setIsCountryCodeSet(isCountryCodeSet);
			obj.setIsOrganizationCodeSet(isOrganizationCodeSet);
			obj.setIsBranchCodeSet(isBranchCodeSet);
			obj.setIsServiceClassCodeSet(isServiceClassCodeSet);
			obj.setIsServiceCodeSet(isServiceCodeSet);
			obj.setIsServicePrefixCodeSet(isServicePrefixCodeSet);
			obj.setIsYearSet(isYearSet);
			obj.setIsMonthSet(isMonthSet);
			obj.setIsDateSet(isDateSet);
			obj.setIsStartTimeSet(isStartTimeSet);
			obj.setSetStartRunningNumber(setStartRunningNumber);
			obj.setStartRunningNumber(startRunningNumber);
			obj.setStartDigits(startDigits);
			obj.setIsDaySet(isDaySet);
			obj.setIsSeparatorCharSet(isSeparatorCharSet);
			obj.setIsYearRollSet(isYearRollSet);
			obj.setIsMonthRollSet(isMonthRollSet);
			obj.setMonthFormat(monthFormat);
			obj.setCreatedBy(1);
			obj.setCreatedDate(new Date());
			obj.setLastUpdatedBy(1);
			obj.setLastUpdatedDate(new Date());
			return obj;
		}
	}
	
	public enum MedicalRecordType {
		NONE,
		PRESCRIPTION,
		ALLERGY,
		MEDICATION,
		HISTORY,
		DIAGNOSIS
	}
	
	public enum InvestigationType {
		NONE,
		BLOOD,
		URINE
	}
	
	public enum DoctorType {
		NONE,
		DOCTOR,
		MEDICAL_SOCIAL_WORKDER,
		RENAL_COORDINATOR,
	}
	
	public enum PaymentConfigStatus {
		NONE,
		NO_REGISTRATION,
		NO_PAYMENT_PLANNER,
		NO_PAYMENT_CONFIG,
		PAYMENT_CONFIG_EXISTED
	}
	
	public enum ScheduleSession {
		AM("AM", "07:00:00", "11:00:00"),
		NN("NN", "11:00:00", "15:00:00"),
		PM("PM", "15:00:00", "19:00:00");
		
		private final String session;
		private final String startTime;
		private final String endTime;
		
		ScheduleSession(String session, String startTime, String endTime) {
			this.session = session;
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		public String getSession() {
			return session;
		}
		public String getStartTime() {
			return startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		
		public static ScheduleSession findEnumBySession(String session) {
			for(ScheduleSession ss : ScheduleSession.values()) {
				if(ss.getSession().equals(session)) {
					return ss;
				}
			}
			return null;
		}
		public static ScheduleSession findEnumByStartTime(String time) {
			for(ScheduleSession ss : ScheduleSession.values()) {
				if(ss.getStartTime().equals(time)) {
					return ss;
				}
			}
			return null;
		}
	}
	
	public enum PaymentPlanSupportType {
		FUNDING,
		CPFCLAIM,
		INSURANCE,
		COMPANY,
		CIVILSERVICE,
		COPAYMENT
	}
	
	public enum PaymentPlanStatus {
		NONE,
		CONFIRMED
	}
	
	public enum DocumentType {
		NONE,
		IN(1),
		DN(1),
		CN(-1),
		ST;
		
		private final int sign;
		
		private DocumentType() {
			this(0);
		}
		
		private DocumentType(int sign) {
			this.sign = sign;
		}
		
		public int getSign() {
			return this.sign;
		}
	}
	
	public enum PatientPaymentPlanSearchStatus{
		NONE,
		CONFIRMED("paymentPlanStatus",1),
		NOT_CONFIRMED("paymentPlanStatus",0),
		CONFIGURED("configureStatus",1),
		NOT_CONFIGURED("configureStatus",0),
		ACTIVATED("entityStatus",1),
		NOT_ACTIVATED("entityStatus",0);
		
		private final String value;
		private final int status;
		
		PatientPaymentPlanSearchStatus() {
			this(null, 0);
		}
		
		PatientPaymentPlanSearchStatus(String value, int status) {
			this.value = value;
			this.status = status;
		}
		public String getValue() {
			return value;
		}
		
		public int getStatus() {
			return status;
		}
		
		public static PatientPaymentPlanSearchStatus getEnum(int id) {
			for (PatientPaymentPlanSearchStatus status : values()) {
				if (status.ordinal() == id) {
					return status;
				}
			}
			return null;
		}
	}
	
	public enum ReportFormatType {
		PDF,
		EXCEL,
		WORD,
		RICHTEXT
	}
	
	public enum ReportingType {
		NONE,	
		INVOICE_LISTING_PATIENT,
		INVOICE_LISTING_FUNDING_ORG,
		DEBIT_NOTE_LISTING_PATIENT,	
		DEBIT_NOTE_LISTING_FUNDING_ORG,
		CN_LISTING_PATIENT,
		CN_LISTING_FUNDING_ORG,
		RECEIPT_LISTING_PATIENT,
		RECEIPT_LISTING_FUNDING_ORG,
		REFUND_NOTE_LISTING_PATIENT,
		REFUND_NOTE_LISTING_FUNDING_ORG,
		RECEIPT_SUMMARY_PATIENT,
		RECEIPT_SUMMARY_AR_FUNDING_ORG,
		SOAP_SOA_DETAIL_PATIENT,
		SOAP_SOA_SUMMARY_PATIENT,
		SOA_Detail_FUNDING_ORG,
		SOAF_SOA_SUMMARY_FUNDING_ORG,
		OLP_OUTSTANDING_LEDGER_PATIENT,
		NO_OUTSTANDING_LEDGER_ADDRESS_PATIENT,
		OLF_OUTSTANDING_LEDGER_FUND_ORG,
		NO_OUTSTANDING_LEDGER_ADDRESS_FUND_ORG,
		MR_MEDINET_REPORT_OUTSTANDING,
		MR_MEDINET_REPORT_REFUND_TO_PATIENT,
		RP1_1st_REMINDER_LETTER_PATIENT,
		RP2_2nd_REMINDER_LETTER_PATIENT, 
		RP3_3rd_REMINDER_LETTER_PATIENT, 
		RL1_1st_REMINDER_LETTER_FUND_ORG,
		RL2_2nd_REMINDER_LETTER_FUND_ORG,
		RL3_3rd_REMINDER_LETTER_FUND_ORG,
		MR_REVENUE_By_PROCEDURE_BY_LOCATION,
		RVS_REVENUE_SUMMARY_BY_SERVICE_GROUP_BY_LOCATION,
		NO_OUTSTANDING_SUMMARY_FUNDING_ORG,
		PATIENT_VISIT_LISTING,
		DAILY_SALES_COLLECTION_SUMMARY,
		CLAIM_SUBMISSION_REPORT,
		ADVANCE_PAYMENT_DETAIL_REPORT,
		ADVANCE_PAYMENT_SUMMARY_REPORT,
		ADVANCE_PAYMENT_OFFSET_DETAIL_REPORT,
		INVOICE_LISTING_INPATIENT,
		AR_AGING_SUMMARY,
		AR_AGING_DETAILS
		
	}
	
	
	//TODO Need to consider to use resource for different languages.
	public enum PaymentSponsorMethod {
		// Equal to Bill To Type
		// TODO need to check for other country customization
		NONE,
		/**
		 * Civil Service Card - 1
		 */
		CSC("Civil Service Card"),
		/**
		 * CPF MediShield - 2
		 */
		MSH("CPF Deductions", "MediShield Claim"),
		/**
		 * MediSave - 3
		 */
		MS("CPF Deductions", "MediSave Claim"),
		/**
		 * Funding Organization - 4
		 */
		FO("Funding Organization"),
		/**
		 * Private Insurance - 5
		 */
		PI("Insurance Company"),
		/**
		 * Corporate Company - 6
		 */
		CCP("Corporate Company"),
		/**
		 * Patient Pay - 7
		 */
		PP("Patient CoPayment"),
		/**
		 * Clinics Pay - 8
		 */
		CLI("Clinics");
		
		private final String typeOfDeduction;
		private final String description;
		private PaymentSponsorMethod() {
			this(null);
		}
		
		private PaymentSponsorMethod(String typeOfDeduction) {
			this(typeOfDeduction, null);
		}
		
		private PaymentSponsorMethod(String typeOfDeduction, String description) {
			this.typeOfDeduction = typeOfDeduction;
			this.description = description;
		}
		
		public String getTypeOfDeduction() {
			return this.typeOfDeduction;
		}
		
		public String getDescription() {
			return this.description != null ? this.description : this.typeOfDeduction;
		}
	}
	
	public enum PaymentStatementStatus {
		NONE,
		DRAFT,
		CONFIRM,
		STATEMENT
	}
	
	public enum ReminderType {
		NONE,
		FIRSTREMINDER4160DAYS,
		SECONDREMINDER6190DAYS,
		FINALREMINDERABOVE90DAYS,
		
	}
	
	
	public enum ReceiptType {
		NONE,
		/**
		 * Receipt document type
		 */
		RC(1),
		/**
		 * Refund document type 
		 */
		RF(-1);
		
		private final int sign;
		
		private ReceiptType() {
			this(0);
		}
		
		private ReceiptType(int sign) {
			this.sign = sign;
		}
		
		public int getSign() {
			return this.sign;
		}
	}
	
	public enum AppointmentStatus {
		NONE,
		CONFIRMED,
		SHOW_ON,
		CANCELED,
		NO_SHOW,
		BOOKED,
		DELETED,
		HOSPITALIZED,
		MISDIALYSIS
	}
	
	public enum AppointmentEntityStatus {
		INACTIVE(false), ACTIVE(true);
		
		private final boolean value;
		
		AppointmentEntityStatus(boolean active){
			this.value = active;
		}
		
		public boolean getValue(){
			return this.value;
		}
	}
	public enum PaymentPlanConfigureStatus {
		NONE,
		CONFIGURE
	}
	
	public enum PlanStatus {
		FALSE,
		TRUE;
	}
	
	public enum PatientStatus {
		NONE,
		UNDERCARE,
		DISCHARGED,
		PASSED_AWAY,
		HOSPITALIZED
	}
	
	public enum CollectionMode {
		NONE,
		PER_VISIT,
		PER_MONTH
	}
	
	public enum MainPhase {
		THERAPY_SELECTION,
		PREPARATION,
		THERAPY,
		END_OF_THERAPY,
		DISINFECTION
	}
	
	public enum DialSide {
		NO_OPERATION,
		MAIN_FLOW,
		BYPASS,
		SEQUENTIAL,
		RINSE_DIAL
	}
	
	public enum ModuleType {
		VASCULAR("VASCULAR"),
		REGISTRATION("REGISTRATION");
		
		private final String moduleName;
		
		ModuleType(String moduleName){
			this.moduleName = moduleName;
		}
		
		public String getName(){
			return this.moduleName;
		}
	}
	
	public enum StationType {
		NONE(),
		HD("Dialysis"),
		WEIGHING_SCALE("Weighing Scale");
		
		private final String stationDisplayName;

		private StationType() {
			stationDisplayName = null;
		}
		private StationType(String stationDisplayName) {
			this.stationDisplayName = stationDisplayName;
		}
		
		public static StationType getStationType(int stationId) {
			for (StationType station : StationType.values()) {
				if (station.ordinal() == stationId) {
					return station;
				}
			}
			return StationType.NONE;
		}
		
		public String getDisplayName() {
			return this.stationDisplayName;
		}
	}
	
	public enum Weekday {
		NONE,
		SUNDAY("Sunday", 1),
		MONDAY("Monday", 2),
		TUESDAY("Tuesday", 3),
		WEDNESDAY("Wednesday", 4),
		THURSDAY("Thursday", 5),
		FRIDAY("Friday", 6),
		SATURDAY("Saturday", 7);
		
		private final String value;
		private final int status;
		
		Weekday() {
			this(null, 0);
		}
		
		Weekday(String value, int status) {
			this.value = value;
			this.status = status;
		}
		public String getValue() {
			return value;
		}
		
		public int getStatus() {
			return status;
		}
		
		public static Weekday getEnum(int id) {
			for (Weekday status : values()) {
				if (status.ordinal() == id) {
					return status;
				}
			}
			return null;
		}
	}
	
	public enum Month {		
		JANUARY("January", 0), 
		FEBRUARY("February", 1),
		MARCH("March", 2), 
		APRIL("April", 3),
		MAY("May", 4), 
		JUNE("June", 5), 
		JULY("July", 6),
		AUGUST("August", 7),
		SEPTEMBER("September", 8),
		OCTOBER("October", 9),
		NOVEMBER("November", 10),
		DECEMBER("December", 11);
		
		private final String value;
		private final int position;
		
		Month() {
			this(null, 0);
		}
		
		Month(String value, int position) {
			this.value = value;
			this.position = position;
		}
		
		public String getValue() {
			return value;
		}
		
		public int getPosition() {
			return position;
		}
		
		public Month getMonth(int month) {
			for (Month status : values()) {
				if (status.ordinal() == month) {
					return status;
				}
			}
			return null;
		}
	}
	
	public enum StatementDetailType {
		NONE,
		NORMAL,
		EPO;
	}
	
	// Lab constants
	public enum LabOrderStatus implements IEnumDescription {
		NS("New Order"),
		PN("Pending Result"),
		CN("Cancelled"),
		PS("Received Result"),
		UL("Unlinked");
		
		private final String description;

		private LabOrderStatus(String description) {
			this.description = description;
		}
		
		@Override
		public String getDescription() {
			return description;
		}
		
		public static LabOrderStatus getLabOrderStatus(int labOrderStatus) {
			for (LabOrderStatus status : LabOrderStatus.values()) {
				if (status.ordinal() == labOrderStatus) {
					return status;
				}
			}
			return null;
		}
	}
	
	// TODO Revise this status later, it's redundant with Order status.
	public enum LabStatus {
		NONE,
		PENDING, 
		FAILED, 
		SUCCESSFUL;
	}
	
	public enum OrderReportStatus {
		NONE,
		RECEIVED;
	}
	
	public enum LabResult {
		NONE,
		CANCELLED,
		DONE,
		FAILED;
	}
	
	public enum ResultInput {
		NONE,
		LAB_AUTOMATION,
		MANUAL;
	}
	
	public enum LabOrderDetailType implements IEnumDescription {
		NORMAL("", "NOR"),
		PRE("Pre", "PRE"),
		POST("Post", "POST");
		
		private final String description;
		private final String orderSuffix;

		private LabOrderDetailType(String description, String orderSuffix) {
			this.description = description;
			this.orderSuffix = orderSuffix;
		}
		
		@Override
		public String getDescription() {
			return description;
		}

		public String getOrderSuffix() {
			return orderSuffix;
		}
		
		public static LabOrderDetailType getOrderDetailType(String orderSuffix) {
			for (LabOrderDetailType labOrderDetailType : LabOrderDetailType.values()) {
				if (labOrderDetailType.orderSuffix.equals(orderSuffix)) {
					return labOrderDetailType;
				}
			}
			return null;
		}
	}
	
	public interface IEnumDescription {
		String getDescription();
	}
	
	public enum ReceiptInvoiceType implements IEnumDescription {
		NONE ("None"),
		SINGLE ("Single"),
		MULTIPLE ("Multiple");
		
		private final String description;

		private ReceiptInvoiceType(String description) {
			this.description = description;
		}
		
		@Override
		public String getDescription() {
			return description;
		}
		
		public static ReceiptInvoiceType getReceiptInvoiceType(int receiptInvoiceType) {
			for (ReceiptInvoiceType status : ReceiptInvoiceType.values()) {
				if (status.ordinal() == receiptInvoiceType) {
					return status;
				}
			}
			return null;
		}
	}
	
	public enum ErrorRaisedFromType implements IEnumDescription {
		UNKNOWN ("Unknown"),
		RUNTIME ("Runtime"),
		RIMS ("RIMS"),
		RIMS_ADAPTER ("RIMS Adapter"),
		RIMS_SAP ("RIMS SAP"),
		RIMS_DASHBOARD ("RIMS Dashboard");
		
		private final String description;

		private ErrorRaisedFromType(String description) {
			this.description = description;
		}
		
		@Override
		public String getDescription() {
			return description;
		}
		
		public static ErrorRaisedFromType getErrorRaisedFromType(int errorRaisedFromType) {
			for (ErrorRaisedFromType status : ErrorRaisedFromType.values()) {
				if (status.ordinal() == errorRaisedFromType) {
					return status;
				}
			}
			return null;
		}
	}
	
	public enum AdvanceType {
		NONE,
		SINGLE,
		MULTIPLE
	}

	public enum AdvanceCustomerType {
		NONE,
		PATIENT,
		CUSTOMER
	}
	
	public enum InvoiceType {
		NONE,
		PATIENT,
		CUSTOMER
	}
}
