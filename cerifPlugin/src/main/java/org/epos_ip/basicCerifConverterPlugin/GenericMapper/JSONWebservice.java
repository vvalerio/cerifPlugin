package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import org.epos_ip.utility.Check;
import org.epos_ip.utility.EposEntities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONWebservice {
	public static void generate(JsonArray results, JsonArray result, int i)
	{
		JsonObject value = result.get(i).getAsJsonObject();
		JsonObject element = new JsonObject();
		element.addProperty("name", Check.checkString(value.get("title")));
		element.addProperty("id", Check.checkString(value.get("webserviceid")));
		element.addProperty("description", Check.checkString(value.get("description")));
		element.addProperty("type", EposEntities.WEBSERVICE.getValue());
		JsonObject geometry = new JsonObject();
		System.out.println(value.get("elevation_min"));
		System.out.println(value.get("latitude_max"));
		System.out.println(value.get("longitude_min"));
		System.out.println(value.get("elevation_max"));
		System.out.println(value.get("latitude_min"));
		System.out.println(value.get("longitude_max"));
		
		geometry.addProperty("minElev", Check.checkDouble(value.get("elevation_min")));
		geometry.addProperty("minLat", Check.checkDouble(value.get("latitude_max")));
		geometry.addProperty("minLong", Check.checkDouble(value.get("longitude_min")));
		geometry.addProperty("maxElev", Check.checkDouble(value.get("elevation_max")));
		geometry.addProperty("maxLat", Check.checkDouble(value.get("latitude_min")));
		geometry.addProperty("maxLong", Check.checkDouble(value.get("longitude_max")));
		element.add("geometry", geometry);
		if(results.size()==0) results.add(element);
		else if(!results.contains(element)) results.add(element);
	}
}
