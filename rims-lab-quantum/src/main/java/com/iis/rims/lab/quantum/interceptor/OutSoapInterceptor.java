package com.iis.rims.lab.quantum.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.Phase;

public class OutSoapInterceptor extends AbstractSoapInterceptor {
	public OutSoapInterceptor() {
		super(Phase.PRE_STREAM_ENDING);
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {

        try {
        	CachedOutputStream os = (CachedOutputStream) message.getContent(OutputStream.class);
        	String currentEnvelopeMessage = IOUtils.toString(os.getInputStream(), "UTF-8");
        	
        	// Dummy cached stream for intercept.
        	CachedOutputStream dummyCachedStream = new CachedOutputStream();
        	message.setContent(OutputStream.class, dummyCachedStream);
        	message.getInterceptorChain().doIntercept(message);
            dummyCachedStream.flush();
            IOUtils.closeQuietly(dummyCachedStream);

//            if (getLogger().isDebugEnabled()) {
//                getLogger().debug("Outbound message: " + currentEnvelopeMessage);
//            }

            String res = changeOutboundMessage(currentEnvelopeMessage);
            res = res != null ? res : currentEnvelopeMessage;

            ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) os.getOut();
            byteArrayOutputStream.reset();
            byteArrayOutputStream.write(res.getBytes());
            os.flush();
            message.setContent(OutputStream.class, os);
            IOUtils.closeQuietly(os);

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
	}

	private String changeOutboundMessage(String value) {
		String s = value.replace("&lt;", "<");
		s = s.replace("&gt;", ">");
		s = s.replace("<xmlData>", "<acceptMessage>\n<arg0>\n<content>");
		s = s.replace("</xmlData>", "</content>\r</arg0>\r</acceptMessage>");
		return s;
	}
}
