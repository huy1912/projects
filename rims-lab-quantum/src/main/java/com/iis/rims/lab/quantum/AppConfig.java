package com.iis.rims.lab.quantum;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.lab.quantum.interceptor.OutSoapInterceptor;
import com.iis.rims.lab.quantum.log.LabLoggingInInterceptor;
import com.iis.rims.lab.quantum.log.LabLoggingOutInterceptor;

@Configuration
public class AppConfig {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Value("${lis.wsdl}")
	private String lisWsdl;
	
	@Value("${lab.local.dir}")
	private String labLocalDir;
	
	private ThreadLocal<String> orderNumberThreadLocal = new ThreadLocal<>();
	
	@PostConstruct
	public void init() {
		mkdir(labLocalDir + "/out");
		mkdir(labLocalDir + "/in");
		mkdir(labLocalDir + "/pdf");
	}
	
	private void mkdir(String dir) {
		File f = new File(dir);
		if (!f.exists()) {
    		f.mkdir();
    	}
	}
	/*
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
	    SpringBus springBus = new SpringBus();
//	    LoggingFeature logFeature = new LoggingFeature();
//	    logFeature.setPrettyLogging(true);
//	    logFeature.initialize(springBus);
//	    springBus.getFeatures().add(logFeature);
	    springBus.getOutInterceptors().add(new OutSoapInterceptor());
	    springBus.getOutInterceptors().add(logOutInterceptor());
	    springBus.getOutFaultInterceptors().add(logOutInterceptor());
	    springBus.getInInterceptors().add(logInInterceptor());
	    return springBus;
	}
	*/
	
//	@Bean
	public AbstractLoggingInterceptor logOutInterceptor() {
	    LoggingOutInterceptor logOutInterceptor = new LabLoggingOutInterceptor(labLocalDir + "/out", orderNumberThreadLocal);
//	    logOutInterceptor.setPrettyLogging(true);
	    return logOutInterceptor;
	}
	
//	@Bean
	public AbstractLoggingInterceptor logInInterceptor() {
	    LoggingInInterceptor logInInterceptor = new LabLoggingInInterceptor(labLocalDir + "/in", orderNumberThreadLocal);
//	    logInInterceptor.setPrettyLogging(true);
	    return logInInterceptor;
	}
	
	@Bean
	public LISIntegrationWebserviceSoap createLISIntegrationService() {
		return createService(LISIntegrationWebserviceSoap.class, lisWsdl);
	}
	
	private <T> T createService(Class<T> clazz, String address) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(clazz);
		factory.setAddress(address);
		Map<String, Object> properties = new HashMap();
		Map<String, String> ns = new HashMap<>();
		ns.put("ws", "http://ws.connectors.connect.mirth.com");
		properties.put("soap.env.ns.map", ns);
		factory.setProperties(properties);
		factory.getOutInterceptors().add(new OutSoapInterceptor());
		factory.getOutInterceptors().add(logOutInterceptor());
		factory.getInInterceptors().add(logInInterceptor());
		T service = (T) factory.create();
		return service;
	}
}
