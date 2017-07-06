package com.iis.rims.lab.quantum.message;

import javax.xml.bind.JAXB;

import com.iis.rims.lab.quantum.orm.MSG;


public class DecodeMessage {
	public static void main(String[] args) {
		MSG msg = JAXB.unmarshal("ORM_sample.xml", MSG.class);
		System.err.println(msg);
	}
}
