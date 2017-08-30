package com.iis.rims.lab.quantum.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tempuri.ArrayOfString;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.SortDirection;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.integration.UploadStatus;
import com.iis.rims.lab.quantum.AppConfig;
import com.iis.rims.lab.quantum.handler.QuantumLabDownloadHandler;
import com.iis.rims.lab.quantum.handler.QuantumLabUploadHandler;
import com.iis.rims.lab.quantum.message.DecodeMessage;
import com.iis.rims.lab.quantum.message.EncodeMessage;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.response.ResponseType;

@Service
public class OrderService {

	@Autowired
	private LISIntegrationWebserviceSoap integrationWebserviceSoap;
	
	@Value("${lis.username}")
	private String username;
	
	@Value("${lis.password}")
	private String password;
	
	@Value("${labCustomerId}")
	private int labCustomerId;
	
	@Value("${lab.local.dir}")
	private String labLocalDir;
	
	public OrderService() {
		AppConfig.LOGGER.info("construct order service....");
	}
	
	public void pushOrders() throws Exception {
		AppConfig.LOGGER.info("push orders");
		LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
		List<LabOrderDetail> list = labOrderDetailDAO.findByCriteria("labOrderDetailId", SortDirection.ASC,
				Restrictions.eq("orderStatus", LabOrderStatus.PN.ordinal()),
				Restrictions.eq("labCustomerId", labCustomerId),
				Restrictions.in("uploadStatus", new Integer[] {null, 2})
//				Restrictions.or(Restrictions.isNull("labOrderNumber"), Restrictions.in("uploadStatus", new Integer[] {null, 2}))
				); // 2 : FAILED upload
		submitOrders(labOrderDetailDAO, list);
		AppConfig.LOGGER.info("Finished pushing orders...");
	}

	public void submitOrders(LabOrderDetailDAO labOrderDetailDAO, List<LabOrderDetail> list) throws Exception {
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
		
		for (Entry<Integer, List<LabOrderDetail>> entry : orders.entrySet()) {
			int labOrderId = entry.getKey();
			List<LabOrderDetail> labOrderDetails = entry.getValue();
			AppConfig.LOGGER.info("Uploading the orders for " + labOrderId);
			for (LabOrderDetail detail : labOrderDetails) {
				AppConfig.LOGGER.info("Uploading the order detail for " + detail.getAccessionNumber());
			}
			MSG orderMessage = QuantumLabUploadHandler.convertToOrderMessage(labOrderId, labOrderDetails, LabOrderStatus.PN);
			String xmlData = EncodeMessage.encodeMsg(orderMessage);
			try {
				String ret = integrationWebserviceSoap.pushOrder(xmlData , username, password);
				AppConfig.LOGGER.info("Upload result is " + ret);
				if (!StringUtils.isEmpty(ret)) {
					ResponseType response = DecodeMessage.decodeMessage(ret, ResponseType.class);
					for (LabOrderDetail detail : labOrderDetails) {
						AppConfig.LOGGER.info("Uploaded successfully the order detail for " + detail.getAccessionNumber());
						detail.setOrderNumberRef(response.getIntegrationHistoryID());
						// Assume the upload status returns with 0
						if ("0".equals(response.getStatus())) {
						}
						detail.setUploadStatus(UploadStatus.UPLOADED.ordinal());
						labOrderDetailDAO.update(detail);
					}
				}
				else {
					for (LabOrderDetail detail : labOrderDetails) {
						AppConfig.LOGGER.error("===Uploaded failed the order detail for " + detail.getAccessionNumber());
						detail.setUploadStatus(UploadStatus.FAILED.ordinal());
						labOrderDetailDAO.update(detail);
					}
				}
				
			}
			catch (Exception ex) {
				for (LabOrderDetail detail : labOrderDetails) {
					AppConfig.LOGGER.error("Uploaded failed the order detail for " + detail.getAccessionNumber());
					detail.setUploadStatus(UploadStatus.FAILED.ordinal());
					labOrderDetailDAO.update(detail);
				}
			}
		}
	}
	
	/**
	 * Get results
	 */
	public void getResults() {
		AppConfig.LOGGER.info("Start getting results");
		LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
		List<LabOrderDetail> list = labOrderDetailDAO.findByCriteria("labOrderDetailId", SortDirection.ASC,
				Restrictions.eq("orderStatus", LabOrderStatus.PN.ordinal()),
				Restrictions.eq("labCustomerId", labCustomerId),
				Restrictions.isNotNull("labOrderNumber"),
				Restrictions.eq("uploadStatus", 1)
				); // 2 : FAILED upload
		for (LabOrderDetail labOrderDetail : list) {
			String labOrderNumber = labOrderDetail.getLabOrderNumber();
			ArrayOfString results = integrationWebserviceSoap.getResultValues(labOrderNumber);
			if (results != null) {
				List<String> resultList = results.getString();
				if (!resultList.isEmpty()) {
					for (String result : resultList) {
						// Log the raw file for processing in the case of FALIED.
						//labLocalDir
						String path = String.format("%s/%s.xml", labLocalDir + "/in", labOrderNumber);
						try {
							FileUtils.writeStringToFile(new File(path) , result, Charset.defaultCharset());
						}
						catch (IOException e) {
							e.printStackTrace();
						}
						QuantumLabDownloadHandler.processResults(result, labOrderNumber, path);
					}
				}
			}
		}
		AppConfig.LOGGER.info("Finished getting results...");
	}
}
