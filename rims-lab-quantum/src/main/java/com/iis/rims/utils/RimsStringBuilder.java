package com.iis.rims.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RimsStringBuilder {
	@Override
	public String toString(){
	    //return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, true);
	}
}
