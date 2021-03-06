package com.iis.rims.domain;
// default package
// Generated May 19, 2013 1:12:07 PM by Hibernate Tools 4.0.0

/**
 * Currency generated by hbm2java
 */
public class SystemProperty extends BaseDomain implements java.io.Serializable {

	public static int OBJECTTYPE = 9;
	public static String OBJECTTYPESTR = "SystemProperty";
	
	private int systemPropertyId;
	private Integer organizationId;
	private Integer branchId;
	private String propertyName;
	private String propertyValue;
	private String propertyGroup;
	

	public SystemProperty() {
	}


	public int getSystemPropertyId() {
		return systemPropertyId;
	}


	public void setSystemPropertyId(int systemPropertyId) {
		this.systemPropertyId = systemPropertyId;
	}


	public Integer getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}


	public Integer getBranchId() {
		return branchId;
	}


	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}


	public String getPropertyName() {
		return propertyName;
	}


	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	public String getPropertyValue() {
		return propertyValue;
	}


	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}


	public String getPropertyGroup() {
		return propertyGroup;
	}


	public void setPropertyGroup(String propertyGroup) {
		this.propertyGroup = propertyGroup;
	}

	
}
