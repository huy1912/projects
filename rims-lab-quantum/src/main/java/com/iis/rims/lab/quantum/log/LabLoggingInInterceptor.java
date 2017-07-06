package com.iis.rims.lab.quantum.log;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;

public class LabLoggingInInterceptor extends LoggingInInterceptor {
	@Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Inbound Message:\n");
 
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            buffer.append(loggingMessage.getPayload());
        }        
        return buffer.toString();
    }
}
