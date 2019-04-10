package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bson.Document;
import org.epos_ip.basicCerifConverterPlugin.core.DatabaseConnection;
import org.epos_ip.beans.Distribution;
import org.epos_ip.beans.ServiceParameter;
import org.epos_ip.beans.Webservice;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

public class JSONMapperDetail extends GenericMapper {

	private String mappedJson = "{}";
	public JSONMapperDetail() {
		super();
	}

	@Override
	public JsonObject generate(JsonObject resultSet, String researchType, String format) {

		System.out.println(resultSet.toString());
		JsonObject resultJson = new JsonObject();

		HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
		ArrayList<Webservice> wsl = new ArrayList<Webservice>();
		Distribution obj = new Distribution();
		boolean distributionCreated = false;
		try {
			for(Entry<String, JsonElement> entries : resultSet.entrySet())
			{
				if(entries.getKey().contains("ResultSet_")){
					if(entries.getValue().getAsJsonArray().size()==0) continue;
					else
					{
						System.out.println(entries.getKey());
						System.out.println(entries.getValue());
						JsonArray result = (JsonArray) entries.getValue();
						for(int i = 0; i<result.size(); i++)
						{
							String type = entries.getKey().replaceAll("ResultSet_", "");

							if(type.split("_")[0].equals("person"))
							{
								resultJson.addProperty("type", "person");
							}
							if(type.split("_")[0].equals("organisation"))
							{
								resultJson.addProperty("type", "organisation");
							}
							if(type.split("_")[0].equals("webservice"))
							{
								JSONWebserviceDetail.generate(result, wsl, i, parametersMap);
								System.out.println("WEBSERVICE GENERATED");
							}
							if(type.split("_")[0].equals("distribution"))
							{
								distributionCreated = true;
								if(format.equals("DEFAULT")) JSONDistributionDefault.generate(result, obj, i);
								else JSONDistribution.generate(result, obj, i);
								System.out.println("DISTRIBUTION GENERATED");
							}
							if(type.split("_")[0].equals("parameters"))
							{
								if(format.equals("DEFAULT")) JSONDistributionDefault.generate(result, parametersMap, i, obj);
								else JSONDistribution.generate(result, parametersMap, i, obj);

								System.out.println("PARAMETERS GENERATED");
							}
						}

					}
				}
			}
			System.out.println("FORMAT"+format);

			/** clean information **/
			if(format.equals("DEFAULT")) {
				ArrayList<String> keysToRemove = new ArrayList<>();
				parametersMap.keySet().forEach(pm -> {
					if(parametersMap.get(pm).getDefaultValue()==null) 
						keysToRemove.add(pm);
				});
				keysToRemove.forEach(k -> parametersMap.remove(k));
				System.out.println("distributionid: "+obj.getId());
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("distributionid", obj.getId());
				FindIterable<Document> cursor = DatabaseConnection.getInstance().getTableDefaults().find(whereQuery);
				for (Document doc : cursor) {
					System.out.println(doc.toJson());
					if(doc.get("distributionid").toString().equals(obj.getId())) {
						obj.setDefaultURL(doc.get("defaultURL").toString());
						obj.setDefaultFormat(doc.get("defaultFormat").toString());
					}
				}
			} else if(format.equals("DETAILS")){
				BasicDBObject whereQuery = new BasicDBObject();
				whereQuery.put("distributionid", obj.getId());
				FindIterable<Document> cursor = DatabaseConnection.getInstance().getTableDefaults().find(whereQuery);
				for (Document doc : cursor) {
					System.out.println(doc.toJson());
					if(doc.get("distributionid").toString().equals(obj.getId())) {
						obj.setOriginalFormat(doc.get("originalFormat").toString());
						obj.setDefaultURL(doc.get("defaultURL").toString());
						obj.setDefaultFormat(doc.get("defaultFormat").toString());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(!wsl.isEmpty())
		{
			Webservice ws = wsl.get(0);
			JsonArray result = new JsonArray();
			JsonObject jj = new JsonObject();
			jj.addProperty("name", ws.getTitle().get(0));
			jj.addProperty("type", "webservice");
			jj.addProperty("endpoint", ws.getURI());
			jj.addProperty("response", ws.getFormat().get(0));
			jj.addProperty("ddss-id", ws.getDdssid());
			JsonArray actions = new JsonArray();
			String[] actionArray = ws.getActions().split(",");
			for(String s : actionArray)
				actions.add(s.replace(" ", ""));
			jj.add("actions", actions);

			JsonArray parameters = new JsonArray();
			ws.getParameter().forEach((sp) -> {
				JsonObject singleParameter = new JsonObject();
				try{
					if(sp.getName()!=null) singleParameter.addProperty("name", sp.getName());
					if(sp.getType()!=null) singleParameter.addProperty("type", sp.getType());
					if(sp.getLabel()!=null) singleParameter.addProperty("label", sp.getLabel());
					/** required : true / false **/
					singleParameter.addProperty("required",false);
					JsonArray values = new JsonArray();
					if(sp.getMaxValue()!=null) singleParameter.addProperty("max",sp.getMaxValue()); // min value
					if(sp.getMinValue()!=null)  singleParameter.addProperty("min",sp.getMinValue()); // max value
					if(sp.getType().equals("string")) singleParameter.addProperty("regex","");
					if(sp.getType().equals("date")) singleParameter.addProperty("format","yyyy-MM-ddTHH:mm:ss.SSS");
					/** regex : <([A-Z][A-Z0-9]*)\b[^>]*>(.*?)</1> **/
					/** format : yyyy-MM-dd HH:mm:ss.SSS/hex (#FF0000) / mathematical (1.011×105) / engineering (101.1×103) / computer (1.1030402E5) **/
					if(sp.getMinValue()!=null || sp.getMaxValue()!=null) singleParameter.addProperty("free", true);
					if(!sp.getValue().isEmpty()) {
						sp.getValue().forEach(e -> values.add(e));
						singleParameter.addProperty("free", false);
						singleParameter.add("enum", values);
					}
					if(sp.getVersion()!=null) singleParameter.addProperty("version", sp.getVersion());
				}catch(Exception e) {}
				parameters.add(singleParameter);
			});

			jj.add("parameters", parameters);
			result.add(jj);
			resultJson.add("operations", result);

			mappedJson = resultJson.toString();
		}
		if(distributionCreated) {
			System.out.println(obj.toString());
			if(!parametersMap.isEmpty()) {
				System.out.println(parametersMap.toString());
				ArrayList<ServiceParameter> parameters = new ArrayList<>();
				parametersMap.entrySet().forEach(e -> {
					if(!e.getValue().isNull()) parameters.add(e.getValue());
				});
				obj.setParameter(parameters);
			}
			Gson gson = new Gson();
			JsonParser jsonParser = new JsonParser();
			resultJson.getAsJsonObject().add("distribution", jsonParser.parse(gson.toJson(obj)));
		}
		System.out.println("COME BACK");
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
		return mappedJson;
	}

}
