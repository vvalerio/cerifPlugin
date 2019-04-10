package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.HashMap;
import org.epos_ip.beans.Distribution;
import org.epos_ip.beans.ServiceParameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONDistributionDefault {
	public static void generate(JsonArray result, Distribution obj, int i)
	{
		//System.out.println("HERE"+result.toString());
		
		if(result.get(i).getAsJsonObject().has("distributionid")) obj.setDistributionid(result.get(i).getAsJsonObject().get("distributionid").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("title")) obj.setTitle(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("identifier")) obj.setId(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelscheme").getAsString().equals("File Types")) obj.setDefaultFormat(result.get(i).getAsJsonObject().get("labelclass").getAsString());
		
		System.out.println(obj.toString());
		
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
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("template")) obj.setDefaultURL(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("variable")) spp.setName(value.get("txtvalue").getAsString());
		if(value.has("labelclass") && value.get("labelclass").getAsString().equals("defaultValue")) spp.setDefaultValue(value.get("txtvalue").getAsString());
	}

}
