package com.iis.rims.lab.quantum.controller;

public class SubmitOrder {
	private String orderNumber;
	private String patientName;
	private String nricFinNumber;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
}
