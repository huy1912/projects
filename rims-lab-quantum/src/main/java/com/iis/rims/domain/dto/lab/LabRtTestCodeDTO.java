package com.iis.rims.domain.dto.lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LabRtTestCodeDTO {
	private Integer labRtTestCodeId;
	private String patientName;
	private String labName;
	private String nricNumber;
	private Date dateOfBirth;
	private Date labResultDate;
	private String orderNumberRef;
	private List<LabResultDTO> labResults = new ArrayList<LabResultDTO>();
	// Lab summary report
	private Integer labOrderId;
	private Integer labOrderDetailId;
	
	public Integer getLabRtTestCodeId() {
		return labRtTestCodeId;
	}
	public void setLabRtTestCodeId(Integer labRtTestCodeId) {
		this.labRtTestCodeId = labRtTestCodeId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getNricNumber() {
		return nricNumber;
	}
	public void setNricNumber(String nricNumber) {
		this.nricNumber = nricNumber;
	}
	public List<LabResultDTO> getLabResults() {
		return labResults;
	}
	public void setLabResults(List<LabResultDTO> labResults) {
		this.labResults = labResults;
	}
	
	public void addLabResult(LabResultDTO result) {
		labResults.add(result);
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getLabResultDate() {
		return labResultDate;
	}
	public void setLabResultDate(Date labResultDate) {
		this.labResultDate = labResultDate;
	}
	
	@JsonIgnore
	public Integer getLabOrderId() {
		return labOrderId;
	}
	public void setLabOrderId(Integer labOrderId) {
		this.labOrderId = labOrderId;
	}
	
	@JsonIgnore
	public Integer getLabOrderDetailId() {
		return labOrderDetailId;
	}
	public void setLabOrderDetailId(Integer labOrderDetailId) {
		this.labOrderDetailId = labOrderDetailId;
	}
	public String getOrderNumberRef() {
		return orderNumberRef;
	}
	public void setOrderNumberRef(String orderNumberRef) {
		this.orderNumberRef = orderNumberRef;
	}
}
