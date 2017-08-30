package com.iis.rims.lab.quantum.log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;

import com.iis.rims.lab.quantum.message.DecodeMessage;

public class LabLoggingInInterceptor extends LoggingInInterceptor {
	private final String inputDir;
	private ThreadLocal<String> orderNumberThreadLocal;
	
	public LabLoggingInInterceptor(String inputDir, ThreadLocal<String> orderNumberThreadLocal) {
		this.inputDir = inputDir;
		this.orderNumberThreadLocal = orderNumberThreadLocal;
	}
	
	@Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Inbound Message:\n");
 
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            String inMessage = loggingMessage.getPayload().toString();
            inMessage = DecodeMessage.decodeXml(inMessage);
            String orderNumber = this.orderNumberThreadLocal.get();
            // Write to log file
            if (inMessage.contains("GetResultValuesResponse")) {
            	String path = String.format("%s/%s_RAW.xml", inputDir, orderNumber);
				try {
					FileUtils.writeStringToFile(new File(path) , inMessage, Charset.defaultCharset());
				}
				catch (IOException e) {
					e.printStackTrace();
				}	
            }
			buffer.append(inMessage);
        }        
        return buffer.toString();
    }
}
