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

import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONDistributionDefault {
	
	private static final Logger LOGGER = Logger.getLogger("JSONDistribution");
	
	private static final String SRC_ID  = "srcid";
	private static final String DISTRIBUTION_ID  = "distributionid";
	private static final String LABELCLASS  = "labelclass";
	private static final String TXTVALUE  = "txtvalue";
	private static final String LABELSCHEME  = "labelscheme";
	
	private JSONDistributionDefault() {}
	
	public static void generate(JsonArray result, Distribution obj, int i)
	{ 		
		if(result.get(i).getAsJsonObject().has(DISTRIBUTION_ID)) obj.setDistributionid(result.get(i).getAsJsonObject().get(DISTRIBUTION_ID).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("title")) obj.setTitle(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("identifier")) obj.setId(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("File Types")) {
			obj.getAvailableFormats().add(new AvailableFormat(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), "", "original"));
		}
		LOGGER.info(obj.toString());
	}

	public static void generate(JsonArray result, Map<String, ServiceParameter> parametersMap, int i, Distribution obj)
	{
		ServiceParameter spp = null;
		JsonObject value = result.get(i).getAsJsonObject();
		
		if(parametersMap.containsKey(result.get(i).getAsJsonObject().get(SRC_ID).getAsString()))
		{
			spp = parametersMap.get(result.get(i).getAsJsonObject().get(SRC_ID).getAsString());
			parameterContext(spp, value, obj);
			LOGGER.info(spp.toString());
		}
		else{
			ServiceParameter sp = new ServiceParameter();
			parameterContext(sp, value, obj);
			parametersMap.put(result.get(i).getAsJsonObject().get(SRC_ID).getAsString(), sp);
			LOGGER.info(sp.toString());
		} 
		
		
	}

	private static void parameterContext(ServiceParameter spp, JsonObject value, Distribution obj)
	{
		//if(value.has(LABELCLASS) && value.get(LABELCLASS).getAsString().equals("template")) obj.setDefaultURL(value.get(TXTVALUE).getAsString());
		if(value.has(LABELCLASS) && value.get(LABELCLASS).getAsString().equals("variable")) spp.setName(value.get(TXTVALUE).getAsString());
		if(value.has(LABELCLASS) && value.get(LABELCLASS).getAsString().equals("defaultValue")) spp.setDefaultValue(value.get(TXTVALUE).getAsString());
	}

}
