package com.iis.rims.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.iis.rims.utils.RimsStringBuilder;

@JsonIgnoreProperties(value = {"createdBy", "createdDate", "lastUpdatedBy", "lastUpdatedDate"})
@JsonInclude(value = Include.NON_NULL)
public abstract class BaseDomain extends RimsStringBuilder implements Serializable {
	
	private Boolean entityStatus;
	private Integer createdBy;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdDate;
	private Integer lastUpdatedBy;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastUpdatedDate;
	private int nextRunningNumber = 0;
	
	private Boolean deleted = false;
	
	public Boolean isEntityStatus() {
		return this.entityStatus != null ? this.entityStatus : false;
	}

	public void setEntityStatus(Boolean entityStatus) {
		this.entityStatus = entityStatus;
	}

	public Integer getCreatedBy() {
		return this.createdBy != null ? this.createdBy : 0;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy != null ? this.lastUpdatedBy : 0;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public Boolean isDeleted() {
		return this.deleted != null ? this.deleted : false;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public int getNextRunningNumber() {
		return nextRunningNumber;
	}

	public void setNextRunningNumber(Integer nextRunningNumber) {
		this.nextRunningNumber = nextRunningNumber;
	}

	@Override
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}