package com.iis.rims.lab.quantum.log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

public class LabLoggingOutInterceptor extends LoggingOutInterceptor {
	
	private final String outputDir;
	private final ThreadLocal<String> orderNumberThreadLocal;
	
	public LabLoggingOutInterceptor(String outputDir, ThreadLocal<String> orderNumberThreadLocal) {
		this.outputDir = outputDir;
		this.orderNumberThreadLocal = orderNumberThreadLocal;
	}
	
	@Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Outbound Message:\n");
 
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            String outMessage = loggingMessage.getPayload().toString();
            orderNumberThreadLocal.remove();
            if (outMessage.contains("PushOrder") && outMessage.contains("<Message_Type>ORM</Message_Type>")) {
            	String orderNumber = StringUtils.substringBetween(outMessage, "<Message_Control_Id>", "</Message_Control_Id>");
            	try {
            		String path = String.format("%s/%s.xml", outputDir, orderNumber);
					FileUtils.writeStringToFile(new File(path) , outMessage, Charset.defaultCharset());
				}
            	catch (IOException e) {
					e.printStackTrace();
				}
            }
            else if (outMessage.contains("GetResultValues") || outMessage.contains("GetReportPDF")) {
            	String orderNumber = StringUtils.substringBetween(outMessage, "<OrderNo>", "</OrderNo>");
            	orderNumberThreadLocal.set(orderNumber);
            }
			buffer.append(outMessage);
        }        
        return buffer.toString();
    }
}
