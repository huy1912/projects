package com.iis.rims.lab.quantum.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

public class OutSoapInterceptor extends AbstractSoapInterceptor {
//	private CacheStream os;

	public OutSoapInterceptor() {
		super(Phase.SEND);
//		os = new CacheStream();
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {

        try {
        	CachedOutputStream  os = (CachedOutputStream) message.getContent(OutputStream.class);
        	String currentEnvelopeMessage = IOUtils.toString(os.getInputStream(), "UTF-8");
        	CachedStream cs = new CachedStream();
        	message.setContent(OutputStream.class, cs);
        	
        	message.getInterceptorChain().doIntercept(message);
            cs.flush();
            IOUtils.closeQuietly(cs);
            CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);

//            String currentEnvelopeMessage = IOUtils.toString(os.getInputStream(), "UTF-8");
            csnew.flush();
            IOUtils.closeQuietly(csnew);

//            if (getLogger().isDebugEnabled()) {
//                getLogger().debug("Outbound message: " + currentEnvelopeMessage);
//            }

            String res = changeOutboundMessage(currentEnvelopeMessage);
            if (res != null) {
//                if (getLogger().isDebugEnabled()) {
//                    getLogger().debug("Outbound message has been changed: " + res);
//                }
            }
            res = res != null ? res : currentEnvelopeMessage;

//            InputStream replaceInStream = IOUtils.toInputStream(res, "UTF-8");
            ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) os.getOut();
            byteArrayOutputStream.reset();
            byteArrayOutputStream.write(res.getBytes());
//            IOUtils.copy(replaceInStream, os);
//            replaceInStream.close();
//            IOUtils.closeQuietly(replaceInStream);

            os.flush();
            message.setContent(OutputStream.class, os);
            IOUtils.closeQuietly(os);

        } catch (IOException ioe) {
//            getLogger().warn("Unable to perform change.", ioe);
            throw new RuntimeException(ioe);
        }
	}

	private String changeOutboundMessage(String value) {
		String s = value.replace("&lt;", "<");
		s = s.replace("&gt;", ">");
//		s = s.replace("<xmlData>", "<ws:acceptMessage xmlns:ws=\"http://ws.connectors.connect.mirth.com\">\r\n<arg0>\r\n<content>");
		s = s.replace("<xmlData>", "<ws:acceptMessage>\n<arg0>\n<content>");
		s = s.replace("</xmlData>", "</content>\r</arg0>\r</ws:acceptMessage>");
		return s;
	}

	private class CachedStream extends CachedOutputStream {
		@Override
		protected void doClose() throws IOException {
			// TODO Auto-generated method stub
			super.doClose();
		}
	}
}
