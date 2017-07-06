package com.iis.rims.lab.quantum;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.tempuri.ArrayOfString;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.lab.quantum.message.EncodeMessage;


//@SpringBootApplication(scanBasePackages = {"hello", "spring.boot.example"})
@SpringBootApplication
public class SpringBootLabQuantumApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBootLabQuantumApplication.class, args);
//		HelloWorldService service = applicationContext.getBean(HelloWorldService.class);
//		System.err.println(service.sayHello("John"));
		
//		RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
//		Greeting greeting = restTemplate.getForObject("http://localhost:8080/boot/greeting", Greeting.class);
//		System.err.println(greeting);
	}
	
	@Bean
	public CommandLineRunner run(final LISIntegrationWebserviceSoap integrationWebserviceSoap) throws Exception {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				ArrayOfString results = integrationWebserviceSoap.getResultValues("161004337");
				System.err.println(results);
				byte[] data = integrationWebserviceSoap.getReportPDF("161004337", "RenalTeam", "renal@123");
				if (data != null & data.length > 0) {
					FileUtils.writeByteArrayToFile(new File("report2.pdf"), data);
				}
				//String xmlData = "<![CDATA[" + EncodeMessage.encodeMsg() + "]]>";
				String xmlData = EncodeMessage.encodeMsg();
				String ret = integrationWebserviceSoap.pushOrder(xmlData , "RenalTeam", "renal@123");
				System.err.println(ret);
				
			}
		};
	}
}
