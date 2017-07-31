package com.iis.rims.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParameterBuilder {
	public static final ParameterBuilder DEFAULT_PARAMETER_BUILDER = new ParameterBuilder();
	private final Map<String, Object> parameters;
	private Map<String, Date> timestampParameters;
	public ParameterBuilder() {
		this.parameters = new HashMap<String, Object>();
	}
	
	public ParameterBuilder(List<String> keys, List<Object> values) {
		this();
		if (keys != null && values != null) {
			int length = Math.min(keys.size(), values.size());
			for (int i = 0; i < length; i++) {
				addParameter(keys.get(i), values.get(i));
			}
		}
	}
	
	public ParameterBuilder addParameter(String key, Object value) {
		parameters.put(key, value);
		return this;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public ParameterBuilder addTimestampParameter(String key, Date value) {
		if (timestampParameters == null) {
			timestampParameters = new HashMap<String, Date>();
		}
		timestampParameters.put(key, value);
		return this;
	}
	
	public Map<String, Date> getTimestampParameters() {
		return timestampParameters;
	}
}
