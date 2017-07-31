package com.iis.rims.hibernate.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.DateTimeFormatUtils;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.domain.LabOrder;

public class LabOrderDAO extends BaseDAO<LabOrder, Integer> {
	
	private static final int NUMBER_OF_DATES_SUMMARY_RECORD = 7;

	/**
	 * @param visitId
	 * @param labCustomerId
	 * @return
	 * @deprecated The labCustomerId is not required for the LabOrder.
	 */
	@Deprecated
	public LabOrder getExistingLabOrder(int visitId, int labCustomerId) {
		return findByUnique(Restrictions.eq("visitId", visitId),
							Restrictions.eq("labCustomerId", labCustomerId));
	}
	
	public List<Integer> getPatientHasOrder(List<Integer> patientIds) {
		return this.findProjectByCriteria(Arrays.asList(Restrictions.in("patientId", patientIds)),
				Arrays.asList(Projections.groupProperty("patientId")));
	}
	
	// TODO Need to discuss whether the group by is required or not.
	public Map<Integer, Date> getRecentInvestigationDates(int patientId) {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.addParameter("topRecord", NUMBER_OF_DATES_SUMMARY_RECORD);
		parameterBuilder.addParameter("patientId", patientId);
		List<Object> objects = this.getNamedQuery("RIMS_SP_LabOrderReceived", parameterBuilder);
 
		Map<Integer, Date> orderDateMapping = new LinkedHashMap<Integer, Date>();
		for (Object obj : objects) {
			Object[] arr = (Object[]) obj;
			orderDateMapping.put((Integer)arr[0], (Date)arr[1]);
		}
		
		return orderDateMapping;
	}
	
	public LabOrder getLabOrder(Date orderDate, String nricFinNumber) {
		Date fromDate = DateTimeFormatUtils.removeTime(orderDate);
		Date toDate = DateTimeFormatUtils.plusDays(fromDate, 1);
		return findByUnique(Restrictions.between("orderDate", fromDate, toDate),
				Restrictions.eq("nricFinNumber", nricFinNumber));
	}
}
