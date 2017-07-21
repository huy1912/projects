package com.iis.rims.lab.quantum.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iis.rims.lab.quantum.AppConfig;
import com.iis.rims.lab.quantum.EmailService;

@Component
public class OrderStatus {

	@Autowired
	private EmailService emailService;
	
	public OrderStatus() {
		AppConfig.LOGGER.info("construct order status....");
	}
	@Scheduled(cron = "0/5 * * * * *")
	public void checkOrderStatus() {
		AppConfig.LOGGER.info("Check order status");
		emailService.sendEmail();
		AppConfig.LOGGER.info("Finished email...");
	}
	
	@Scheduled(cron = "0/10 * * * * *")
	public void checkReportStatus() {
		AppConfig.LOGGER.info("Check report status");
	}
}
