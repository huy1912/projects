package com.iis.rims.domain;

// Generated Jul 29, 2015 11:30:25 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;

/**
 * TblLabOrderDetail generated by hbm2java
 */
public class LabOrderDetail extends BaseDomain implements java.io.Serializable {

	private Integer labOrderDetailId;
	private int labOrderId;
	private int labPackageId;
	private String labPackageName;
	private String accessionNumber;
	private int procedureId;
	private String procedureName;
	private BigDecimal listedCashPrice;
	private int quantity;
	private BigDecimal netAmount;
	private int netAmountSign;
	private boolean confirmed;
	private int paymentStatus;
	private Integer orderReportStatus;
	private byte[] orderReportAttached;
	private String remark;
	private Boolean deleted;

	private int orderStatus;
	private int orderHl7status;
	private String orderHl7message;
	private Integer labCustomerId;
	private Integer uploadStatus;
	private Integer visitDetailId;
	private int detailType;
	private String labOrderNumber;
	private String failedOrderPath;
	private String errorMessage;
	private Integer resultInput;
	private Date labResultDate;
	private String orderNumberRef;
	private String ormMessage;
	private String orderDetailNumber;
	
	public LabOrderDetail() {
	}

	public LabOrderDetail(int labOrderId, int labPackageId,
			String labPackageName, String accessionNumber, int procedureId,
			String procedureName, int quantity, BigDecimal netAmount,
			int netAmountSign, boolean confirmed, int paymentStatus) {
		this.labOrderId = labOrderId;
		this.labPackageId = labPackageId;
		this.labPackageName = labPackageName;
		this.accessionNumber = accessionNumber;
		this.procedureId = procedureId;
		this.procedureName = procedureName;
		this.quantity = quantity;
		this.netAmount = netAmount;
		this.netAmountSign = netAmountSign;
		this.confirmed = confirmed;
		this.paymentStatus = paymentStatus;
	}

	public LabOrderDetail(int labOrderId, int labPackageId,
			String labPackageName, String accessionNumber, int procedureId,
			String procedureName, BigDecimal listedCashPrice, int quantity,
			BigDecimal netAmount, int netAmountSign, boolean confirmed,
			int paymentStatus, Integer orderReportStatus,
			byte[] orderReportAttached, String remark, Boolean deleted) {
		this.labOrderId = labOrderId;
		this.labPackageId = labPackageId;
		this.labPackageName = labPackageName;
		this.accessionNumber = accessionNumber;
		this.procedureId = procedureId;
		this.procedureName = procedureName;
		this.listedCashPrice = listedCashPrice;
		this.quantity = quantity;
		this.netAmount = netAmount;
		this.netAmountSign = netAmountSign;
		this.confirmed = confirmed;
		this.paymentStatus = paymentStatus;
		this.orderReportStatus = orderReportStatus;
		this.orderReportAttached = orderReportAttached;
		this.remark = remark;
		this.deleted = deleted;
	}

	public Integer getLabOrderDetailId() {
		return this.labOrderDetailId;
	}

	public void setLabOrderDetailId(Integer labOrderDetailId) {
		this.labOrderDetailId = labOrderDetailId;
	}

	public int getLabOrderId() {
		return this.labOrderId;
	}

	public void setLabOrderId(int labOrderId) {
		this.labOrderId = labOrderId;
	}

	public int getLabPackageId() {
		return this.labPackageId;
	}

	public void setLabPackageId(int labPackageId) {
		this.labPackageId = labPackageId;
	}

	public String getLabPackageName() {
		return this.labPackageName;
	}

	public void setLabPackageName(String labPackageName) {
		this.labPackageName = labPackageName;
	}

	public String getAccessionNumber() {
		return this.accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public int getProcedureId() {
		return this.procedureId;
	}

	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}

	public String getProcedureName() {
		return this.procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public BigDecimal getListedCashPrice() {
		return this.listedCashPrice;
	}

	public void setListedCashPrice(BigDecimal listedCashPrice) {
		this.listedCashPrice = listedCashPrice;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getNetAmount() {
		return this.netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public int getNetAmountSign() {
		return this.netAmountSign;
	}

	public void setNetAmountSign(int netAmountSign) {
		this.netAmountSign = netAmountSign;
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public int getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getOrderReportStatus() {
		return this.orderReportStatus;
	}

	public void setOrderReportStatus(Integer orderReportStatus) {
		this.orderReportStatus = orderReportStatus;
	}

	public byte[] getOrderReportAttached() {
		return this.orderReportAttached;
	}

	public void setOrderReportAttached(byte[] orderReportAttached) {
		this.orderReportAttached = orderReportAttached;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderHl7status() {
		return orderHl7status;
	}

	public void setOrderHl7status(int orderHl7status) {
		this.orderHl7status = orderHl7status;
	}

	public String getOrderHl7message() {
		return orderHl7message;
	}

	public void setOrderHl7message(String orderHl7message) {
		this.orderHl7message = orderHl7message;
	}

	public Integer getLabCustomerId() {
		return labCustomerId;
	}

	public void setLabCustomerId(Integer labCustomerId) {
		this.labCustomerId = labCustomerId;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	public boolean isSubmitAction() {
		 LabOrderStatus labOrderStatus = LabOrderStatus.getLabOrderStatus(orderStatus);
		 if (labOrderStatus != null) {
			 return labOrderStatus == LabOrderStatus.PN || labOrderStatus == LabOrderStatus.PS; 
		 }
		 return false;
	}

	public Integer getVisitDetailId() {
		return visitDetailId;
	}

	public void setVisitDetailId(Integer visitDetailId) {
		this.visitDetailId = visitDetailId;
	}

	public int getDetailType() {
		return detailType;
	}

	public void setDetailType(int detailType) {
		this.detailType = detailType;
	}

	public String getLabOrderNumber() {
		return labOrderNumber;
	}

	public void setLabOrderNumber(String labOrderNumber) {
		this.labOrderNumber = labOrderNumber;
	}

	public String getFailedOrderPath() {
		return failedOrderPath;
	}

	public void setFailedOrderPath(String failedOrderPath) {
		this.failedOrderPath = failedOrderPath;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getResultInput() {
		return resultInput;
	}

	public void setResultInput(Integer resultInput) {
		this.resultInput = resultInput;
	}

	public Date getLabResultDate() {
		return labResultDate;
	}

	public void setLabResultDate(Date labResultDate) {
		this.labResultDate = labResultDate;
	}

	public String getOrderNumberRef() {
		return orderNumberRef;
	}

	public void setOrderNumberRef(String orderNumberRef) {
		this.orderNumberRef = orderNumberRef;
	}

	public String getOrmMessage() {
		return ormMessage;
	}

	public void setOrmMessage(String ormMessage) {
		this.ormMessage = ormMessage;
	}

	public String getOrderDetailNumber() {
		return orderDetailNumber;
	}

	public void setOrderDetailNumber(String orderDetailNumber) {
		this.orderDetailNumber = orderDetailNumber;
	}
}