package com.iis.rims.domain;

import java.io.Serializable;
import java.util.Date;

public class LabTestCode extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String OBJECTTYPESTR = "LabTestCode";
	
	private int labTestCodeId;
	private int organizationId;
	private int customerId;
	private String testCode;
	private String testName;
	private String remark;
	private String rtTestCodeColumn;
	
	private Integer testCodeType;
	private Boolean reportDisplay;
	private Integer testGroup;
	private String rangeValue;
	
	// For display on the result.
	private Float displayOrder;

	public LabTestCode() {
	}

	public LabTestCode(int labTestCodeId, int organizationId, int customerId,
			String testCode, String testName, String remark,
			Boolean entityStatus, int createdBy, Date createdDate,
			int lastUpdatedBy, Date lastUpdatedDate) {
		this.labTestCodeId = labTestCodeId;
		this.organizationId = organizationId;
		this.customerId = customerId;
		this.testCode = testCode;
		this.testName = testName;
		this.remark = remark;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public int getLabTestCodeId() {
		return labTestCodeId;
	}

	public void setLabTestCodeId(int labTestCodeId) {
		this.labTestCodeId = labTestCodeId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRtTestCodeColumn() {
		return rtTestCodeColumn;
	}

	public void setRtTestCodeColumn(String rtTestCodeColumn) {
		this.rtTestCodeColumn = rtTestCodeColumn;
	}

	public Integer getTestCodeType() {
		return testCodeType;
	}

	public void setTestCodeType(Integer testCodeType) {
		this.testCodeType = testCodeType;
	}

	public Boolean getReportDisplay() {
		return reportDisplay;
	}

	public void setReportDisplay(Boolean reportDisplay) {
		this.reportDisplay = reportDisplay;
	}

	public Integer getTestGroup() {
		return testGroup;
	}

	public void setTestGroup(Integer testGroup) {
		this.testGroup = testGroup;
	}

	public String getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(String rangeValue) {
		this.rangeValue = rangeValue;
	}

	public Float getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Float displayOrder) {
		this.displayOrder = displayOrder;
	}
}