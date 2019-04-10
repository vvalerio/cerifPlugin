package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.JsonArray;

public class JSONFacetsOrganisations {
	public static void generate(HashMap<String, HashSet<String>> organisations, JsonArray result, int i)
	{
		String keyname = result.get(i).getAsJsonObject().get("organisation").getAsString();
		String ddssname = result.get(i).getAsJsonObject().get("ddss").getAsString();
		
		System.out.println(keyname+" "+ddssname);

		if(organisations.containsKey(keyname)) {
			organisations.get(keyname).add(ddssname);
		}
		else {
			HashSet<String> hs = new HashSet<String>();
			hs.add(ddssname);
			organisations.put(keyname, hs);
		}

	}
}
