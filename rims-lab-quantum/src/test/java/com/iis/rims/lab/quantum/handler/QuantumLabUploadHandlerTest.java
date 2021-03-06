package com.iis.rims.lab.quantum.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.SortDirection;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.lab.quantum.orm.MSG;

public class QuantumLabUploadHandlerTest {

	@Test
	public void testConvertToOrderMessageIntegerLabOrderDetailLabOrderStatus() throws Exception {
		LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
		List<LabOrderDetail> list = labOrderDetailDAO.findByCriteria("labOrderDetailId", SortDirection.ASC,
				Restrictions.eq("orderStatus", LabOrderStatus.PN.ordinal()),
				Restrictions.eq("labCustomerId", 67),
				Restrictions.isNull("labOrderNumber"));
		System.err.println(list.size());
		Map<Integer, List<LabOrderDetail>> orders = new LinkedHashMap<>();
		// Split the order
		for (LabOrderDetail detail : list) {
			Integer labOrderId = detail.getLabOrderId();
			List<LabOrderDetail> details = orders.get(labOrderId);
			if (details == null) {
				details = new ArrayList<>();
				orders.put(labOrderId, details);
			}
			details.add(detail);
		}
		
		System.err.println(orders.size());
		
		for (Entry<Integer, List<LabOrderDetail>> entry : orders.entrySet()) {
			MSG orderMessage = QuantumLabUploadHandler.convertToOrderMessage(entry.getKey(), entry.getValue(), LabOrderStatus.PN);
		}
	}

}
