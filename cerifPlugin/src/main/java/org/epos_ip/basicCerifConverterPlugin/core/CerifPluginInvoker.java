package org.epos_ip.basicCerifConverterPlugin.core;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import org.bson.Document;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDDSS;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDistribution;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDistributionDefault;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDomains;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONFacetsKeywords;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONFacetsOrganisations;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONKeywords;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONOrganisation;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONPerson;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONWebservice;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONWebserviceDetail;
import org.epos_ip.beans.DDSS;
import org.epos_ip.beans.Distribution;
import org.epos_ip.beans.ServiceParameter;
import org.epos_ip.beans.Webservice;
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

public class CerifPluginInvoker extends CallableJavaPlugin {

	public static void main(String args[]) throws ClassNotFoundException{
		Class.forName("org.epos_ip.basicCerifConverterPlugin.core.CerifPluginInvoker");
		System.out.println("DDSSInvoker class successfully loaded");

		System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_update\":[]}"));
		System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_facets_keywords\":[],\"ResultSet_facets_organisation\":[]}"));
	}

	public CerifPluginInvoker(ConversionDescriptor conversion) throws PluginConfigurationException {
		super(conversion);
		// TODO Auto-generated constructor stub
	}

	private static Gson gson = new Gson();

	@Override
	protected Optional<String> doInvoke(String payload) throws PayloadProcessingException {
		JsonObject resultJson = new JsonObject();
		JsonObject payloadJson  =  gson.fromJson(payload, JsonObject.class);

		JsonArray keywords = null;
		JsonArray domains = null;
		JsonArray facets = null;
		JsonArray ddss = null;
		HashMap<String, HashSet<String>> keywordsComplex = new HashMap<>();
		HashMap<String, HashSet<String>> organisationsComplex = new HashMap<>();
		ArrayList<DDSS> ddssList = new ArrayList<DDSS>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();

		JsonArray results = new JsonArray();

		for(Entry<String, JsonElement> entries : payloadJson.entrySet())
		{
			JsonArray result = (JsonArray) entries.getValue();
			for(int i = 0; i<result.size(); i++)
			{
				switch(entries.getKey().replaceAll("ResultSet_", "").split("_")[0])
				{
				case "person":
					JSONPerson.generate(results, result, i);
					break;
				case "organisation":
					JSONOrganisation.generate(results, result, i);
					break;
				case "webservice":
					JSONWebservice.generate(results, result, i);
					break;
				case "domains":
					domains = new JsonArray();
					JSONDomains.generate(resultJson, results, domains, result, i);
					break;
				case "keywords":
					keywords = new JsonArray();
					JSONKeywords.generate(keywords, result, i);
					break;
				case "facets":
					facets = new JsonArray();
					keywords = new JsonArray();
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equals("keywords")) {
						JSONFacetsKeywords.generate(keywordsComplex, result, i);
					}
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equals("organisation")) {
						JSONFacetsOrganisations.generate(organisationsComplex, result, i);
					}
					break;
				case "ddss":
					ddss = new JsonArray();
					JSONDDSS.generate(ddssList, distributionList, result, i);
					break;
				}
			}
		}

		if(facets!= null) {
			JsonArray kw = new JsonArray();
			keywordsComplex.entrySet().forEach(r -> {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("name", r.getKey());
				JsonArray ddssids = new JsonArray();
				r.getValue().forEach(e -> ddssids.add(e));
				jsonObject.add("ddss", ddssids);
				kw.add(jsonObject);
			});
			JsonObject keywordsObject = new JsonObject();
			keywordsObject.addProperty("name", "keywords");
			keywordsObject.add("children", kw);
			facets.add(keywordsObject);
			JsonArray org = new JsonArray();
			organisationsComplex.entrySet().forEach(r -> {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("name", r.getKey());
				JsonArray ddssids = new JsonArray();
				r.getValue().forEach(e -> ddssids.add(e));
				jsonObject.add("ddss", ddssids);
				org.add(jsonObject);
			});
			JsonObject organisationsObject = new JsonObject();
			organisationsObject.addProperty("name", "organisations");
			organisationsObject.add("children", org);
			facets.add(organisationsObject);
			resultJson.add("facets", facets);
		}
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
		return Optional.of(resultJson.toString());
	}

	protected static Optional<String> doTest(String payload) {

		System.out.println(payload);
		JsonObject resultJson = new JsonObject();
		JsonObject payloadJson  =  gson.fromJson(payload, JsonObject.class);

		System.out.println("PAYLOAD: "+payloadJson.toString());
		JsonArray keywords = null;
		JsonArray domains = null;
		JsonArray facets = null;
		JsonArray ddss = null;
		HashMap<String, HashSet<String>> keywordsComplex = new HashMap<>();
		HashMap<String, HashSet<String>> organisationsComplex = new HashMap<>();
		ArrayList<DDSS> ddssList = new ArrayList<DDSS>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();

		JsonArray results = new JsonArray();

		System.out.println("ENTRY SET: "+payloadJson.entrySet());

		for(Entry<String, JsonElement> entries : payloadJson.entrySet())
		{
			switch(entries.getKey().replaceAll("ResultSet_", "").split("_")[0])
			{
			case "domains":
				domains = new JsonArray();
				break;
			case "keywords":
				keywords = new JsonArray();
				break;
			case "facets":
				facets = new JsonArray();
				keywords = new JsonArray();
				break;
			case "ddss":
				ddss = new JsonArray();
				break;
			}
			JsonArray result = (JsonArray) entries.getValue();
			for(int i = 0; i<result.size(); i++)
			{
				switch(entries.getKey().replaceAll("ResultSet_", "").split("_")[0])
				{
				case "person":
					JSONPerson.generate(results, result, i);
					break;
				case "organisation":
					JSONOrganisation.generate(results, result, i);
					break;
				case "webservice":
					JSONWebservice.generate(results, result, i);
					break;
				case "domains":
					JSONDomains.generate(resultJson, results, domains, result, i);
					break;
				case "keywords":
					JSONKeywords.generate(keywords, result, i);
					break;
				case "facets":
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equals("keywords")) {
						JSONFacetsKeywords.generate(keywordsComplex, result, i);
					}
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equals("organisation")) {
						JSONFacetsOrganisations.generate(organisationsComplex, result, i);
					}
					break;
				case "ddss":
					JSONDDSS.generate(ddssList, distributionList, result, i);
					break;
				}
			}
		}

		if(facets!= null) {
			JsonArray kw = new JsonArray();
			System.out.println("STO UQI");
			keywordsComplex.entrySet().forEach(r -> {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("name", r.getKey());
				JsonArray ddssids = new JsonArray();
				r.getValue().forEach(e -> ddssids.add(e));
				jsonObject.add("ddss", ddssids);
				kw.add(jsonObject);
			});
			JsonObject keywordsObject = new JsonObject();
			keywordsObject.addProperty("name", "keywords");
			keywordsObject.add("children", kw);
			facets.add(keywordsObject);
			JsonArray org = new JsonArray();
			organisationsComplex.entrySet().forEach(r -> {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("name", r.getKey());
				JsonArray ddssids = new JsonArray();
				r.getValue().forEach(e -> ddssids.add(e));
				jsonObject.add("ddss", ddssids);
				org.add(jsonObject);
			});
			JsonObject organisationsObject = new JsonObject();
			organisationsObject.addProperty("name", "organisations");
			organisationsObject.add("children", org);
			facets.add(organisationsObject);
			resultJson.add("facets", facets);
		}
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

		System.out.println(resultJson.toString());
		return Optional.of(resultJson.toString());
	}
}
