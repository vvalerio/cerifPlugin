package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.HashMap;
import org.epos_ip.beans.Distribution;
import org.epos_ip.beans.ServiceParameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONDistribution {
	public static void generate(JsonArray result, Distribution obj, int i)
	{
		if(result.get(i).getAsJsonObject().has("distributionid")) obj.setDistributionid(result.get(i).getAsJsonObject().get("distributionid").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("title")) obj.setTitle(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("identifier")) obj.setId(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelscheme").getAsString().equals("Distribution Types")) obj.setType(result.get(i).getAsJsonObject().get("labelclass").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("accessURL")) obj.setEndpoint(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("downloadURL")) obj.setEndpoint(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelscheme").getAsString().equals("File Types")) obj.setOriginalFormat(result.get(i).getAsJsonObject().get("labelclass").getAsString());
		
		System.out.println("DSITRIBUTION: "+obj.toString());
		
	}
	
	public static void generate(JsonArray result, HashMap<String, ServiceParameter> parametersMap, int i, Distribution obj)
	{
		ServiceParameter spp = null;
		JsonObject value = result.get(i).getAsJsonObject();
		
		if(parametersMap.containsKey(result.get(i).getAsJsonObject().get("srcid").getAsString()))
		{
			spp = parametersMap.get(result.get(i).getAsJsonObject().get("srcid").getAsString());
			parameterContext(spp, value, obj);
			System.out.println(spp.toString());
		}
		else{
			ServiceParameter sp = new ServiceParameter();
			parameterContext(sp, value, obj);
			parametersMap.put(result.get(i).getAsJsonObject().get("srcid").getAsString(), sp);
			System.out.println(sp.toString());
		} 
		
		
	}

	private static void parameterContext(ServiceParameter spp, JsonObject value, Distribution obj)
	{
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("template")) obj.setEndpoint(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("variable")) spp.setName(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("label")) spp.setLabel(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("maxValue")) spp.setMaxValue(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("minValue")) spp.setMinValue(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("xsd")) spp.setType(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("paramValue")) spp.setValue(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("version")) spp.setVersion(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("defaultValue")) spp.setDefaultValue(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("property")) spp.setProperty(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("valuePattern")) spp.setValuePattern(value.get("txtvalue").getAsString());
	}

}
