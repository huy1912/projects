package com.iis.rims.hibernate.dao;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.Address;
import com.iis.rims.domain.Patient;

public class AddressDAO extends BaseDAO<Address, Integer> {
	
	public Address findPatientAddress(Integer patientId) {
		return findByUnique(
				Restrictions.eq("masterId", patientId),
				Restrictions.eq("masterTypeId", Patient.OBJECTTYPEID)
		);
	}
}
