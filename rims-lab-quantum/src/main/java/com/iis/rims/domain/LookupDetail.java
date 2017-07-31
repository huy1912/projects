package com.iis.rims.domain;
// default package
// Generated May 19, 2013 1:12:07 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * LookupDetail generated by hbm2java
 */
public class LookupDetail extends BaseDomain implements java.io.Serializable {

	public static int OBJECTTYPE = 14;
	public static String OBJECTTYPESTR = "LookupDetail";
	
	private int lookupDetailId;
	private int lookupHeaderId;
	private String lookupDetailName;
	private String lookupDetailDescription;
	private int lookupDetailValue;
	
	private Boolean isInventoryItem;
	private String sapb1InventoryItemCode;
	private String sapb1InventoryItemRemarks;
	public LookupDetail() {
	}

	public LookupDetail(int lookupDetailId, int lookupHeaderId,
			String lookupDetailName, int lookupDetailValue,
			boolean entityStatus, int createdBy, Date createdDate,
			int lastUpdatedBy, Date lastUpdatedDate) {
		this.lookupDetailId = lookupDetailId;
		this.lookupHeaderId = lookupHeaderId;
		this.lookupDetailName = lookupDetailName;
		this.lookupDetailValue = lookupDetailValue;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public LookupDetail(int lookupDetailId, int lookupHeaderId,
			String lookupDetailName, String lookupDetailDescription,
			int lookupDetailValue, boolean entityStatus, int createdBy,
			Date createdDate, int lastUpdatedBy, Date lastUpdatedDate) {
		this.lookupDetailId = lookupDetailId;
		this.lookupHeaderId = lookupHeaderId;
		this.lookupDetailName = lookupDetailName;
		this.lookupDetailDescription = lookupDetailDescription;
		this.lookupDetailValue = lookupDetailValue;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public int getLookupDetailId() {
		return this.lookupDetailId;
	}

	public void setLookupDetailId(int lookupDetailId) {
		this.lookupDetailId = lookupDetailId;
	}

	public int getLookupHeaderId() {
		return this.lookupHeaderId;
	}

	public void setLookupHeaderId(int lookupHeaderId) {
		this.lookupHeaderId = lookupHeaderId;
	}

	public String getLookupDetailName() {
		return this.lookupDetailName;
	}

	public void setLookupDetailName(String lookupDetailName) {
		this.lookupDetailName = lookupDetailName;
	}

	public String getLookupDetailDescription() {
		return this.lookupDetailDescription;
	}

	public void setLookupDetailDescription(String lookupDetailDescription) {
		this.lookupDetailDescription = lookupDetailDescription;
	}

	public int getLookupDetailValue() {
		return this.lookupDetailValue;
	}

	public void setLookupDetailValue(int lookupDetailValue) {
		this.lookupDetailValue = lookupDetailValue;
	}

	public Boolean getIsInventoryItem() {
		return isInventoryItem;
	}

	public void setIsInventoryItem(Boolean isInventoryItem) {
		this.isInventoryItem = isInventoryItem;
	}

	public String getSapb1InventoryItemCode() {
		return sapb1InventoryItemCode;
	}

	public void setSapb1InventoryItemCode(String sapb1InventoryItemCode) {
		this.sapb1InventoryItemCode = sapb1InventoryItemCode;
	}

	public String getSapb1InventoryItemRemarks() {
		return sapb1InventoryItemRemarks;
	}

	public void setSapb1InventoryItemRemarks(String sapb1InventoryItemRemarks) {
		this.sapb1InventoryItemRemarks = sapb1InventoryItemRemarks;
	}
	
}
