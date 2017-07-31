package com.iis.rims.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.domain.RunningNumber;
import com.iis.rims.domain.dto.RunningNumberDTO;

public class RunningNumberDAO extends BaseDAO<RunningNumber, Integer> {
	public RunningNumberDTO getRunningNumber(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber) {
		return getRunningNumber(organizationId, branchId, runningNumber, 0, 0);
	}
	
	public RunningNumberDTO getRunningNumber(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId) {
		return getRunningNumber(organizationId, branchId, runningNumber, serviceClassId, 0);
	}
	
	public RunningNumberDTO getRunningNumber(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId, int serviceId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			RunningNumberDTO obj = (RunningNumberDTO)session.getNamedQuery("getRunningNumber")
					.setParameter("organizationId", organizationId)
					.setParameter("branchId", branchId)
					.setParameter("runningNumberType", runningNumber.getRunningNumberType())
					.setParameter("serviceClassId", serviceClassId)
					.setParameter("serviceId", serviceId)
					.setParameter("servicePrefixId", 0)
					.setParameter("updateImmediate", 0)
					.setParameter("invoiceDate", null)
					.setParameter("IsYearRolled", runningNumber.isYearRollSet())
					.setResultTransformer(Transformers.aliasToBean(RunningNumberDTO.class))
					.uniqueResult();
			transaction.commit();
			return obj;
		}
		catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
	
	public RunningNumberDTO getRunningNumber(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId, 
			int serviceId, int servicePrefixId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			RunningNumberDTO obj = (RunningNumberDTO)session.getNamedQuery("getRunningNumber")
					.setParameter("organizationId", organizationId)
					.setParameter("branchId", branchId)
					.setParameter("runningNumberType", runningNumber.getRunningNumberType())
					.setParameter("serviceClassId", serviceClassId)
					.setParameter("serviceId", serviceId)
					.setParameter("servicePrefixId", servicePrefixId)
					.setParameter("updateImmediate", 0)
					.setParameter("invoiceDate", null)
					.setParameter("IsYearRolled", runningNumber.isYearRollSet())
					.setResultTransformer(Transformers.aliasToBean(RunningNumberDTO.class))
					.uniqueResult();
			transaction.commit();
			return obj;
		}
		catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
	
	/**
	 * Get the running number and update the latest running number.
	 * @param organizationId
	 * @param branchId
	 * @param runningNumber
	 * @return
	 */
	public RunningNumberDTO getRunningNumberUpdate(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber) {
		return this.getRunningNumberUpdate(organizationId, branchId, runningNumber, 0, 0, 0, true, null);
	}
	
	public RunningNumberDTO getRunningNumberUpdate(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, String date) {
		return this.getRunningNumberUpdate(organizationId, branchId, runningNumber, 0, 0, 0, true, date);
	}
	
	public RunningNumberDTO getRunningNumberUpdate(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId, 
			int serviceId, int servicePrefixId, boolean isUpdateImmediate, String date) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			RunningNumberDTO obj = (RunningNumberDTO)session.getNamedQuery("getRunningNumber")
					.setParameter("organizationId", organizationId)
					.setParameter("branchId", branchId)
					.setParameter("runningNumberType", runningNumber.getRunningNumberType())
					.setParameter("serviceClassId", serviceClassId)
					.setParameter("serviceId", serviceId)
					.setParameter("servicePrefixId", servicePrefixId)
					.setParameter("updateImmediate", isUpdateImmediate ? 1 : 0)
					.setParameter("invoiceDate", date)
					.setParameter("IsYearRolled", runningNumber.isYearRollSet())
					.setResultTransformer(Transformers.aliasToBean(RunningNumberDTO.class))
					.uniqueResult();
			transaction.commit();
			return obj;
		}
		catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
	
	public RunningNumberDTO validateRunningNumber(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId, 
			int serviceId, int servicePrefixId, int currnetRunningNumber) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			RunningNumberDTO obj = (RunningNumberDTO)session.getNamedQuery("validateRunningNumber")
					.setParameter("organizationId", organizationId)
					.setParameter("branchId", branchId)
					.setParameter("runningNumberType", runningNumber.getRunningNumberType())
					.setParameter("serviceClassId", serviceClassId)
					.setParameter("serviceId", serviceId)
					.setParameter("servicePrefixId", servicePrefixId)
					.setParameter("IsYearRolled", runningNumber.isYearRollSet())
					.setParameter("nextRunningNumberIn", currnetRunningNumber)
					.setResultTransformer(Transformers.aliasToBean(RunningNumberDTO.class))
					.uniqueResult();
			transaction.commit();
			return obj;
		}
		catch (Exception ex) {
			transaction.rollback();
			throw ex;
		}
	}
	
	public String getRunningNumberCode(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber) {
		return getRunningNumberCode(organizationId, branchId, runningNumber, 0, 0);
	}
	
	public String getRunningNumberCode(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId) {
		return getRunningNumberCode(organizationId, branchId, runningNumber, serviceClassId, 0);
	}
	
	public String getRunningNumberCode(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId, int serviceId) {
		return getRunningNumber(organizationId, branchId, runningNumber, serviceClassId, serviceId).getRunningNumberCode();
	}
	
	public String validateRunningNumberCode(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId,
			int serviceId, int servicePrefixId, String nextRunningNumberCode, int nextRunningNumber) {
		return validateRunningNumber(organizationId, branchId, runningNumber, serviceClassId, serviceId,
				servicePrefixId, nextRunningNumber).getRunningNumberCode();
	}
	
	public String getRunningNumberCode(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId,
			int serviceId, int servicePrefixId) {
		return getRunningNumber(organizationId, branchId, runningNumber, serviceClassId, serviceId, servicePrefixId)
				.getRunningNumberCode();
	}
	
	public RunningNumberDTO getRunningNumberObjectWithUpdate(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId,
			int serviceId, int servicePrefixId) {
		RunningNumberDTO dto = getRunningNumber(organizationId, branchId, runningNumber, 
				serviceClassId, serviceId, servicePrefixId);
		return dto;
	}
	
	public RunningNumberDTO getRunningNumberObject(int organizationId, int branchId, 
			com.iis.rims.common.RIMSConstants.RunningNumber runningNumber, int serviceClassId,
			int serviceId, int servicePrefixId) {
		RunningNumberDTO dto = getRunningNumber(organizationId, branchId, runningNumber, 
				serviceClassId, serviceId, servicePrefixId);
		return dto;
	}
	
	public static void main(String[] args) {
		RunningNumberDAO dao = new RunningNumberDAO();
		RunningNumberDTO obj = dao.getRunningNumber(2, 0, com.iis.rims.common.RIMSConstants.RunningNumber.EXAMINATIONPROCEDURE_CODE,
				0, 9, 14);
		System.err.println(obj.getRunningNumberCode());
		System.err.println(obj.getLatestRunningNumber());
	}
}
