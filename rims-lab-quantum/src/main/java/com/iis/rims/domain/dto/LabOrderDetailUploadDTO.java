package com.iis.rims.domain.dto;

public class LabOrderDetailUploadDTO {
	private int labOrderDetailId;
	private int sequenceNumber;
	private String procedureDescription;
	private String profileName;
	private String accessionNumber;
	
	public int getLabOrderDetailId() {
		return labOrderDetailId;
	}
	public void setLabOrderDetailId(int labOrderDetailId) {
		this.labOrderDetailId = labOrderDetailId;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProcedureDescription() {
		return procedureDescription;
	}
	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}
	public String getAccessionNumber() {
		return accessionNumber;
	}
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
}
