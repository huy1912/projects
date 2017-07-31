package com.iis.rims.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.common.SortDirection;
import com.iis.rims.domain.LookupDetail;

public class LookupDetailDAO extends BaseDAO<LookupDetail, Integer> {
	public List<LookupDetail> getLookupDetail(Integer organizationId) {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.addParameter("organizationId", organizationId);
		return getNamedQuery("getLookupDetail", parameterBuilder);
	}
	
	public LookupDetail getLookupDetailById(int lookupHeaderId, int lookupDetailValue ) {
		List<LookupDetail> list = this.findByCriteria(Restrictions.eq("lookupHeaderId", lookupHeaderId),
			Restrictions.eq("lookupDetailValue", lookupDetailValue));
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	public LookupDetail getLookupDetailById2(int lookupHeaderId) {
		return this.findByUnique(Restrictions.eq("lookupHeaderId", lookupHeaderId));
	}
	
	public List<LookupDetail> getLookupDetailByHeaderId(Integer lookupHeaderId) {
		return this.findByCriteria("lookupDetailValue", SortDirection.ASC,
				Restrictions.eq("lookupHeaderId", lookupHeaderId));
	}
}
