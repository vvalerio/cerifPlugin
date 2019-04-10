package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bson.Document;
import org.epos_ip.beans.DDSS;
import org.epos_ip.beans.Distribution;
import org.epos_ip.utility.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class JSONMapper extends GenericMapper {
	private JsonObject resultJson;

	public JSONMapper() {
		super();
		resultJson = new JsonObject();
	}

	@Override
	public JsonObject generate(JsonObject resultSet, String researchType, String format) {

		System.out.println("RESEARCH TYPE: "+researchType);
		JsonObject summary = null;
		JsonArray keywords = null;
		JsonArray domains = null;
		JsonArray facets = null;
		JsonArray ddss = null;
		HashMap<String, HashSet<String>> keywordsComplex = new HashMap<>();
		HashMap<String, HashSet<String>> organisationsComplex = new HashMap<>();
		ArrayList<DDSS> ddssList = new ArrayList<DDSS>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();
		//JsonObject responseGeoJSON = null;

		switch(researchType)
		{
		case "search":
			summary = new JsonObject();
			summary.addProperty("Page", 1);
			summary.addProperty("resultsPerPage", 25);
			break;
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
		default:
			if(resultSet.getAsJsonObject().has("TCSRESULT")) {
				return TCSMapper.mapping_TCSformat2GEOJson(resultSet.getAsJsonObject().get("TCSRESULT").getAsString(), format);
			}
			else return new JsonObject();

		}
		try {
			int counter = 0;

			JsonArray results = new JsonArray();

			for(Entry<String, JsonElement> entries : resultSet.entrySet())
			{
				JsonArray result = (JsonArray) entries.getValue();
				for(int i = 0; i<result.size(); i++)
				{
					counter++;
					switch(entries.getKey().replaceAll("ResultSet_", "").split("_")[0])
					{
					case "person":
						JSONPerson.generate(results, result, i);
						summary.addProperty("numberOfResults", counter);
						resultJson.add("summary", summary);
						resultJson.add("results", results);
						break;
					case "organisation":
						JSONOrganisation.generate(results, result, i);
						summary.addProperty("numberOfResults", counter);
						resultJson.add("summary", summary);
						resultJson.add("results", results);
						break;
					case "webservice":
						JSONWebservice.generate(results, result, i);
						summary.addProperty("numberOfResults", counter);
						resultJson.add("summary", summary);
						resultJson.add("results", results);
						break;
					case "domains":
						JSONDomains.generate(resultJson, results, domains, result, i);
						resultJson.add("domains", domains);
						break;
					case "keywords":
						JSONKeywords.generate(keywords, result, i);
						resultJson.add(researchType, keywords);
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
		}catch(Exception e) {e.printStackTrace();}

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
			resultJson.add(researchType, facets);
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
		return resultJson;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		//return resultJson.toString();
		Gson gson = new GsonBuilder().create();
		return gson.toJson(resultJson);
	}

}
