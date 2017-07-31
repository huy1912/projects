package com.iis.rims.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.ContactInfo;
import com.iis.rims.domain.Patient;

public class ContactInfoDAO extends BaseDAO<ContactInfo, Integer> {
	public List<ContactInfo> findByPatientId(Integer patientId) {
		return findByCriteria(
				Restrictions.eq("masterId", patientId),
				Restrictions.eq("masterTypeId", Patient.OBJECTTYPEID),
				Restrictions.eq("entityStatus", true)
		);
	}
}
