package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JSONKeywords {
	public static void generate(JsonArray keywords, JsonArray result, int i)
	{
		try{
			String keyList = result.get(i).getAsJsonObject().get("keyword").getAsString();
			for(String s : keyList.split(", |,"))
			{
				boolean found = false;
				for(JsonElement e : keywords)
				{
					if(e.getAsString().equals(s.toLowerCase())) found = true;
				}
				if(!found) keywords.add(s.toLowerCase());

			}
		}
		catch(Exception e){}
	}
}
