package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SpatialInformation {
	/*
	public static void main(String[] args) {
		SpatialInformation.doSpatial("POLYGON((-34 74,45 74,45 33,-34 33,-34 74))");
	}
*/
	public static JsonObject doSpatial(String spatial)
	{
		JsonObject spatialReturn = new JsonObject();
		JsonObject wkid = new JsonObject();
		wkid.addProperty("wkid", 4326);
		spatialReturn.add("spatialReference", wkid);
		spatial = spatial.replaceAll("POLYGON", "").replaceAll("POINT","").replaceAll("\\)","").replaceAll("\\(","");
		
		System.out.println(spatial);
		
		String[] points = spatial.split(",");
		
		JsonArray path = new JsonArray();
		for(String point : points) {
			String[] latlon  = point.split(" ");
			JsonArray points1 = new JsonArray();
			points1.add(Double.parseDouble(latlon[0]));
			points1.add(Double.parseDouble(latlon[1]));
			path.add(points1);
		}
		
		spatialReturn.add("path", path);
		
		System.out.println(spatialReturn.toString());
		
		return spatialReturn;
	}
}
