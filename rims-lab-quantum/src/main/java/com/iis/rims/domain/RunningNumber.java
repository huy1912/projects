package com.iis.rims.domain;

// Generated Jun 20, 2013 11:25:34 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * RunningNumber generated by hbm2java
 */
public class RunningNumber extends BaseDomain implements java.io.Serializable {

	private Integer runningNumberId;
	private int organizationId;
	private int branchId;
	private String runningNumberName;
	private String runningNumberCode;
	private int startRunningNumber;
	private int latestRunningNumber;
//	private int nextRunningNumber;
	private Boolean isYearRolled;
	private Integer rollingYear;

	public RunningNumber() {
	}

	public RunningNumber(int organizationId, int branchId,
			String runningNumberName, String runningNumberCode,
			int startRunningNumber, int latestRunningNumber,
			int nextRunningNumber, boolean entityStatus, int createdBy,
			Date createdDate, int lastUpdatedBy, Date lastUpdatedDate) {
		this.organizationId = organizationId;
		this.branchId = branchId;
		this.runningNumberName = runningNumberName;
		this.runningNumberCode = runningNumberCode;
		this.startRunningNumber = startRunningNumber;
		this.latestRunningNumber = latestRunningNumber;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public RunningNumber(int organizationId, int branchId,
			String runningNumberName, String runningNumberCode,
			int startRunningNumber, int latestRunningNumber,
			int nextRunningNumber, Boolean isYearRolled, boolean entityStatus,
			int createdBy, Date createdDate, int lastUpdatedBy,
			Date lastUpdatedDate) {
		this.organizationId = organizationId;
		this.branchId = branchId;
		this.runningNumberName = runningNumberName;
		this.runningNumberCode = runningNumberCode;
		this.startRunningNumber = startRunningNumber;
		this.latestRunningNumber = latestRunningNumber;
		this.isYearRolled = isYearRolled;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public Integer getRunningNumberId() {
		return this.runningNumberId;
	}

	public void setRunningNumberId(Integer runningNumberId) {
		this.runningNumberId = runningNumberId;
	}

	public int getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getRunningNumberName() {
		return this.runningNumberName;
	}

	public void setRunningNumberName(String runningNumberName) {
		this.runningNumberName = runningNumberName;
	}

	public String getRunningNumberCode() {
		return this.runningNumberCode;
	}

	public void setRunningNumberCode(String runningNumberCode) {
		this.runningNumberCode = runningNumberCode;
	}

	public int getStartRunningNumber() {
		return this.startRunningNumber;
	}

	public void setStartRunningNumber(int startRunningNumber) {
		this.startRunningNumber = startRunningNumber;
	}

	public int getLatestRunningNumber() {
		return this.latestRunningNumber;
	}

	public void setLatestRunningNumber(int latestRunningNumber) {
		this.latestRunningNumber = latestRunningNumber;
	}

	/*public int getNextRunningNumber() {
		return this.nextRunningNumber;
	}

	public void setNextRunningNumber(int nextRunningNumber) {
		this.nextRunningNumber = nextRunningNumber;
	}*/

	public Boolean getIsYearRolled() {
		return this.isYearRolled;
	}

	public void setIsYearRolled(Boolean isYearRolled) {
		this.isYearRolled = isYearRolled;
	}

	public Integer getRollingYear() {
		return rollingYear;
	}

	public void setRollingYear(Integer rollingYear) {
		this.rollingYear = rollingYear;
	}
}
