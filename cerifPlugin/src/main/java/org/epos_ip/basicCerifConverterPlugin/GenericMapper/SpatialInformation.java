/*******************************************************************************
 * Copyright 2021 EPOS ERIC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SpatialInformation {

	
	public static JsonArray doSpatial(String spatial)
	{
		JsonObject spatialReturn = new JsonObject();
		JsonObject wkid = new JsonObject();
		wkid.addProperty("wkid", 4326);
		spatialReturn.add("spatialReference", wkid);
		boolean isPoint = (spatial.contains("POINT")) ? true : false;

		spatial = spatial.replaceAll("POLYGON", "").replaceAll("POINT", "").replaceAll("\\)", "").replaceAll("\\(", "");

		if(isPoint) spatial = spatial.replaceAll("\\,", "");
		String[] points = spatial.split(",");


		JsonArray path = new JsonArray();
		
		if(isPoint) {
			String[] latlon = points[0].trim().split(" ");
			//spatialReturn.addProperty("x", Double.parseDouble(latlon[0]));
			//spatialReturn.addProperty("y", Double.parseDouble(latlon[1]));
			path.add(Double.parseDouble(latlon[0]));
			path.add(Double.parseDouble(latlon[1]));
		} else {
			try {
				for(String point : points) {
					String[] latlon  = point.trim().split(" ");
					JsonArray points1 = new JsonArray();
					points1.add(Double.parseDouble(latlon[0]));
					points1.add(Double.parseDouble(latlon[1]));
					path.add(points1);
				}
			}catch(Exception e) {}
		}
		return path;

	}

	public static boolean checkPoint(String spatial) {
		return (spatial.contains("POINT"))? true : false;
	}
}
