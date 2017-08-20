package com.iis.rims.lab.quantum.scheduler;

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
	
	@Scheduled(cron = "${push.orders.cron}")
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
