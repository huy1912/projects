package com.iis.rims.hibernate.dao;

import org.junit.Test;

import com.iis.rims.domain.LabProfile;

public class LabProfileDAOTest {

	@Test
	public void testGetLabProfile() {
		LabProfileDAO dao = new LabProfileDAO();
		LabProfile o = dao.getLabProfile(65, 223);
		System.err.println(o.getProfileName());
	}

}
