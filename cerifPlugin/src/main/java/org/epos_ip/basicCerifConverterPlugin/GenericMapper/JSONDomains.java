package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONDomains {
    public static void generate(JsonObject resultJson, JsonArray results, JsonArray domains, JsonArray result, int i)
    {
	JsonObject value = result.get(i).getAsJsonObject();
	try{
	    String domainList = value.get("domain").getAsString().toLowerCase();
	    for(String dom : domainList.split(", "))
	    {
		boolean found = false;
		for(JsonElement e : domains)
		{
		    if(e.getAsString().equals(dom)) found = true;
		}
		if(!found) domains.add(dom);
		
		JsonArray subdomainList = new JsonArray();
		String subDomList = value.get("subdomain").getAsString().toLowerCase();
		for(String s : subDomList.split(", "))
		{
		    subdomainList.add(s.toLowerCase());
		}
		if(!found) resultJson.add(dom,subdomainList);
		else{
		    for(JsonElement je : subdomainList)
		    {
			if(resultJson.get(dom).getAsJsonArray().contains(je)) continue;
			else resultJson.get(dom).getAsJsonArray().add(je);
			
		    }
		}
		System.out.println("DOMAINS: "+domainList.toString());
	    }
	    
	}
	catch(Exception e){}
    }
}
