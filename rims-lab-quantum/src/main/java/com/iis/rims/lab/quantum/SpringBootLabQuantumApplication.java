package com.iis.rims.lab.quantum;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tempuri.ArrayOfString;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.common.SortDirection;
import com.iis.rims.common.RIMSConstants.LabOrderStatus;
import com.iis.rims.domain.LabOrderDetail;
import com.iis.rims.hibernate.dao.LabOrderDetailDAO;
import com.iis.rims.lab.quantum.handler.QuantumLabUploadHandler;
import com.iis.rims.lab.quantum.message.DecodeMessage;
import com.iis.rims.lab.quantum.message.EncodeMessage;
import com.iis.rims.lab.quantum.orm.MSG;


//@SpringBootApplication(scanBasePackages = {"hello", "spring.boot.example"})
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringBootLabQuantumApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLabQuantumApplication.class, args);
//		HelloWorldService service = applicationContext.getBean(HelloWorldService.class);
//		System.err.println(service.sayHello("John"));
		
//		RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
//		Greeting greeting = restTemplate.getForObject("http://localhost:8080/boot/greeting", Greeting.class);
//		System.err.println(greeting);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootLabQuantumApplication.class);
	}
	@Bean
	public CommandLineRunner run(final LISIntegrationWebserviceSoap integrationWebserviceSoap) throws Exception {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				ArrayOfString results = integrationWebserviceSoap.getResultValues("10000006");
				if (results != null && !results.getString().isEmpty()) {
					for (String s : results.getString()) {
						MSG msg = DecodeMessage.decodeResults(s);
						System.err.println(msg);
					}
				}
//				System.err.println(results);
//				byte[] data = integrationWebserviceSoap.getReportPDF("10000007", "RenalTeam", "renal@123");
//				if (data != null & data.length > 0) {
//					FileUtils.writeByteArrayToFile(new File("report2.pdf"), data);
//				}
				
				if (true) {
					return;
				}
				//String xmlData = "<![CDATA[" + EncodeMessage.encodeMsg() + "]]>";
				String xmlData = EncodeMessage.encodeMsg();
				String ret = integrationWebserviceSoap.pushOrder(xmlData , "RenalTeam", "renal@123");
				if (true) {
					return;
				}
//				System.err.println(ret);
				LabOrderDetailDAO labOrderDetailDAO = new LabOrderDetailDAO();
				List<LabOrderDetail> list = labOrderDetailDAO.findByCriteria("labOrderDetailId", SortDirection.ASC,
						Restrictions.eq("orderStatus", LabOrderStatus.PN.ordinal()),
						Restrictions.eq("labCustomerId", 65),
						Restrictions.or(Restrictions.isNull("labOrderNumber"), Restrictions.in("uploadStatus", new Integer[] {null, 2}))
						); // 2 : FAILED upload
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
				
//				System.err.println(orders.size());
//				
//				for (Entry<Integer, List<LabOrderDetail>> entry : orders.entrySet()) {
//					MSG orderMessage = QuantumLabUploadHandler.convertToOrderMessage(entry.getKey(), entry.getValue(), LabOrderStatus.PN);
//					String xmlData = EncodeMessage.encodeMsg(orderMessage);
//					String ret = integrationWebserviceSoap.pushOrder(xmlData , "RenalTeam", "renal@123");
//					System.err.println(ret);
//				}
				
			}
		};
	}
}
