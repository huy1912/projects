package com.iis.rims.hibernate.dao;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.LookupHeader;


public class LookupHeaderDAO extends BaseDAO<LookupHeader, Integer> {
	public static void main(String[] args) {
		LookupHeaderDAO dao = new LookupHeaderDAO();
		System.err.println(dao.findAll().size());
	}
	
	public LookupHeader getLookupHeaderById(int lookupHeaderId) {
		return this.findByUnique(Restrictions.eq("lookupHeaderId", lookupHeaderId));
	}
	public LookupHeader getLookupHeaderByName(int organizationId, String lookupHeaderName) {
		return this.findByUnique(
				Restrictions.eq("organizationId", organizationId),
				Restrictions.ilike("lookupName", lookupHeaderName));
	}
	
}
