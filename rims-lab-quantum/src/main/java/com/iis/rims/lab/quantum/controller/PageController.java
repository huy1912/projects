package com.iis.rims.lab.quantum.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.ArrayOfString;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.lab.quantum.message.EncodeMessage;

@Controller
public class PageController {
	
	@Autowired
	private LISIntegrationWebserviceSoap integrationWebserviceSoap;
	
	@Value("${lis.username}")
	private String username;
	
	@Value("${lis.password}")
	private String password;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		model.put("appName", "rims lab");
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		System.err.println("Welcome page...............");
		model.put("message", "Welcome");
		return "welcome";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String submitOrder(@RequestBody SubmitOrder order) {
		try {
			String xmlData = EncodeMessage.encodeMsg(order);
			String ret = integrationWebserviceSoap.pushOrder(xmlData , username, password);
			return ret;
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(value = "/getResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getResult(@RequestBody String orderNumber) {
		ArrayOfString results = integrationWebserviceSoap.getResultValues(orderNumber);
		return results.getString();
	}
	
	@RequestMapping(value = "/getReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getReport(HttpServletResponse response, @RequestBody String orderNumber) throws Exception {
		byte[] buffer = integrationWebserviceSoap.getReportPDF(orderNumber, username, password);
		IOUtils.write(buffer, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
        response.flushBuffer();
//		System.err.println(orderNumber);
//		return orderNumber;
	}
}
