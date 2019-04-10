package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.JsonArray;

public class JSONFacetsKeywords {
	public static void generate(HashMap<String, HashSet<String>> keywords, JsonArray result, int i)
	{
		for(String keyword : result.get(i).getAsJsonObject().get("keyword").getAsString().split(",")) {
			String keyname = keyword.trim().toLowerCase();
			String ddssname = result.get(i).getAsJsonObject().get("ddss").getAsString();

			if(keywords.containsKey(keyname)) {
				keywords.get(keyname).add(ddssname);
			}
			else {
				HashSet<String> hs = new HashSet<String>();
				hs.add(ddssname);
				keywords.put(keyname, hs);
			}

		}
	}
}
