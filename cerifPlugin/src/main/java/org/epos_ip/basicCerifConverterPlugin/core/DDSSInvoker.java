package org.epos_ip.basicCerifConverterPlugin.core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDDSS;
import org.epos_ip.beans.DDSS;
import org.epos_ip.beans.Distribution;
import org.epos_ip.utility.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DDSSInvoker{
	
	public static void main(String args[]) throws ClassNotFoundException{
	      Class.forName("org.epos_ip.basicCerifConverterPlugin.core.DDSSInvoker");
	      System.out.println("DDSSInvoker class successfully loaded");
	   }

	/*protected DDSSInvoker(ConversionDescriptor conversion) throws PluginConfigurationException {
		super(conversion);
		// TODO Auto-generated constructor stub
	}*/

	private static Gson gson = new Gson();

	//@Override
	//protected Optional<String> doInvoke(String payload) {
	public DDSSInvoker(String payload) {

		JsonObject resultJson = new JsonObject();
		JsonObject payloadJson  =  gson.fromJson(payload, JsonObject.class);
		ArrayList<DDSS> ddssList = new ArrayList<DDSS>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();
		JsonArray ddss = new JsonArray();
		try {

			for(Entry<String, JsonElement> entries : payloadJson.entrySet())
			{
				JsonArray result = (JsonArray) entries.getValue();
				for(int i = 0; i<result.size(); i++)
				{
					switch(entries.getKey().replaceAll("ResultSet_", "").split("_")[0])
					{
					case "ddss":
						JSONDDSS.generate(ddssList, distributionList, result, i);
						break;
					}
				}
			}
		}catch(Exception e) {e.printStackTrace();}

		if(ddss!= null) {
			Gson gson = new Gson();
			JsonParser jsonParser = new JsonParser();

			System.out.println();

			/**
			 * CHECK FOR EMPTY DDSSs
			 */
			for(Iterator<DDSS> iterator = ddssList.iterator(); iterator.hasNext();) {
				DDSS ddssNext = iterator.next();
				if(ddssNext.getDdss()==null) 
					iterator.remove();
			}


			ddssList.forEach(ds -> {
				//System.out.println("productlist: "+ds.getProductid().toString());
				distributionList.forEach(dist -> {
					System.out.println(dist.getProductid().toString());
					if(ds.getProductid().contains(dist.getProductid())) {
						ds.getDistributions().add(dist);
					}
				});
			});

			ddssList.forEach(ds -> {
				System.out.println(ds.toString());
			

			});
			resultJson.getAsJsonObject().add("ddss", jsonParser.parse(gson.toJson(ddssList)).getAsJsonArray());
		}

		System.out.println(resultJson.getAsString());
	}

}
