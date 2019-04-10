package org.epos_ip.basicCerifConverterPlugin.GenericMapper;


import org.epos_ip.basicCerifConverterPlugin.interfaces.MapperInterface;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GenericMapper implements MapperInterface {
	private String mappedResult;

	public GenericMapper()
	{
		mappedResult = new String();
	}

	@Override
	public JsonElement generate(JsonObject resultSet, String researchType, String format) {
		return resultSet;}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return mappedResult;
	}



}
