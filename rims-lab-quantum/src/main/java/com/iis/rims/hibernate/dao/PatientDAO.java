package com.iis.rims.hibernate.dao;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.Patient;

public class PatientDAO extends BaseDAO<Patient, Integer> {

	public Patient findByPatientId(String nricFinNumber) {
		return findByUnique("nricFinNumber", nricFinNumber);
	}
}
