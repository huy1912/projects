package com.iis.rims.hibernate.dao;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.SystemProperty;

public class SystemPropertyDAO extends BaseDAO<SystemProperty, Long> {

	public SystemProperty findByPropertyName(int organizationId,
			String propertyName) {
		return findByUnique(Restrictions.eq("organizationId", organizationId),
				Restrictions.eq("propertyName", propertyName));
	}

	public HashMap<String, String> findSystemProperties() {
		HashMap<String, String> map = new HashMap<String, String>();
		List<SystemProperty> lst = findAll();
		for (SystemProperty p : lst) {
			if (!map.containsKey(p.getPropertyName()))
				map.put(p.getPropertyName(), p.getPropertyValue());
		}
		return map;
	}
	
	public HashMap<String, String> findSystemProperties(int organizationId) {
		HashMap<String, String> map = new HashMap<String, String>();
		List<SystemProperty> lst = findByCriteria(Restrictions.eq("organizationId", organizationId));
		for (SystemProperty p : lst) {
			if (!map.containsKey(p.getPropertyName()))
				map.put(p.getPropertyName(), p.getPropertyValue());
		}
		return map;
	}
}
