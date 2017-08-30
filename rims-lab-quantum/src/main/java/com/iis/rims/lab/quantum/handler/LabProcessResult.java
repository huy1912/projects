package com.iis.rims.lab.quantum.handler;

public class LabProcessResult {
	private String orderNumber;
	private String labNo;
	private String oruFileName;
	private String pdfFileName;
	
	public LabProcessResult build(String orderNumber, String labNo, String oruFileName, String pdfFileName) {
		setOrderNumber(orderNumber);
		setLabNo(labNo);
		setOruFileName(oruFileName);
		setPdfFileName(pdfFileName);
		return this;
	}
	
	public LabProcessResult buildPdfFileName(String pdfFileName) {
		setOruFileName("");
		setPdfFileName(pdfFileName);
		return this;
	}
	
	public LabProcessResult buildOruFileName(String oruFileName) {
		setOruFileName(oruFileName);
		setPdfFileName("");
		return this;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getLabNo() {
		return labNo;
	}
	public void setLabNo(String labNo) {
		this.labNo = labNo;
	}
	public String getOruFileName() {
		return oruFileName;
	}
	public void setOruFileName(String oruFileName) {
		this.oruFileName = oruFileName;
	}
	public String getPdfFileName() {
		return pdfFileName;
	}
	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}
}

