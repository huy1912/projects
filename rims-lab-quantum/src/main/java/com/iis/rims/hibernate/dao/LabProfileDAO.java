package com.iis.rims.hibernate.dao;

import java.util.List;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.domain.LabProfile;

public class LabProfileDAO extends BaseDAO<LabProfile, Integer> {
	public LabProfile getLabProfile(int customerId, int procedureId) {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		
		parameterBuilder.addParameter("customerId", customerId)
						.addParameter("procedureId", procedureId);
		List<LabProfile> list = getNamedQuery("QUERY_GETLABPROCEDURE", parameterBuilder);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
}
