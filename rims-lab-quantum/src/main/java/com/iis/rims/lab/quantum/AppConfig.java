package com.iis.rims.lab.quantum;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.lab.quantum.interceptor.OutSoapInterceptor;
import com.iis.rims.lab.quantum.log.LabLoggingInInterceptor;
import com.iis.rims.lab.quantum.log.LabLoggingOutInterceptor;

@Configuration
public class AppConfig {
	
	@Value("${lis.wsdl}")
	private String lisWsdl; 
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
	    SpringBus springBus = new SpringBus();
//	    LoggingFeature logFeature = new LoggingFeature();
//	    logFeature.setPrettyLogging(true);
//	    logFeature.initialize(springBus);
//	    springBus.getFeatures().add(logFeature);
	    springBus.getInInterceptors().add(logInInterceptor());
	    springBus.getOutInterceptors().add(logOutInterceptor());
	    springBus.getOutInterceptors().add(new OutSoapInterceptor());
	    springBus.getOutFaultInterceptors().add(logOutInterceptor());
	    return springBus;
	}
	
//	@Bean
	public AbstractLoggingInterceptor logOutInterceptor() {
	    LoggingOutInterceptor logOutInterceptor = new LabLoggingOutInterceptor();
	    logOutInterceptor.setPrettyLogging(true);
	    return logOutInterceptor;
	}
	
//	@Bean
	public AbstractLoggingInterceptor logInInterceptor() {
	    LoggingInInterceptor logInInterceptor = new LabLoggingInInterceptor();
	    logInInterceptor.setPrettyLogging(true);
	    return logInInterceptor;
	}
	
	@Bean
	public LISIntegrationWebserviceSoap createLISIntegrationService() {
		//return createService(LISIntegrationWebserviceSoap.class, "http://52.187.20.127/LISIntegrationtesting/LisIntegrationWebservice.asmx?wsdl");
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
//		factory.getInInterceptors().add(new OutSoapInterceptor());
//		factory.getOutInterceptors().add(new LabLoggingOutInterceptor());
		T service = (T) factory.create();
		return service;
	}
}
