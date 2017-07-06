package com.iis.rims.lab.quantum.message;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.orm.MSG;

public class EncodeMessage {
	public static String encodeMsg() throws Exception {
		MSG msg = new MSG();
		msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(msg, stringWriter);
		String xmlData = stringWriter.toString();
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		
		return xmlData;
	}
	
	public static void main(String[] args) throws Exception {
		MSG msg = new MSG();
		msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
//		PID pid = new PID();
//		pid.setAddress1("AAA");
//		msg.setPID(pid);
		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(msg, stringWriter);
		String xmlData = stringWriter.toString();
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		System.err.println(xmlData);
//		JAXBContext jc = JAXBContext.newInstance(MSG.class);

//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        File xml = new File("src/forum18617998/input.xml");
//        MSG root = (MSG) unmarshaller.unmarshal(xml);

//        Marshaller marshaller = jc.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(msg, System.out);
	}
}
