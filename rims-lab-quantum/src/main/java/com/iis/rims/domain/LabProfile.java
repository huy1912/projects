package com.iis.rims.domain;

import java.io.Serializable;
import java.util.Date;

public class LabProfile extends BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String OBJECTTYPESTR = "LabProfile";
	
	private int labProfileId;
	private int organizationId;
	private int customerId;
	private String profileName;
	private String profileDescription;

	public LabProfile() {
	}
	
	public LabProfile(int labProfileId, int organizationId, int customerId,
			String profileName, Boolean entityStatus, int createdBy,
			Date createdDate, int lastUpdatedBy, Date lastUpdatedDate) {
		this.labProfileId = labProfileId;
		this.organizationId = organizationId;
		this.customerId = customerId;
		this.profileName = profileName;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}
	
	public int getLabProfileId() {
		return labProfileId;
	}

	public void setLabProfileId(int labProfileId) {
		this.labProfileId = labProfileId;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileDescription() {
		return profileDescription;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	
}