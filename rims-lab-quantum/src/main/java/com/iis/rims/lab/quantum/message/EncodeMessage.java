package com.iis.rims.lab.quantum.message;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.controller.SubmitOrder;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.MSG.MSH;
import com.iis.rims.lab.quantum.orm.MSG.EVN;
import com.iis.rims.lab.quantum.orm.MSG.ORC;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest.OBR;
import com.iis.rims.lab.quantum.orm.MSG.PID;
import com.iis.rims.lab.quantum.orm.MSG.PV1;

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
	
	public static String encodeMsg(SubmitOrder order) throws Exception {
		MSG msg = new MSG();
		msg.setMSH(new MSH());
		msg.setEVN(new EVN());
		PID pid = new PID();
		pid.setPatientIdInt(order.getPatientId());
		pid.setPatientName(order.getPatientName());
		pid.setPatientIdNumber(order.getNricFinNumber());
		msg.setPID(pid);
		PV1 pv1 = new PV1();
		pv1.setVisitNumber(order.getVisitNumber());
		msg.setPV1(pv1);
		ORC orc = new ORC();
		orc.setORCPlacerOrderNumber(order.getOrcOrderNumber());
		msg.setORC(orc);
		ObservationRequest observationRequest = new ObservationRequest();
		OBR obr = new OBR();
		obr.setOBRPlacerOrderNumber(order.getObrOrderNumber());
		observationRequest.getOBR().add(obr);
		msg.setObservationRequest(observationRequest);
//		msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
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
