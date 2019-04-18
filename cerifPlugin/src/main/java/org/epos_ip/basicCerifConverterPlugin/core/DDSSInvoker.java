package org.epos_ip.basicCerifConverterPlugin.core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import org.bson.Document;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDDSS;
import org.epos_ip.beans.DDSS;
import org.epos_ip.beans.Distribution;
import org.epos_ip.converter.common.exception.PayloadProcessingException;
import org.epos_ip.converter.common.exception.PluginConfigurationException;
import org.epos_ip.converter.common.java.CallableJavaPlugin;
import org.epos_ip.converter.common.plugin.type.ConversionDescriptor;
import org.epos_ip.utility.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class DDSSInvoker extends CallableJavaPlugin {
	
	public static void main(String args[]) throws ClassNotFoundException{
	      Class.forName("org.epos_ip.basicCerifConverterPlugin.core.DDSSInvoker");
	      System.out.println("DDSSInvoker class successfully loaded");
	   }

	public DDSSInvoker(ConversionDescriptor conversion) throws PluginConfigurationException {
		super(conversion);
		// TODO Auto-generated constructor stub
	}

	private static Gson gson = new Gson();

	@Override
	protected Optional<String> doInvoke(String payload) throws PayloadProcessingException {
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

			BasicDBObject ddssQueryList = new BasicDBObject();
			FindIterable<Document> ddssCursorList = DatabaseConnection.getInstance().getDDSSTable().find(ddssQueryList);
			ArrayList<String> ddssValue = new ArrayList<>();
			ddssList.forEach(e -> ddssValue.add(e.getDdss()));
			for (Document doc : ddssCursorList) {
				if(!ddssValue.contains(doc.get("ddss").toString())) {
					DDSS ddss1 = new DDSS();
					ddss1.setDdss(doc.get("ddss").toString());
					ddssList.add(ddss1);
				}
			}

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
				BasicDBObject ddssQuery = new BasicDBObject();
				ddssQuery.put("ddss", ds.getDdss());
				FindIterable<Document> ddssCursor = DatabaseConnection.getInstance().getDDSSTable().find(ddssQuery);
				for (Document doc : ddssCursor) {
					if(doc.get("ddss").toString().equals(ds.getDdss())) {
						ds.setTitle(doc.get("name").toString());
					}
				}

				ds.getDistributions().forEach(distr -> {
					BasicDBObject whereQuery = new BasicDBObject();
					whereQuery.put("distributionid", distr.getId());
					FindIterable<Document> cursor = DatabaseConnection.getInstance().getTableDefaults().find(whereQuery);
					for (Document doc : cursor) {
						if(doc.get("distributionid").toString().equals(distr.getId())) {
							distr.setDefaultURL(doc.get("defaultURL").toString());
							if(doc.containsKey("defaultFormat")) distr.setDefaultFormat(doc.get("defaultFormat").toString());
							else distr.setDefaultFormat(doc.get("originalFormat").toString());
						}
					}
					try {
						if(distr.getStartDate()!=null || distr.getEndDate()!=null) {
							distr.setStartDate(Utils.convert(distr.getStartDate(),distr.getPattern(),Utils.EPOS_internal_format));
							distr.setEndDate(Utils.convert(distr.getEndDate(),distr.getPattern(),Utils.EPOS_internal_format));
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				});

			});
			resultJson.getAsJsonObject().add("ddss", jsonParser.parse(gson.toJson(ddssList)).getAsJsonArray());
		}
		return Optional.of(resultJson.getAsString());
	}

}
