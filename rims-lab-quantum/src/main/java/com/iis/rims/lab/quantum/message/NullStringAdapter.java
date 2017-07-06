package com.iis.rims.lab.quantum.message;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NullStringAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String value) throws Exception {
		if (value == null) {
			return "";
		}
		return value;
	}

	@Override
	public String unmarshal(String value) throws Exception {
		if (value == "") {
			return null;
		}
		return value;
	}

}
