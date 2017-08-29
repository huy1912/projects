package com.iis.rims.hibernate.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.common.SortDirection;
import com.iis.rims.domain.LabTestCode;

public class LabTestCodeDAO extends BaseDAO<LabTestCode, Integer> {

	public static class RtLabTestCode {
		private String rtTestCodeColumn;

		public String getRtTestCodeColumn() {
			return rtTestCodeColumn;
		}

		public void setRtTestCodeColumn(String rtTestCodeColumn) {
			this.rtTestCodeColumn = rtTestCodeColumn;
		}

	}
	
	/**
	 * Get the mapping between the remark and rt test column.
	 * @return the mapping.
	 */
	public Map<String, String> getTestCodeColumns() {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		// TODO Need to use the group by on the remark and rt test column to support for multiple lab customers.
		List<LabTestCode> list = getTestCodesInDisplayOrder();
		
		for (LabTestCode labTestCode : list) {
			columns.put(labTestCode.getRemark(), labTestCode.getRtTestCodeColumn());
		}
		
		return columns;
	}

	public List<LabTestCode> getTestCodesInDisplayOrder() {
		return this.findAll("displayOrder", SortDirection.ASC);
	}

	public List<RtLabTestCode> getLabTestCodes() {
		return this.getNamedQuery("GetRTLabTestCodeList", ParameterBuilder.DEFAULT_PARAMETER_BUILDER, RtLabTestCode.class);
	}

	public boolean isExistingTestCode(Integer labTestCodeId, String testCode) {
		int count;
		if (labTestCodeId != 0) {
			count = this.getCount(Restrictions.ne("labTestCodeId", labTestCodeId),
						Restrictions.eq("testCode", testCode));
		}
		else {
			count = this.getCount(Restrictions.eq("testCode", testCode));
		}
		return count > 0;
	}
	
	public List<List<String>> getRtTestCodes(int organiztionId, int customerId, Collection<String> testCodes) {
		List<LabTestCode> list = this.findByCriteria(Restrictions.eq("customerId", customerId),
											Restrictions.in("testCode", testCodes));
		List<List<String>> ret = Arrays.asList((List<String>) CollectionUtils.collect(list, new BeanToPropertyValueTransformer("rtTestCodeColumn")),
				(List<String>) CollectionUtils.collect(list, new BeanToPropertyValueTransformer("testCode")));
		
		return ret;
	}
	
	public List<LabTestCode> getTestCodeRemarks(Collection<String> rtTestCodes) {
		return this.findByCriteria("displayOrder", SortDirection.ASC, Restrictions.in("rtTestCodeColumn", rtTestCodes));
	}
}
