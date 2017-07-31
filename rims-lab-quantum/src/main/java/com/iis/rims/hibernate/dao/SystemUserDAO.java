package com.iis.rims.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.SystemUser;

public class SystemUserDAO extends BaseDAO<SystemUser, Integer> {
		
	public SystemUser getLoggedInUserName(String userName) {
		SystemUser systemUser = this.findByUnique(
			Restrictions.eq("loginName", userName), 
			Restrictions.eq("entityStatus", true));
		return systemUser;
	}
	public SystemUser getUserByEmail(String email) {
		SystemUser user = this.findByUnique(
			Restrictions.eq("email", email),
			Restrictions.eq("entityStatus", true));
		if(StringUtils.isEmpty(user)){
			return user;
		}
		return user;
	}
	
	public List<SystemUser> getAllActiveUsers(Integer organizationId) {
		return this.findByCriteria(
			Restrictions.eq("organizationId", organizationId),
			Restrictions.eq("entityStatus", true));
	}
}
