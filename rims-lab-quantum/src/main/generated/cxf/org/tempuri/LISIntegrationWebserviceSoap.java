package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-17T09:02:01.564+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "LISIntegrationWebserviceSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface LISIntegrationWebserviceSoap {

    @WebResult(name = "GetReportPDFResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetReportPDF", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetReportPDF")
    @WebMethod(operationName = "GetReportPDF", action = "http://tempuri.org/GetReportPDF")
    @ResponseWrapper(localName = "GetReportPDFResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetReportPDFResponse")
    public byte[] getReportPDF(
        @WebParam(name = "OrderNo", targetNamespace = "http://tempuri.org/")
        java.lang.String orderNo,
        @WebParam(name = "userName", targetNamespace = "http://tempuri.org/")
        java.lang.String userName,
        @WebParam(name = "password", targetNamespace = "http://tempuri.org/")
        java.lang.String password
    );

    @WebResult(name = "ApprovedResultVaues", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetResultValues", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetResultValues")
    @WebMethod(operationName = "GetResultValues", action = "http://tempuri.org/GetResultValues")
    @ResponseWrapper(localName = "GetResultValuesResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetResultValuesResponse")
    public org.tempuri.ArrayOfString getResultValues(
        @WebParam(name = "OrderNo", targetNamespace = "http://tempuri.org/")
        java.lang.String orderNo
    );

    @WebResult(name = "Response", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "PushOrder", targetNamespace = "http://tempuri.org/", className = "org.tempuri.PushOrder")
    @WebMethod(operationName = "PushOrder", action = "http://tempuri.org/PushOrder")
    @ResponseWrapper(localName = "PushOrderResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.PushOrderResponse")
    public java.lang.String pushOrder(
        @WebParam(name = "xmlData", targetNamespace = "http://tempuri.org/")
        java.lang.String xmlData,
        @WebParam(name = "userName", targetNamespace = "http://tempuri.org/")
        java.lang.String userName,
        @WebParam(name = "password", targetNamespace = "http://tempuri.org/")
        java.lang.String password
    );
}
