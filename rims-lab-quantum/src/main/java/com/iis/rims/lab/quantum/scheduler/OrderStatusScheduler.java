package com.iis.rims.lab.quantum.scheduler;

<<<<<<< Upstream, based on origin/master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iis.rims.lab.quantum.AppConfig;
import com.iis.rims.lab.quantum.service.OrderService;

@Component
public class OrderStatusScheduler {

	@Autowired
	private OrderService orderService;
	
	public OrderStatusScheduler() {
		AppConfig.LOGGER.info("construct order status....");
	}
	
<<<<<<< HEAD
	@Scheduled(cron = "${push.orders.cron}")
=======
	@Scheduled(cron = "${push.orders.cron}")
=======
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.common.SortDirection;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.lab.quantum.AppConfig;
import com.iis.rims.lab.quantum.EmailService;
import com.iis.rims.lab.quantum.handler.QuantumLabUploadHandler;
import com.iis.rims.lab.quantum.message.EncodeMessage;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.service.OrderService;

@Component
public class OrderStatusScheduler {

	@Autowired
	private OrderService orderService;
	
	public OrderStatusScheduler() {
		AppConfig.LOGGER.info("construct order status....");
	}
	
//	@Scheduled(cron = "0/10 * * * * *")
	// TODO Move to properties file.
>>>>>>> 29bf08f lab
>>>>>>> branch 'master' of https://github.com/huy1912/projects.git
	public void pushOrders() throws Exception {
		orderService.pushOrders();
	}

//	@Scheduled(cron = "0/5 * * * * *")
	public void retrieveResults() {
		AppConfig.LOGGER.info("Check order status");
	}
	
//	@Scheduled(cron = "0/10 * * * * *")
	public void retrievePDFReports() {
		AppConfig.LOGGER.info("Check report status");
	}
}
