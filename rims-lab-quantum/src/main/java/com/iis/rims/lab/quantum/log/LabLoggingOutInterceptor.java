package com.iis.rims.lab.quantum.log;

import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

public class LabLoggingOutInterceptor extends LoggingOutInterceptor {
	@Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Outbound Message:\n");
 
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            buffer.append(loggingMessage.getPayload());
        }        
        return buffer.toString();
    }
}
