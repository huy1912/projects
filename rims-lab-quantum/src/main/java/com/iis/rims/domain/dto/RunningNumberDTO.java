package com.iis.rims.domain.dto;

public class RunningNumberDTO {
	private String runningNumberCode;
	private Integer latestRunningNumber;
	
	public String getRunningNumberCode() {
		return runningNumberCode;
	}
	public void setRunningNumberCode(String runningNumberCode) {
		this.runningNumberCode = runningNumberCode;
	}
	public Integer getLatestRunningNumber() {
		return latestRunningNumber;
	}
	public void setLatestRunningNumber(Integer latestRunningNumber) {
		this.latestRunningNumber = latestRunningNumber;
	}
}
