package com.iis.rims.domain;

import java.util.Date;

public class LabProcedure extends BaseDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static int OBJECTTYPE = 18;
	public static String OBJECTTYPESTR = "LabProcedure";
	
	private int labProcedureId;
	private int organizationId;
	private int customerId;
	private int labProfileId;
	private int procedureId;
	
	public LabProcedure() {
	}

	public LabProcedure(int labProcedureId, int organizationId, int customerId,
			int labProfileId, int procedureId, Boolean entityStatus, int createdBy,
			Date createdDate, int lastUpdatedBy, Date lastUpdatedDate) {
		this.labProcedureId = labProcedureId;
		this.organizationId = organizationId;
		this.customerId = customerId;
		this.labProfileId = labProfileId;
		this.procedureId = procedureId;
		this.setEntityStatus(entityStatus);
		this.setCreatedBy(createdBy);
		this.setCreatedDate(createdDate);
		this.setLastUpdatedBy(lastUpdatedBy);
		this.setLastUpdatedDate(lastUpdatedDate);
	}

	public int getLabProcedureId() {
		return labProcedureId;
	}

	public void setLabProcedureId(int labProcedureId) {
		this.labProcedureId = labProcedureId;
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

	public int getLabProfileId() {
		return labProfileId;
	}

	public void setLabProfileId(int labProfileId) {
		this.labProfileId = labProfileId;
	}

	public int getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}
	
}