package com.iis.rims.lab.quantum;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/webapp/" };
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
//		if (!registry.hasMappingForPattern("/**")) {
//			registry.addResourceHandler("/**").addResourceLocations(
//					CLASSPATH_RESOURCE_LOCATIONS);
//		}
	}
}
