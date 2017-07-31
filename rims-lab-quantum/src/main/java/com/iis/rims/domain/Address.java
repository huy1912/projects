package com.iis.rims.domain;
// default package
// Generated May 19, 2013 1:12:07 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Address generated by hbm2java
 */
// Last OBJECTTYPE = 56

public class Address extends BaseDomain {

	public static int objectTypeId = 21;
	public static String objectTypeStr = "Address";
	
	private int addressId;
	private int organizationId;
	private int masterTypeId;
	private int masterId;
	private int addressType;
	private String address1;
	private String address2;
	private String city;
	private String regionState;
	private String postalCode;
	private int countryId;
	private String remarks;

	public Address() {
	}

	public Address(int addressId, int organizationId, int masterTypeId,
			int masterId, int addressType, String address1, String address2,
			int countryId) {
		this.addressId = addressId;
		this.organizationId = organizationId;
		this.masterTypeId = masterTypeId;
		this.masterId = masterId;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.countryId = countryId;
	}

	public Address(int addressId, int organizationId, int masterTypeId,
			int masterId, int addressType, String address1, String address2,
			String city, String regionState, String postalCode, int countryId,
			String remarks, boolean entityStatus, int createdBy,
			Date createdDate, int lastUpdatedBy, Date lastUpdatedDate) {
		this.addressId = addressId;
		this.organizationId = organizationId;
		this.masterTypeId = masterTypeId;
		this.masterId = masterId;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.regionState = regionState;
		this.postalCode = postalCode;
		this.countryId = countryId;
		this.remarks = remarks;
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getMasterTypeId() {
		return this.masterTypeId;
	}

	public void setMasterTypeId(int masterTypeId) {
		this.masterTypeId = masterTypeId;
	}

	public int getMasterId() {
		return this.masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getAddressType() {
		return this.addressType;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegionState() {
		return this.regionState;
	}

	public void setRegionState(String regionState) {
		this.regionState = regionState;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getObjectTypeId() {
		return objectTypeId;
	}

	public String getObjectTypeStr() {
		return objectTypeStr;
	}

	public String getFullTextAddress() {
		return String.format("%s, %s, %s, %s", address1, address2, city, postalCode);
	}	
}
