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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONDistribution {

	private static final String SRC_ID  = "srcid";
	private static final String PRODUCT_ID  = "productid";
	private static final String LABELCLASS  = "labelclass";
	private static final String TXTVALUE  = "txtvalue";
	private static final String LABELSCHEME  = "labelscheme";

	private JSONDistribution() {}

	public static void generate(JsonArray result, Distribution obj, int i)
	{ 
		if(result.get(i).getAsJsonObject().has(PRODUCT_ID)) obj.setDistributionid(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("title")) obj.setTitle(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("title")) obj.setServiceName(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("DOI")) obj.setDOI(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("identifier")) obj.setId(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("Distribution Types")) obj.setType(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("freq")) obj.setFrequencyUpdate(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("entrypoint")) obj.setServiceEndpoint(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("downloadURL")) obj.setDownloadURL(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("File Types")) {
			if(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().toLowerCase().equals("png")) {
				obj.getAvailableFormats().add(new AvailableFormat("WMS", "application/vnd.ogc.wms_xml","", "original"));
			}
			else obj.getAvailableFormats().add(new AvailableFormat(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), "", "original"));
		}
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("plugin")) {
			obj.getAvailableFormats().add(new AvailableFormat("GEOJSON","application/epos.geo+json", "", "converted"));
		}
		//if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("minValue")) obj.setStartDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		//if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("maxValue")) obj.setEndDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());

		if(!result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("dataset") && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("startDate")) 
			obj.getTemporalCoverage().setStartDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(!result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("dataset") && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("endDate")) 
			obj.getTemporalCoverage().setEndDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		
		if(!result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("service") && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("startDate")) 
			obj.getServiceTemporalCoverage().setStartDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(!result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("service") && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("endDate")) 
			obj.getServiceTemporalCoverage().setEndDate(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());

		//if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("valuePattern")) obj.setPattern(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("wsSpatial")) 
			obj.getServiceSpatial().addPaths(SpatialInformation.doSpatial(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()),SpatialInformation.checkPoint(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()));
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("dsSpatial")) {
			obj.getSpatial().addPaths(SpatialInformation.doSpatial(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()),SpatialInformation.checkPoint(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()));
		}
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("keywords")) obj.setKeywords(new ArrayList<String>(Arrays.asList(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString().split(","))));

		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("license")) obj.setLicense(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("description")) obj.setDescription(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("description")) obj.setServiceDescription(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("documentation")) obj.setServiceDocumentation(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("DataProvider")) obj.getDataProvider().add(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()); //
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("WebserviceProvider")) obj.setServiceProvider(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()); //
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("InternalID")) obj.getInternalID().add(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString()); //

	}

	public static void generate(JsonArray result, Map<String, ServiceParameter> parametersMap, int i, Distribution obj)
	{
		ServiceParameter spp = null;
		JsonObject value = result.get(i).getAsJsonObject();

		if(parametersMap.containsKey(result.get(i).getAsJsonObject().get(SRC_ID).getAsString()))
		{
			spp = parametersMap.get(result.get(i).getAsJsonObject().get(SRC_ID).getAsString());
			parameterContext(spp, value, obj); 
		}
		else{
			ServiceParameter sp = new ServiceParameter();
			parameterContext(sp, value, obj);
			parametersMap.put(result.get(i).getAsJsonObject().get(SRC_ID).getAsString(), sp); 
		} 


	}

	private static void parameterContext(ServiceParameter spp, JsonObject value, Distribution obj)
	{
		if(value.has(LABELCLASS)) {
			switch(value.get(LABELCLASS).getAsString()) {
			case "template":
				obj.setServiceEndpoint(value.get(TXTVALUE).getAsString());
				obj.setEndpoint(value.get(TXTVALUE).getAsString());
				break;

			case "variable":
				spp.setName(value.get(TXTVALUE).getAsString());
				break;

			case "label":
				spp.setLabel(value.get(TXTVALUE).getAsString());
				break;

			case "maxValue":
				spp.setMaxValue(value.get(TXTVALUE).getAsString());
				break;

			case "minValue":
				spp.setMinValue(value.get(TXTVALUE).getAsString());
				break;

			case "xsd":
				spp.setType(value.get(TXTVALUE).getAsString());
				break;

			case "hydra":
				if(value.get(TXTVALUE).getAsString().equals("required"))
					spp.setRequired(true);
				break;

			case "paramValue":
				spp.setEnumValue(value.get(TXTVALUE).getAsString());
				break;
			case "version":
				spp.setVersion(value.get(TXTVALUE).getAsString());
				break;
			case "defaultValue":
				spp.setDefaultValue(value.get(TXTVALUE).getAsString());
				break;
			case "property":
				spp.setProperty(value.get(TXTVALUE).getAsString());
				break;
			case "valuePattern":
				spp.setValuePattern(value.get(TXTVALUE).getAsString());
				break;
				}
		}

	}
}