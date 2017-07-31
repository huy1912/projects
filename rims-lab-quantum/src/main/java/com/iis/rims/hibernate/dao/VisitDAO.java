package com.iis.rims.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.domain.Visit;

public class VisitDAO extends BaseDAO<Visit, Integer> {
	
	public Visit findRecentVisitByRegId(Integer regId){
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			Query namedQuery = session.getNamedQuery("recentVisitByRegId");
			namedQuery.setInteger("regId", regId);
			namedQuery.setDate("currentDate", new Date());
			List<Visit> visits = namedQuery.list();
			transaction.commit();
			if (visits != null && visits.size() >0) {				
				return visits.get(0);
			}
		}
		catch(Exception ex) {
			transaction.rollback();
			throw new RuntimeException(ex);
		}
		
		return null;
	}
	public Visit findVisitByRegId(Integer regId){
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			Query namedQuery = session.getNamedQuery("findVisitByRegId");
			namedQuery.setInteger("regId", regId);
			//namedQuery.setDate("currentDate", new Date());
			List<Visit> visits = namedQuery.list();
			transaction.commit();
			if (visits != null && visits.size() >0) {				
				return visits.get(0);
			}
		}
		catch(Exception ex) {
			transaction.rollback();
			throw new RuntimeException(ex);
		}
		
		return null;
	}
	
	@Deprecated
	public boolean isPastVisitByRegId(Integer regId){
//		Session session = this.getSession();
//		Transaction transaction = session.beginTransaction();
//		try {
//			
//			Query namedQuery = session.getNamedQuery("pastVisitByRegId");
//			namedQuery.setInteger("regId", regId);
//			namedQuery.setDate("currentDate", new Date());
//			List<Visit> visits = namedQuery.list();
//			transaction.commit();
//			if (visits != null && visits.size() >0) {				
//				return visits.get(0);
//			}
//		}
//		catch(Exception ex) {
//			transaction.rollback();
//			throw new RuntimeException(ex);
//		}
//		
//		return null;
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.addParameter("regId", regId)
						.addParameter("currentDate", new Date());
		List<Visit> visits = getNamedQuery("pastVisitByRegId", parameterBuilder);
		return !visits.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public Visit findRecentVisitById(Integer visitId){
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			Query namedQuery = session.getNamedQuery("recentVisitById");
			namedQuery.setInteger("visitId", visitId);
			List<Visit> visits = namedQuery.list();
			transaction.commit();
			
			if (visits != null && visits.size() >0) {				
				return visits.get(0);
			}
		}
		catch(Exception ex) {
			transaction.rollback();
			throw ex;
		}
		
		return null;
	}
	
	public Visit findRecentVisitByMaxId(Integer regId){
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			Query namedQuery = session.getNamedQuery("recentVisitByMaxId");
			namedQuery.setInteger("regId", regId);
			List<Visit> visits = namedQuery.list();
			transaction.commit();
			
			if (visits != null && visits.size() >0) {				
				return visits.get(0);
			}
		}
		catch(Exception ex) {
			transaction.rollback();
			throw ex;
		}
		
		return null;
	}
	
	public List<Visit> findAllMortalityPatientVisit(String nricNumber){
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query namedQuery = session.getNamedQuery("findAllMortalityPatientVisit");
			namedQuery.setString("nricNumber", nricNumber);
			List<Visit> visits = namedQuery.list();
			transaction.commit();
			return visits;
		}
		catch(Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
	
	public static void main(String[] args) {
		VisitDAO visitDAO = new VisitDAO();
		System.err.println(visitDAO.findRecentVisitByRegId(8));
	}
}
