package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import org.epos_ip.utility.Check;
import org.epos_ip.utility.EposEntities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class JSONOrganisation {
    public static void generate(JsonArray results, JsonArray result, int i)
    {
	JsonObject value = result.get(i).getAsJsonObject();
	JsonObject element = new JsonObject();
	element.addProperty("name", Check.checkString(value.get("name")));
	element.addProperty("id", Check.checkString(value.get("identifier")));
	element.addProperty("type", EposEntities.ORGANISATION.getValue());
	JsonObject geometry = new JsonObject();
	geometry.addProperty("elev", Check.checkDouble(value.get("elevation")));
	geometry.addProperty("lat", Check.checkDouble(value.get("latitude")));
	geometry.addProperty("long", Check.checkDouble(value.get("longitude")));
	element.add("geometry", geometry);
	
	if(results.size()==0) results.add(element);
	else if(!results.contains(element)) results.add(element);
    }
}
