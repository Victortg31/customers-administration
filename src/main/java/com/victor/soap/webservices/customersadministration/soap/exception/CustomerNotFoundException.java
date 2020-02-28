package com.victor.soap.webservices.customersadministration.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/*avisando que a request do cliente está errada
@SoapFault(faultCode=FaultCode.CLIENT)*/

//Custon a exception
@SoapFault(faultCode=FaultCode.CUSTOM,customFaultCode="{http://victor.com.br}001_Customer_Not_Found")
public class CustomerNotFoundException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
