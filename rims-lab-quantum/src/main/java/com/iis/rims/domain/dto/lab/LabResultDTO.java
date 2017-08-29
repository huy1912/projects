package com.iis.rims.domain.dto.lab;

public class LabResultDTO {
	private String rtTestCodeColumn;
	private String observationValue;
	private String units;
	private String referenceRange;
	private String abnormalFlags;
	private String observationStatus;
	private String remark;
	private String note;
	
	public String getObservationValue() {
		return observationValue;
	}
	public void setObservationValue(String observationValue) {
		this.observationValue = observationValue;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getReferenceRange() {
		return referenceRange;
	}
	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}
	public String getAbnormalFlags() {
		return abnormalFlags;
	}
	public void setAbnormalFlags(String abnormalFlags) {
		this.abnormalFlags = abnormalFlags;
	}
	public String getObservationStatus() {
		return observationStatus;
	}
	public void setObservationStatus(String observationStatus) {
		this.observationStatus = observationStatus;
	}
	public String getRtTestCodeColumn() {
		return rtTestCodeColumn;
	}
	public void setRtTestCodeColumn(String rtTestCodeColumn) {
		this.rtTestCodeColumn = rtTestCodeColumn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
