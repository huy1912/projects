package com.iis.rims.lab.quantum.message;

import java.io.StringReader;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.orm.MSG;


public class DecodeMessage {
	public static void main(String[] args) {
//		MSG msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
//		System.err.println(msg);
		
		String xmlData = "<?xml version=\"1.0\" encodin g=\"utf-8\"?><content xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><MSG>aaaa</MSG></content>";
		System.err.println(decodeResults(xmlData));
//		Content msg = JAXB.unmarshal(new File("1000006_REPORT.xml"), Content.class);
//		System.err.println(msg);
	}
	
	public static MSG decodeResults(String xmlData) {
		xmlData = xmlData.replaceAll("<content[^>]*>(.+)</content>", "$1");
		return decodeMessage(xmlData, MSG.class);
	}
	
//	public static void main(String[] args) {
//		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-16\"?><Response><IntegrationHistoryID>24730486</IntegrationHistoryID><Status>0</Status></Response>";
//		ResponseType response = decodeMessage(xmlData, ResponseType.class);
//		System.err.println(response.getIntegrationHistoryID());
//	}
	
	public static <T> T decodeMessage(String xmlData, Class<T> clazz) {
		StringReader reader = new StringReader(xmlData);
		T msg = JAXB.unmarshal(reader, clazz);
		reader.close();
		return msg;
	}
}
