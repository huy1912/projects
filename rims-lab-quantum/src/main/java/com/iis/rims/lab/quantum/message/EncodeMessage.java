package com.iis.rims.lab.quantum.message;

import java.io.File;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.controller.SubmitOrder;
import com.iis.rims.lab.quantum.orm.MSG;
import com.iis.rims.lab.quantum.orm.MSG.EVN;
import com.iis.rims.lab.quantum.orm.MSG.MSH;
import com.iis.rims.lab.quantum.orm.MSG.ORC;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest;
import com.iis.rims.lab.quantum.orm.MSG.ObservationRequest.OBR;
import com.iis.rims.lab.quantum.orm.MSG.PID;
import com.iis.rims.lab.quantum.orm.MSG.PV1;

public class EncodeMessage {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("00000000000");
	private static int COUNTER = 0;
	public static String encodeMsg() throws Exception {
		MSG msg = new MSG();
		msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(msg, stringWriter);
		String xmlData = stringWriter.toString();
		
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		
		return xmlData;
	}
	
	public static String encodeMsg(MSG msg) throws Exception {
		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(msg, stringWriter);
		String xmlData = stringWriter.toString();
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		
		return xmlData;
	}
	
	public static String encodeMsg(SubmitOrder order) throws Exception {
		Date today = new Date();
		MSG msg = new MSG();
		MSH msh = new MSH();
		msh.setSendingApplication("Renal");
		msh.setSendingFacility("RNL");
		msh.setReceivingApplication("LIS");
		msh.setReceivingFacility("XXX");
		msh.setMessageDateTime(FORMATTER.format(today));
		msh.setMessageType("ORM");
		msh.setMessageControlId(DECIMAL_FORMATTER.format(++COUNTER));
		msg.setMSH(msh);
		EVN evn = new EVN();
		evn.setEventTypeCode("O01");
		evn.setRecordedDateTime(FORMATTER.format(today));
		msg.setEVN(evn);
		PID pid = new PID();
		pid.setPatientIdInt(order.getPatientId());
		pid.setPatientName(order.getPatientName());
		pid.setPatientIdNumber(order.getNricFinNumber());
		pid.setDateOfBirth("19510915");
		pid.setGenderCode("M");
		msg.setPID(pid);
		PV1 pv1 = new PV1();
		pv1.setVisitNumber(order.getVisitNumber());
		msg.setPV1(pv1);
		ORC orc = new ORC();
		orc.setOrderControl("NW");
		orc.setORCPlacerOrderNumber(order.getOrcOrderNumber());
		msg.setORC(orc);
		ObservationRequest observationRequest = new ObservationRequest();
		OBR obr = new OBR();
		obr.setSetIdOBR("1");
		obr.setRequestedDateTime(FORMATTER.format(today));
		obr.setOBRPlacerOrderNumber(order.getObrOrderNumber());
		observationRequest.getOBR().add(obr);
		msg.setObservationRequest(observationRequest);
		return encodeMsg(msg);
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
