package com.iis.rims.lab.quantum.message;

import java.io.StringReader;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.orm.MSG;


public class DecodeMessage {
	public static void main(String[] args) throws Exception {
		/*
		MSG msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
		System.err.println(msg);
		
		String xmlData = "<?xml version=\"1.0\" encodin g=\"utf-8\"?><content xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><MSG>aaaa</MSG></content>";
		System.err.println(decodeResults(xmlData));
//		Content msg = JAXB.unmarshal(new File("1000006_REPORT.xml"), Content.class);
//		System.err.println(msg);
		
		xmlData = "<?xml version=\"1.0\" encoding=\"UTF-16\"?><Response><IntegrationHistoryID>24730486</IntegrationHistoryID><Status>0</Status></Response>";
		ResponseType response = decodeMessage(xmlData, ResponseType.class);
		System.err.println(response.getIntegrationHistoryID());
		
		String xmlData = FileUtils.readFileToString(new File("10000120_4.xml"));
		MSG msg = decodeResults(xmlData);
		System.err.println(msg);
		*/
	}
	
	public static MSG decodeResults(String xmlData) {
		xmlData = xmlData.replaceAll("<content[^>]*>", "");
		xmlData = xmlData.replaceAll("</content>", "");
		return decodeMessage(xmlData, MSG.class);
	}
	
	public static <T> T decodeMessage(String xmlData, Class<T> clazz) {
		StringReader reader = new StringReader(xmlData);
		T msg = JAXB.unmarshal(reader, clazz);
		reader.close();
		return msg;
	}
	
	public static String decodeXml(String value) {
		String xmlData = value.replace("&lt;", "<");
		xmlData = xmlData.replace("&gt;", ">");
		return xmlData;
	}
}
