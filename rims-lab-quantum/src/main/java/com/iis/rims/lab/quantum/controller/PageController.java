package com.iis.rims.lab.quantum.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.ArrayOfString;
import org.tempuri.LISIntegrationWebserviceSoap;

import com.iis.rims.lab.quantum.message.EncodeMessage;

@Controller
public class PageController {
	private static Logger LOGGER = Logger.getLogger(PageController.class);
	@Autowired
	private LISIntegrationWebserviceSoap integrationWebserviceSoap;
	
	@Value("${lis.username}")
	private String username;
	
	@Value("${lis.password}")
	private String password;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		model.put("appName", "Quantum Lab");
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
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
			LOGGER.error(e.getMessage(), e);
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/getResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getResult(@RequestBody String orderNumber) {
		ArrayOfString results = integrationWebserviceSoap.getResultValues(orderNumber);
		return results.getString();
	}
	
	@RequestMapping(value = "/getReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getReport(HttpServletResponse response, String orderNumber) {
		try {
			byte[] buffer = integrationWebserviceSoap.getReportPDF(orderNumber, username, password);
//			byte[] buffer = IOUtils.toByteArray(new FileInputStream("C:\\jhmi\\tmp\\ABI.pdf"));
//			IOUtils.write(buffer, response.getOutputStream());
//			response.setContentType("application/x-download");
//			response.setHeader("Content-Disposition", "attachment;filename=report.pdf");
//			response.flushBuffer();
			return "Report is successfully retrieved for the given " + orderNumber;
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/valVisitNo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AjaxResponse validateVisitNumber(String visitNumber) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setValid(!"".equals(visitNumber));
		ajaxResponse.setMessage("Blah blah");
		return ajaxResponse;
	}
}
