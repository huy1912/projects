package com.iis.rims.lab.quantum;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DevelopmentConfig {

	@EventListener
	public void handleContextChanged(ApplicationReadyEvent event) {
		System.err.println(event);
	}
}
