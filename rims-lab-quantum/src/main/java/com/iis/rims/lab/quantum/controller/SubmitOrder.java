package com.iis.rims.lab.quantum.controller;

public class SubmitOrder {
	private String patientId;
	private String patientName;
	private String nricFinNumber;
	private String orcOrderNumber;
	private String obrOrderNumber;
	private String visitNumber;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getNricFinNumber() {
		return nricFinNumber;
	}
	public void setNricFinNumber(String nricFinNumber) {
		this.nricFinNumber = nricFinNumber;
	}
	public String getOrcOrderNumber() {
		return orcOrderNumber;
	}
	public void setOrcOrderNumber(String orcOrderNumber) {
		this.orcOrderNumber = orcOrderNumber;
	}
	public String getObrOrderNumber() {
		return obrOrderNumber;
	}
	public void setObrOrderNumber(String obrOrderNumber) {
		this.obrOrderNumber = obrOrderNumber;
	}
	public String getVisitNumber() {
		return visitNumber;
	}
	public void setVisitNumber(String visitNumber) {
		this.visitNumber = visitNumber;
	}
}
