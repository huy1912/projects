package com.iis.rims.hibernate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;

import com.iis.rims.common.BaseDAO;
import com.iis.rims.common.ParameterBuilder;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.domain.dto.LabOrderDetailUploadDTO;

public class LabOrderDetailDAO extends BaseDAO<LabOrderDetail, Integer> {

	public List<LabOrderDetail> getLabOrderDetails(Integer labOrderId) {
		return this.findByCriteria(Restrictions.eq("labOrderId", labOrderId));
	}
	
	public List<LabOrderDetailUploadDTO> getLabOrderDetailsForUpload(int labOrderId, int labCustomerId) {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.addParameter("labOrderId", labOrderId)
						.addParameter("labCustomerId", labCustomerId)
						.addParameter("labOrderDetailId", null);
											
		return this.getNamedQuery("RIMS_SP_LabOrderDetailUpload", parameterBuilder, LabOrderDetailUploadDTO.class);
	}
	
	public LabOrderDetailUploadDTO getLabOrderDetailForUpload(int labOrderDetailId) {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		parameterBuilder.addParameter("labOrderId", null)
						.addParameter("labCustomerId", null)
						.addParameter("labOrderDetailId", labOrderDetailId);
											
		return this.getNamedQueryUnique("RIMS_SP_LabOrderDetailUpload", parameterBuilder, LabOrderDetailUploadDTO.class);
	}
	
	public LabOrderDetail getLabOrderDetail(String accessionNumber, int detailType) {
		return findByUnique(Restrictions.eq("accessionNumber", accessionNumber),
							Restrictions.eq("detailType", detailType));
	}
	
	public LabOrderDetail getLabOrderDetailByOrderNumber(String labOrderNumber) {
		return findByUnique(Restrictions.eq("labOrderNumber", labOrderNumber));
	}
	
	public LabOrderDetail getLabOrderDetailByOrderNumberRef(String orderNumberRef) {
		return findByUnique(Restrictions.eq("orderNumberRef", orderNumberRef));
	}
	
	public Map<Integer, Integer> getUploadStatus(List<Integer> labOrderDetailIds) {
		List<LabOrderDetail> list = this.findByCriteria(Restrictions.in("labOrderDetailId", labOrderDetailIds));
		Map<Integer, Integer> statuses = new HashMap<Integer, Integer>();
		for (LabOrderDetail labOrderDetail : list) {
			statuses.put(labOrderDetail.getLabOrderDetailId(), labOrderDetail.getUploadStatus());
		}
		return statuses;
	}
}
