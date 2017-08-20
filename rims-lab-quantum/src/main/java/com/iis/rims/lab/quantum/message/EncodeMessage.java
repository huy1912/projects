package com.iis.rims.lab.quantum.message;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
<<<<<<< Upstream, based on origin/master
=======
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
>>>>>>> 29bf08f lab

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
	private static final String EMPTY_ELEMENT_REGEX = "<([A-Za-z_]+)></([A-Za-z_]+)>";
	private static int COUNTER = 0;
	@Deprecated
	public static String encodeMsg() throws Exception {
		MSG msg = new MSG();
		msg = JAXB.unmarshal(new File("ORM_sample.xml"), MSG.class);
		StringWriter stringWriter = new StringWriter();
		JAXBContext jc = JAXBContext.newInstance(MSG.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(msg, stringWriter);
		
		String xmlData = stringWriter.toString();
		
<<<<<<< Upstream, based on origin/master
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		xmlData = xmlData.replaceAll(EMPTY_ELEMENT_REGEX, "<$1/>");
		xmlData = xmlData.replace("<MSG>", "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ws:acceptMessage><arg0><content><MSG>");
		xmlData = xmlData.replace("</MSG>", "</MSG></content></arg0></ws:acceptMessage></soapenv:Body></soap:Envelope>");
=======
//		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		xmlData = xmlData.replaceAll(EMPTY_ELEMENT_REGEX, "<$1/>");
//		String LINE_SEPARATOR = System.getProperty("line.separator");
		//xmlData = xmlData.replaceAll("(\\s</?[A-Za-z])", "\t\t\t\t\t\t$1");
//		xmlData = xmlData.replaceAll("(\\s{4})", "\t\t\t\t\t\t$1");
//		xmlData = xmlData.replace("<MSG>", String.format("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">%s\t<soapenv:Header/>%s\t<soapenv:Body>%s\t\t<ws:acceptMessage>%s\t\t\t<arg0>%s\t\t\t\t<content>%s\t\t\t\t\t<MSG>", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR));
//		xmlData = xmlData.replace("</MSG>", String.format("\t\t\t\t\t</MSG>%s\t\t\t\t</content>%s\t\t\t</arg0>%s\t\t</ws:acceptMessage>%s\t</soapenv:Body>%s</soap:Envelope>", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR));
		xmlData = xmlData.replace("<MSG>", "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ws:acceptMessage><arg0><content><MSG>");
		xmlData = xmlData.replace("</MSG>", "</MSG></content></arg0></ws:acceptMessage></soapenv:Body></soap:Envelope>");
		
//		xmlData = prettyFormat(xmlData);
>>>>>>> 29bf08f lab
		return xmlData;
	}
	
	public static String encodeMsg(MSG msg) throws Exception {
		StringWriter stringWriter = new StringWriter();
		JAXB.marshal(msg, stringWriter);
		String xmlData = stringWriter.toString();
<<<<<<< Upstream, based on origin/master
		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		xmlData = xmlData.replaceAll(EMPTY_ELEMENT_REGEX, "<$1/>");
		xmlData = xmlData.replace("<MSG>", "<arg0><content><MSG>");
		xmlData = xmlData.replace("</MSG>", "</MSG></content></arg0>");
=======
//		xmlData = xmlData.substring(xmlData.indexOf("\n") + 1);
		xmlData = xmlData.replaceAll(EMPTY_ELEMENT_REGEX, "<$1/>");
		xmlData = xmlData.replace("<MSG>", "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ws:acceptMessage><arg0><content><MSG>");
		xmlData = xmlData.replace("</MSG>", "</MSG></content></arg0></ws:acceptMessage></soapenv:Body></soap:Envelope>");
>>>>>>> 29bf08f lab
		return xmlData;
	}
	
	@Deprecated
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
		observationRequest.setOBR(obr);
		msg.getObservationRequest().add(observationRequest);
		return encodeMsg(msg);
	}
	
	public static void main(String[] args) throws Exception {
		System.err.println(encodeMsg());
<<<<<<< Upstream, based on origin/master
=======
	}
	
	public static String prettyFormat(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static String prettyFormat(String input) {
	    return prettyFormat(input, 10);
>>>>>>> 29bf08f lab
	}
}
