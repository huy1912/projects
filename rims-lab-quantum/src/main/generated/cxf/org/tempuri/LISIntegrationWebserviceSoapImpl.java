
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.tempuri;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-16T18:35:16.015+08:00
 * Generated source version: 3.1.11
 * 
 */

@javax.jws.WebService(
                      serviceName = "LISIntegrationWebservice",
                      portName = "LISIntegrationWebserviceSoap",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "file:/C:/Users/HUY/git/labs/rims-lab-quantum/src/main/resources/wsdl/LisIntegrationWebservice.wsdl",
                      endpointInterface = "org.tempuri.LISIntegrationWebserviceSoap")
                      
public class LISIntegrationWebserviceSoapImpl implements LISIntegrationWebserviceSoap {

    private static final Logger LOG = Logger.getLogger(LISIntegrationWebserviceSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see org.tempuri.LISIntegrationWebserviceSoap#getReportPDF(java.lang.String orderNo, java.lang.String userName, java.lang.String password)*
     */
    public byte[] getReportPDF(java.lang.String orderNo, java.lang.String userName, java.lang.String password) { 
        LOG.info("Executing operation getReportPDF");
        System.out.println(orderNo);
        System.out.println(userName);
        System.out.println(password);
        try {
            byte[] _return = new byte[0];
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.LISIntegrationWebserviceSoap#getResultValues(java.lang.String orderNo)*
     */
    public org.tempuri.ArrayOfString getResultValues(java.lang.String orderNo) { 
        LOG.info("Executing operation getResultValues");
        System.out.println(orderNo);
        try {
            org.tempuri.ArrayOfString _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.LISIntegrationWebserviceSoap#pushOrder(java.lang.String xmlData, java.lang.String userName, java.lang.String password)*
     */
    public java.lang.String pushOrder(java.lang.String xmlData, java.lang.String userName, java.lang.String password) { 
        LOG.info("Executing operation pushOrder");
        System.out.println(xmlData);
        System.out.println(userName);
        System.out.println(password);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
