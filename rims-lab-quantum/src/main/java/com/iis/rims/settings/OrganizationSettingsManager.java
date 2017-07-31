package com.iis.rims.settings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iis.rims.hibernate.dao.SystemPropertyDAO;

public class OrganizationSettingsManager {

	private static ConcurrentHashMap<Integer, OrganizationSettingsManager> ORGANIZATION_SETTINGS 
		= new ConcurrentHashMap<Integer, OrganizationSettingsManager>(16, 0.9f ,1);
	private final BigDecimal defaultGst;
	private final BigDecimal defaultGstReverse;
	private final BigDecimal defaultGstInPercentage;
	private final Integer maxCapMedishield;
	private final Integer maxCapMedisave;
	private final Integer maxChangeReturn;
	private final Integer maxNumberOfLeftOfAppointments;
	private final Integer maxNumberOfMonthsOfAppointments;
	private final Integer maxNumberOfDaysCrossMonth;
	private final String excelTemplatePath;
	private final String defaultCustomerTemplatePath;
	private final Integer pollingInterval;
	private final Integer graphPollingInterval;
	private final Integer homePollingInterval;
	private final Integer carouselInterval;
	private final Integer dialysisDurationHours;
	private final Integer numberOfHDReadingPerHours;
	private final Integer hdStationMonitoringInterval;
	private final Integer hdServiceId;
	
	// Define constants for SAP
	private final Boolean sapFlag;
	private final String taxCode;
	private final String zeroTaxCode;
	private final String sapLink;
	private final Boolean sapSendException;
	private final String sapDatabaseServer;
	private final String sapDatabaseName;
	private final String sapDatabaseType;
	private final String sapUserName;
	private final String sapPassword;
	private final String sapLanguage;
	private final String sapLicenseServer;
	
	
	private final String nurseDesignations;
	private final String doctorDesignations;
	private final Integer hDChartDetailIntervalSelection;
	private final Integer organizationId;
	private final String rimsUrl;
	private final String rimsUserName;
	private final String rimsPwd;
	private final String sapUrl;
	private final String sapDbUserName;
	private final String sapDbPwd;
	private final String advancePaymentFromPatients;
	
	private final Boolean labIntegrationEnabledFlag;
	private final Integer labOrdersUIPollingInterval;
	private final Integer labOrdersDownloadInterval;
	
	private final Boolean medinetIntegrationEnabledFlag;
	private final Integer medinetClaimDownloadInterval;
	private final BigDecimal epoSponsor;
	private final int labServiceId;
	private final int epoServiceId;
	private final int medicationServiceId;
	private final int labCustomerTypeId;
	private final int nationalityHeaderId;
	private final int serviceClassId;
		
	private OrganizationSettingsManager(int organizationId) {	
		SystemPropertyDAO dao = new SystemPropertyDAO();
		this.organizationId = organizationId;
		HashMap<String, String> sp = dao.findSystemProperties(organizationId);

		defaultGst =  sp.containsKey("GST") ? new BigDecimal(sp.get("GST")) : new BigDecimal("0.07");			
		defaultGstInPercentage = defaultGst.multiply(BigDecimal.valueOf(100));
		defaultGstReverse = sp.containsKey("GSTReverse") ? new BigDecimal(sp.get("GSTReverse")):  new BigDecimal("1.07") ;		
		
		maxCapMedishield = sp.containsKey("MaxCapMedishield") ? new Integer(sp.get("MaxCapMedishield")) : 1000;
		maxCapMedisave = sp.containsKey("MaxCapMedisave") ? new Integer(sp.get("MaxCapMedisave")) : 450;
		maxChangeReturn = sp.containsKey("MaxChangeReturn") ? new Integer(sp.get("MaxChangeReturn")) : 100;
		maxNumberOfLeftOfAppointments = sp.containsKey("MaxNumberOfLeftOfAppointments") ? new Integer(sp.get("MaxNumberOfLeftOfAppointments")) : 3;
		maxNumberOfMonthsOfAppointments = sp.containsKey("MaxNumberOfMonthsOfAppointments") ? new Integer(sp.get("MaxNumberOfMonthsOfAppointments")) : 3;
		maxNumberOfDaysCrossMonth = sp.containsKey("MaxNumberOfDaysCrossMonth") ? new Integer(sp.get("MaxNumberOfDaysCrossMonth")) : 3;
		excelTemplatePath = sp.containsKey("excelTemplatePath") ? sp.get("excelTemplatePath") : "reports/excel"; 
		pollingInterval = sp.containsKey("PollingInterval") ? new Integer(sp.get("PollingInterval")) : 300000;
		graphPollingInterval = sp.containsKey("GraphPollingInterval") ? new Integer(sp.get("GraphPollingInterval")) : 300000;
		homePollingInterval = sp.containsKey("HomePollingInterval") ? new Integer(sp.get("HomePollingInterval")) : 120000;
		carouselInterval = sp.containsKey("CarouselInterval") ? new Integer(sp.get("CarouselInterval")) : 60000;		
		sapFlag = sp.containsKey("SAPFlag") ? new Boolean(sp.get("SAPFlag")) : false;
		taxCode = sp.containsKey("TaxCode") ? sp.get("TaxCode") : "SR";
		zeroTaxCode = sp.containsKey("ZeroTaxCode") ? sp.get("ZeroTaxCode") : "ZR";
		sapLink = sp.containsKey("SAPHyperlink") ? sp.get("SAPHyperlink") : "http://192.168.81.85:8181/rimsSAP/";
		dialysisDurationHours = sp.containsKey("DialysisDurationHours") ? new Integer(sp.get("DialysisDurationHours")) : 4;
		numberOfHDReadingPerHours = sp.containsKey("NumberOfHDReadingPerHours") ? new Integer(sp.get("NumberOfHDReadingPerHours")) : 12;
		hdStationMonitoringInterval = sp.containsKey("HDSTATION_MONITORING_INTERVAL") ? new Integer(sp.get("HDSTATION_MONITORING_INTERVAL")) : 10;
		defaultCustomerTemplatePath =  sp.containsKey("defaultCustomerTemplatePath") ? sp.get("defaultCustomerTemplatePath") : "template/Default_Customer_Template.xlsx"; 
		nurseDesignations =  sp.containsKey("NURSE_DESINATIONS") ? sp.get("NURSE_DESINATIONS") : "1,4,10,11,12";
		doctorDesignations =  sp.containsKey("DOCTOR_DESINATIONS") ? sp.get("DOCTOR_DESINATIONS") : "2,5,19";
		hDChartDetailIntervalSelection =  sp.containsKey("HDCHARTDETAIL_INTERVAL_SELECTION") ? new Integer(sp.get("HDCHARTDETAIL_INTERVAL_SELECTION")) : 3;
		serviceClassId =  sp.containsKey("ServiceClassId") ? new Integer(sp.get("ServiceClassId")) : 1;
		hdServiceId =  sp.containsKey("HDServiceId") ? new Integer(sp.get("HDServiceId")) : 1;
		sapSendException = sp.containsKey("SAPSendException") ? new Boolean(sp.get("SAPSendException")) : true;
		
		sapDatabaseServer = sp.containsKey("login.databaseServer") ?sp.get("login.databaseServer") : "RIMSDBSTSVR";
		sapDatabaseName = sp.containsKey("login.databaseName") ?sp.get("login.databaseName") : "Renal_test";
		sapDatabaseType = sp.containsKey("login.databaseType") ?sp.get("login.databaseType") : "6";
		sapUserName = sp.containsKey("login.companyUsername") ?sp.get("login.companyUsername") : "Manager";
		sapPassword = sp.containsKey("login.companyPassword") ?sp.get("login.companyPassword") : "welcome";
		sapLanguage = sp.containsKey("login.language") ?sp.get("login.language") : "3";
		sapLicenseServer = sp.containsKey("login.licenseServer") ?sp.get("login.licenseServer") : "192.168.81.85:30000";
		rimsUrl = sp.containsKey("RIMSUrl") ?sp.get("RIMSUrl"):"jdbc:sqlserver://192.168.85.186:1433;databaseName=RIMSDB_NEW";
		rimsUserName = sp.containsKey("RIMSUserName") ?sp.get("RIMSUserName"):"sa";
		rimsPwd = sp.containsKey("RIMSPwd") ?sp.get("RIMSPwd"):"P0pc0rn!)!)";
		sapUrl = sp.containsKey("SAPUrl")?sp.get("SAPUrl"):"jdbc:sqlserver://192.168.85.186:1433;databaseName=RIMSDB_NEW";
		sapDbUserName = sp.containsKey("SAPUserName")?sp.get("SAPUserName"):"sa";
		sapDbPwd = sp.containsKey("SAPPwd")?sp.get("SAPPwd") :"P0pc0rn)*)*";
		advancePaymentFromPatients = sp.containsKey("AdvancePaymentFromPatients") ? sp.get("AdvancePaymentFromPatients") : "21020510";
		
		labOrdersUIPollingInterval = sp.containsKey("LabOrdersUIPollingInterval") ? new Integer(sp.get("LabOrdersUIPollingInterval")) : 600000;
		labOrdersDownloadInterval  = sp.containsKey("LabOrdersDownloadInterval") ? new Integer(sp.get("LabOrdersDownloadInterval")) : 600000;
		labIntegrationEnabledFlag = sp.containsKey("LabIntegrationEnabled") ? new Boolean(sp.get("LabIntegrationEnabled")) : false;
		medinetIntegrationEnabledFlag = sp.containsKey("MedinetIntegrationEnabled") ? new Boolean(sp.get("MedinetIntegrationEnabled")) : false;
		medinetClaimDownloadInterval = sp.containsKey("MedinetClaimDownloadInterval") ? new Integer(sp.get("MedinetClaimDownloadInterval")) : 600000;
		epoSponsor = sp.containsKey("EpoSponsor") ? new BigDecimal(sp.get("EpoSponsor")) : BigDecimal.valueOf(0.9);
		labServiceId =  sp.containsKey("LabServiceId") ? new Integer(sp.get("LabServiceId")) : 4;
		epoServiceId =  sp.containsKey("EpoServiceId") ? new Integer(sp.get("EpoServiceId")) : 8;
		medicationServiceId =  sp.containsKey("MedicationServiceId") ? new Integer(sp.get("MedicationServiceId")) : 6;
		labCustomerTypeId =  sp.containsKey("LabCustomerTypeId") ? new Integer(sp.get("LabCustomerTypeId")) : 11;
		nationalityHeaderId =  sp.containsKey("NationalityHeaderId") ? new Integer(sp.get("NationalityHeaderId")) : 38;
	}

	public BigDecimal getDefaultGst() {
		return defaultGst;
	}

	public BigDecimal getDefaultGstInPercentage() {
		return defaultGstInPercentage;
	}

	public BigDecimal getDefaultGstReverse() {
		return defaultGstReverse;
	}
	
	public BigDecimal getGstAmount(BigDecimal value) {
		return value.subtract(getNetAmount(value));
	}
	public String getRimsUrl() {
		return rimsUrl;
	}

	public String getRimsUserName() {
		return rimsUserName;
	}

	public String getRimsPwd() {
		return rimsPwd;
	}

	public String getSapUrl() {
		return sapUrl;
	}

	public String getSapDbUserName() {
		return sapDbUserName;
	}

	public String getSapDbPwd() {
		return sapDbPwd;
	}
	
	public String getAdvancePaymentFromPatients() {
		return advancePaymentFromPatients;
	}

	public BigDecimal getGstAmountFromNetAmount(BigDecimal value) {
//		return value.subtract(value.divide(defaultGstReverse, 2, RoundingMode.HALF_UP));
		if(defaultGst.floatValue() > 0.0)
			return value.multiply(defaultGst);
		else
			return new BigDecimal(0);
	}

	public BigDecimal getNetAmount(BigDecimal value) {
		if(defaultGstReverse.floatValue() > 0.0)
			return value.divide(defaultGstReverse, 2, RoundingMode.HALF_UP);
		else
			return value;
	}
	
	public BigDecimal getGrossAmount(BigDecimal netAmount) {
		if(defaultGstReverse.floatValue() > 0.0)
			return netAmount.multiply(defaultGstReverse);
		else
			return netAmount;
	}
	
	public Integer getMaxCapMedishield() {
		return maxCapMedishield;
	}

	public Integer getMaxCapMedisave() {
		return maxCapMedisave;
	}

	public Integer getMaxChangeReturn() {
		return maxChangeReturn;
	}
	
	public Integer getMaxNumberOfLeftOfAppointments(){
		return maxNumberOfLeftOfAppointments;
	}
	
	public Integer getMaxNumberOfMonthsOfAppointments(){
		return maxNumberOfMonthsOfAppointments;
	}

	public Integer getMaxNumberOfDaysCrossMonth() {
		return maxNumberOfDaysCrossMonth;
	}

	public String getExcelTemplatePath() {
		return excelTemplatePath;
	}
	
	public Integer getPollingInterval() {
		return pollingInterval;
	}
	
	public Integer getCarouselInterval() {
		return carouselInterval;
	}

	public Integer getGraphPollingInterval() {
		return graphPollingInterval;
	}

	public Integer getHomePollingInterval() {
		return homePollingInterval;
	}
	
	
	public Boolean getSapFlag() {
		return sapFlag;
	}

	public String getTaxCode() {
		return taxCode;
	}

	/*public void setExcelTemplatePath(String excelTemplatePath) {
		this.excelTemplatePath = excelTemplatePath;
	}*/
	
	public Integer getServiceClassId() {
		return serviceClassId;
	}

	public Integer getHdServiceId() {
		return hdServiceId;
	}

	public String getZeroTaxCode() {
		return zeroTaxCode;
	}

	public String getSapLink() {
		return sapLink;
	}

	public Integer getDialysisDurationHours() {
		return dialysisDurationHours;
	}

	public Integer getNumberOfHDReadingPerHours() {
		return numberOfHDReadingPerHours;
	}

	public String getDefaultCustomerTemplatePath() {
		return defaultCustomerTemplatePath;
	}

	public String getNurseDesignations() {
		return nurseDesignations;
	}

	public String getDoctorDesignations() {
		return doctorDesignations;
	}

	/*public void setDefaultCustomerTemplatePath(String defaultCustomerTemplatePath) {
		this.defaultCustomerTemplatePath = defaultCustomerTemplatePath;
	}*/

	public Integer gethDChartDetailIntervalSelection() {
		return hDChartDetailIntervalSelection;
	}

	public Boolean getSapSendException() {
		return sapSendException;
	}

	public String getSapDatabaseServer() {
		return sapDatabaseServer;
	}

	public String getSapDatabaseName() {
		return sapDatabaseName;
	}

	public String getSapDatabaseType() {
		return sapDatabaseType;
	}

	public String getSapUserName() {
		return sapUserName;
	}

	public String getSapPassword() {
		return sapPassword;
	}

	public String getSapLanguage() {
		return sapLanguage;
	}

	public String getSapLicenseServer() {
		return sapLicenseServer;
	}

	public Integer getHdStationMonitoringInterval() {
		return hdStationMonitoringInterval;
	}
	
	public Integer getEpoService() {
		return epoServiceId;
	}
	
	public Integer getMedicationService() {
		return medicationServiceId;
	}
	
	public Integer getHDService() {
		return hdServiceId;
	}
	
	// TODO Use the system property if required.
	public Integer getMedishieldId() {
		return 15;
	}
	
	public Integer getMedisaveId() {
		return 16;
	}
	
	public int getFOCustomerType() {
		return 4;
	}
	
	public int getCSCCustomerType() {
		return 6;
	}
	
	public Map<String, Integer> getServiceMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("HAEMODIALYSIS", getHDService());
		map.put("MEDICATION", getMedicationService());
		map.put("EPO", getEpoService());
		return map;
	}
	
	public Map<String, Integer> getCustomerMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("MEDISHIELD", getMedishieldId());
		map.put("MEDISAVE", getMedisaveId());
		return map;
	}
	
	public Integer getOrganizationId() {
		return organizationId;
	}
	
	public int getLabCustomerType() {
		return labCustomerTypeId;
	}
	
	public int getLabServiceId() {
		return labServiceId;
	}
	
	public int getNationalityHeaderId() {
		return nationalityHeaderId;
	}
	
	public static OrganizationSettingsManager getOrganizationSettingsById(int organizationId, String branchName) {
		OrganizationSettingsManager obj = ORGANIZATION_SETTINGS.get(organizationId);
		if(obj == null) {
			synchronized (OrganizationSettingsManager.class) {
				obj = ORGANIZATION_SETTINGS.get(organizationId);
				if (obj == null) {
					OrganizationSettingsManager newOrgSetting = new OrganizationSettingsManager(organizationId);
					ORGANIZATION_SETTINGS.put(organizationId, newOrgSetting);
					obj = newOrgSetting;
				}
			}
		}
		return obj;
	}

	public Boolean getLabIntegrationEnabledFlag() {
		return labIntegrationEnabledFlag;
	}

	public Integer getLabOrdersUIPollingInterval() {
		return labOrdersUIPollingInterval;
	}

	public Integer getLabOrdersDownloadInterval() {
		return labOrdersDownloadInterval;
	}

	public Boolean getMedinetIntegrationEnabledFlag() {
		return medinetIntegrationEnabledFlag;
	}

	public Integer getMedinetClaimDownloadInterval() {
		return medinetClaimDownloadInterval;
	}
	
	public BigDecimal getEpoSponsor() {
		return epoSponsor;
	}
}
