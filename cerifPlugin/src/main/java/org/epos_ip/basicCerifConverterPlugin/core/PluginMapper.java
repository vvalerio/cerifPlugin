package org.epos_ip.basicCerifConverterPlugin.core;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.epos_ip.basicCerifConverterPlugin.GenericMapper.AvailableFormat;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.DDSS;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.Distribution;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.Facets;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDDSS;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONDistribution;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONFacetsKeywords;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.JSONFacetsOrganisations;
import org.epos_ip.basicCerifConverterPlugin.GenericMapper.ServiceParameter;
import org.epos_ip.utility.EnvironmentVariables;
import org.epos_ip.utility.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;


public class PluginMapper {

	private static final Logger LOGGER = Logger.getLogger("Mapper");
	private static final Gson gson = new Gson();

	private static final String CHILDREN = "children";
	private static final String PARAMS = "params";
	private static final String GET_ORIGINAL_URL = "getoriginalurl";
	private static final String SEARCH = "search";
	private static final String EXECUTE = "execute";
	private static final String DETAILS = "details";
	private static final String WORKSPACES = "workspaces";
	private static final String KEYWORDS = "keywords";
	private static final String ORGANISATIONS = "organisations";
	private static final String DISTRIBUTION = "distribution";
	private static final String DISTRIBUTIONS = "distributions";
	private static final String PARAMETERS = "parameters";
	private static final String API_PATH_EXECUTE  = "/webapi/v1.3/execute?id=";
	private static final String API_PATH_DETAILS  = "/webapi/v1.3/resources/details?id=";
	private static final String API_FORMAT = "&format=";

	private static final String NORTHEN_LAT  = "epos:northernmostLatitude";
	private static final String SOUTHERN_LAT  = "epos:southernmostLatitude";
	private static final String WESTERN_LON  = "epos:westernmostLongitude";
	private static final String EASTERN_LON  = "epos:easternmostLongitude";

	private PluginMapper() {}

/*	public static void main(String[] args) {
		String payload = "{\"ResultSet_distribution\":[{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"SWIRRL ICS-D\",\"labelclass\":\"LiterateProgramming\",\"txtvalue\":null,\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"dcat\",\"labelclass\":\"keyword\",\"txtvalue\":\"Python, Data Analysis, programming\",\"lang\":\"en\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"dct\",\"labelclass\":\"description\",\"txtvalue\":\"literate programming environment\",\"lang\":\"en\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"JupyterLab\",\"lang\":\"en\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"epos\",\"labelclass\":\"WebService\",\"txtvalue\":null,\"lang\":\"rdf\",\"startdate\":\"2021-07-21 14:44:19.026\",\"enddate\":\"2021-07-21 14:45:15.87\"},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"hydra\",\"labelclass\":\"entrypoint\",\"txtvalue\":\"https://epos-ics-d.brgm-rec.fr/swirrl-api\",\"lang\":null,\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"schema\",\"labelclass\":\"dateModified\",\"txtvalue\":null,\"lang\":\"rdf\",\"startdate\":\"2013-07-07 16:48:06\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"schema\",\"labelclass\":\"datePublished\",\"txtvalue\":null,\"lang\":\"rdf\",\"startdate\":\"2021-07-07 16:48:06\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":\"schema\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/WebService/JupyterLab\",\"lang\":null,\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclass\",\"txtvalue\":\"LiterateProgramming\",\"lang\":\"en\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclass\",\"txtvalue\":\"WebService\",\"lang\":\"rdf\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclass\",\"txtvalue\":\"dateModified\",\"lang\":\"rdf\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclass\",\"txtvalue\":\"datePublished\",\"lang\":\"rdf\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"SWIRRL ICS-D\",\"lang\":\"en\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"epos\",\"lang\":\"rdf\",\"startdate\":null,\"enddate\":null},{\"productid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"distributionid\":\"73a38a4c-9c35-4e37-8fe2-6dc1e7cbbc86\",\"labelscheme\":null,\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"schema\",\"lang\":\"rdf\",\"startdate\":null,\"enddate\":null}],\"ResultSet_parameters\":[]}";
		MappedResult mr = new MappedResult();
		System.out.println(payload);
		System.out.println(mr.convertResult(gson.fromJson(payload, JsonObject.class), null, "api.details", false));
	}
*/
	public static JsonElement convertResult(JsonObject resultSet, Map<String, Object> map, String kind,
			boolean paramEmpty) {

		JsonObject resultJson = new JsonObject();

		JsonArray facets = new JsonArray();
		JsonArray ddss = new JsonArray();
		Map<String, HashSet<String>> keywordsComplex = new TreeMap<>();
		Map<String, HashSet<String>> organisationsComplex = new TreeMap<>();
		ArrayList<DDSS> ddssList = new ArrayList<>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();

		HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
		Distribution obj = new Distribution();
		boolean distributionCreated = false;

		JsonObject superParameters = new JsonObject();
		JsonObject overrideParameters = new JsonObject();

		LOGGER.info("params " +superParameters.toString());

		if (resultSet.has(PARAMS))
			superParameters = resultSet.getAsJsonObject(PARAMS);

		if (superParameters != null) {
			if (superParameters.has(PARAMS)) {
				overrideParameters = superParameters.get(PARAMS).getAsJsonObject();
			}
		}

		LOGGER.info("Parameters: " + overrideParameters);
		LOGGER.info("Kind: " + kind);
		LOGGER.info("Prepare conversion...");

		resultSet.remove(PARAMS);

		for (Entry<String, JsonElement> entries : resultSet.entrySet()) {
			if (entries.getKey().contains("ResultSet_")) {
				JsonArray result = (JsonArray) entries.getValue();

				switch (entries.getKey().replace("ResultSet_", "")) {
				case SEARCH:
					ddssList = new ArrayList<DDSS>();
					ddss = new JsonArray();
					for (int i = 0; i < result.size(); i++) {
						JSONDDSS.generate(ddssList, distributionList, result, i);
					}
					break;
				case KEYWORDS:
					facets = new JsonArray();
					for (int i = 0; i < result.size(); i++) {
						JSONFacetsKeywords.generate(keywordsComplex, result, i);
					}
					break;
				case ORGANISATIONS:
					for (int i = 0; i < result.size(); i++) {
						JSONFacetsOrganisations.generate(organisationsComplex, result, i);
					}
					break;
				case DISTRIBUTION:
					for (int i = 0; i < result.size(); i++) {
						JSONDistribution.generate(result, obj, i);
					}
					distributionCreated = true;
					break;
				case PARAMETERS:
					for (int i = 0; i < result.size(); i++) {
						JSONDistribution.generate(result, parametersMap, i, obj);
					}
					break;
				default:
					break;
				}
			}
		}

		if (facets != null) {
			List<Object> ddssAvailable = Arrays
					.asList(ddssList.stream().filter(e -> e.getDdss() != null).map(e -> e.getDdss()).toArray());

			JsonArray kw = new JsonArray();

			keywordsComplex.entrySet().stream().forEach(r -> {
				for (String ddssItem : r.getValue()) {
					if (ddssAvailable.contains(ddssItem)) {
						if (!r.getKey().equals("")) {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("name", r.getKey());
							jsonObject.addProperty("id", Base64.getEncoder().encodeToString(r.getKey().getBytes()));
							kw.add(jsonObject);
						}
						break;
					}
				}

			});
			JsonObject keywordsObject = new JsonObject();
			keywordsObject.addProperty("name", KEYWORDS);
			keywordsObject.add(CHILDREN, kw);
			facets.add(keywordsObject);
			JsonArray org = new JsonArray();
			organisationsComplex.entrySet().forEach(r -> {
				for (String ddssItem : r.getValue()) {
					if (ddssAvailable.contains(ddssItem)) {
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("name", r.getKey());
						jsonObject.addProperty("id", Base64.getEncoder().encodeToString(r.getKey().getBytes()));
						org.add(jsonObject);
						break;
					}
				}
			});
			JsonObject organisationsObject = new JsonObject();
			organisationsObject.addProperty("name", ORGANISATIONS);
			organisationsObject.add(CHILDREN, org);
			facets.add(organisationsObject);
			resultJson.add("filters", facets);
		}
		if (ddss != null) {
			/**
			 * CHECK FOR EMPTY DDSSs
			 */
			for (Iterator<DDSS> iterator = ddssList.iterator(); iterator.hasNext();) {
				DDSS ddssNext = iterator.next();
				if (ddssNext.getDdss() == null)
					iterator.remove();
			}
			

			for (Iterator<Distribution> iterator = distributionList.iterator(); iterator.hasNext();) {
				Distribution ddssNext = iterator.next();
				if (ddssNext.getId() == null)
					iterator.remove();
			}
			
			/** CLEAN DISTRIBUTIONS **/
			Map<String, Distribution> mapDist = new HashMap<String, Distribution>();
			for (Distribution ds : distributionList) {
				String key = ds.getId();
				if (!mapDist.containsKey(key)) {
					mapDist.put(key, ds);
				}
			}
			Collection<Distribution> uniqueDistributions = mapDist.values();

			LOGGER.info("distributions "+  gson.toJson(uniqueDistributions));

			ddssList.forEach(ds -> {
				uniqueDistributions.forEach(dist -> {
					if (ds.getProductid().contains(dist.getProductid())) {
						ds.getDistributions().add(dist);
					}
				});
			});

			ddssList.forEach(ds -> {
				ds.getDistributions().forEach(distr -> {
					if (distr.getTemporalCoverage().getStartDate() != null)
						distr.getTemporalCoverage()
						.setStartDate(distr.getTemporalCoverage().getStartDate().replace(" ", "T").concat("Z"));
					if (distr.getTemporalCoverage().getEndDate() != null)
						distr.getTemporalCoverage()
						.setEndDate(distr.getTemporalCoverage().getEndDate().replace(" ", "T").concat("Z"));
					if (distr.getServiceTemporalCoverage().getStartDate() != null)
						distr.getServiceTemporalCoverage()
						.setStartDate(distr.getServiceTemporalCoverage().getStartDate().replace(" ", "T").concat("Z"));
					if (distr.getServiceTemporalCoverage().getEndDate() != null)
						distr.getServiceTemporalCoverage()
						.setEndDate(distr.getServiceTemporalCoverage().getEndDate().replace(" ", "T").concat("Z"));
					
					distr.setHref(EnvironmentVariables.API_HOST + API_PATH_DETAILS + distr.getId());
					
					distr.getAvailableFormats().forEach(af -> {
						af.setLabel(af.getLabel().toUpperCase());
						af.setFormat(af.getFormat().toLowerCase());
						if (af.getFormat().equals("application/vnd.ogc.wms_xml")) {
							af.setHref(distr.getServiceEndpoint().split("\\{")[0] + "?");
						} else
							af.setHref(EnvironmentVariables.API_HOST + API_PATH_EXECUTE + distr.getId()
							+ API_FORMAT
							+ af.getFormat().replace("\\/", "%2F").replace("\\+", "%2B"));
					});
					LinkedHashSet<AvailableFormat> hashSet = new LinkedHashSet<>(distr.getAvailableFormats());
					ArrayList<AvailableFormat> afList = new ArrayList<>(hashSet);
					distr.setAvailableFormats(afList);
					distr.setServiceEndpoint(null);
					distr.setKeywords(null);
					distr.setDataProvider(null);
					distr.setInternalID(null);
					distr.setSpatial(null);
					distr.setTemporalCoverage(null);
					distr.setServiceSpatial(null);
					distr.setServiceTemporalCoverage(null);

				});

			});
			
			System.out.println(distributionList);
			
	/*		JsonParser jsonParser = new JsonParser();
			JsonElement xJson = jsonParser.parse(gson.toJson(distributionList));
			
			JsonArray childrenDistributions = new JsonArray();
			
			JsonObject subcategory = new JsonObject();
			subcategory.addProperty("name", "SWIRRL API");
			subcategory.add("distributions", xJson); //TAROCCA
			
			childrenDistributions.add(subcategory);
			
			JsonObject domain = new JsonObject();
			domain.addProperty("name", "ICS-D");
			domain.add("children", childrenDistributions);
			
			JsonArray childrenResults = new JsonArray();
			childrenResults.add(domain);
			
			JsonObject results = new JsonObject();
			results.addProperty("name", "domains");
			results.add("children", childrenResults);
			
			
			resultJson.add("results", results);*/
			if (paramEmpty)
				try {
					resultJson.add("results", generateTree(Facets.getFacets(), ddssList));
				} catch (IOException e) {
					LOGGER.info("Error getting facets "+e);
				}
			else
				try {
					resultJson.add("results", generatePartialTree(Facets.getFacets(), ddssList));
				} catch (IOException e) {
					LOGGER.info("Error getting facets"+e);
				}
		}
		if (distributionCreated) {
			
			if (obj.getSpatial().getWkid() == null)
				obj.setSpatial(obj.getServiceSpatial());
			else
				obj.setSpatial(obj.getSpatial());

			
			if (obj.getTemporalCoverage().getStartDate() != null)
				obj.getTemporalCoverage()
				.setStartDate(obj.getTemporalCoverage().getStartDate().replace(" ", "T").concat("Z"));
			if (obj.getTemporalCoverage().getEndDate() != null)
				obj.getTemporalCoverage()
				.setEndDate(obj.getTemporalCoverage().getEndDate().replace(" ", "T").concat("Z"));
			if (obj.getServiceTemporalCoverage().getStartDate() != null)
				obj.getServiceTemporalCoverage()
				.setStartDate(obj.getServiceTemporalCoverage().getStartDate().replace(" ", "T").concat("Z"));
			if (obj.getServiceTemporalCoverage().getEndDate() != null)
				obj.getServiceTemporalCoverage()
				.setEndDate(obj.getServiceTemporalCoverage().getEndDate().replace(" ", "T").concat("Z"));
			

			if (!parametersMap.isEmpty()) {

				ArrayList<ServiceParameter> parameters = new ArrayList<>();
				parametersMap.entrySet().forEach(e -> {
					if (!e.getValue().isNull())
						parameters.add(e.getValue());
				});

				obj.getAvailableFormats().forEach(af -> {
					af.setLabel(af.getLabel().toUpperCase());
					af.setFormat(af.getFormat().toLowerCase());
					if (af.getFormat().equals("application/vnd.ogc.wms_xml"))
						af.setHref(obj.getServiceEndpoint().split("\\{")[0] + "?");
					else
						af.setHref(EnvironmentVariables.API_HOST + API_PATH_EXECUTE + obj.getId() + API_FORMAT
								+ af.getFormat().replace("\\/", "%2F").replace("\\+", "%2B"));
				});
				obj.setParameters(parameters);
				obj.getParameters().forEach(p -> {
					if (p.getProperty() != null && p.getEnumValue()!=null && p.getProperty().equals("schema:encodingFormat")) {
						p.getEnumValue().forEach(e -> {
							AvailableFormat af = new AvailableFormat(e.toUpperCase(), e, EnvironmentVariables.API_HOST
									+ API_PATH_EXECUTE + obj.getId() + API_FORMAT + e, "original");
							boolean exists = false;
							for (AvailableFormat fd : obj.getAvailableFormats()) {
								if (fd.getType().equals(af.getType()) && fd.getFormat().equals(af.getFormat())
										&& fd.getHref().equals(af.getHref()) && fd.getLabel().equals(af.getLabel())) {
									exists = true;
								}
							}
							if (!exists)
								obj.getAvailableFormats().add(af);
						});
					}
				});

				obj.getParameters().forEach(p -> {
					if (p != null) {
						if (p.getName() != null && p.getName().equals("service")) {
							if (p.getValue() != null && p.getValue().contains("WMS"))
								obj.getAvailableFormats().forEach(af -> {
									af.setHref(obj.getServiceEndpoint().split("\\{")[0] + "?");
								});
						}
					}
				});

			}

			if (kind.contains(DETAILS)) {
				if(obj.getServiceEndpoint()!=null) obj.setServiceEndpoint(obj.getServiceEndpoint().split("\\{")[0]);
			}

			if ((kind.contains(GET_ORIGINAL_URL) || kind.contains(EXECUTE) || kind.contains(WORKSPACES))
					&& overrideParameters != null && ( overrideParameters.has(PARAMS) || overrideParameters.has("format"))) {

				JsonObject subParams = overrideParameters.has(PARAMS)? gson.fromJson(overrideParameters.get(PARAMS).getAsString(), JsonObject.class) : new JsonObject();


				try {
					if ((kind.contains(GET_ORIGINAL_URL) || kind.contains(EXECUTE) || kind.contains(WORKSPACES))
							&& overrideParameters != null && overrideParameters.has(PARAMS)
							&& subParams.has("schema:startDate") && subParams.has("schema:endDate")) {
						String startDate = subParams.get("schema:startDate").getAsString();
						String endDate = subParams.get("schema:endDate").getAsString();
						for (int i = 0; i < obj.getParameters().size(); i++) {
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals("schema:startDate"))) {
								try {
									obj.getParameters().get(i)
									.setValue((startDate == null && obj.getParameters().get(i).isRequired())
											? obj.getParameters().get(i).getDefaultValue()
													: Utils.convert(startDate, Utils.EPOS_internal_format,
															obj.getParameters().get(i).getValuePattern()));
								} catch (ParseException e) {
									LOGGER.info("Error converting result to json element"+e);
								}
							}
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals("schema:endDate"))) {
								try {
									obj.getParameters().get(i)
									.setValue((endDate == null && obj.getParameters().get(i).isRequired())
											? obj.getParameters().get(i).getDefaultValue()
													: Utils.convert(endDate, Utils.EPOS_internal_format,
															obj.getParameters().get(i).getValuePattern()));
								} catch (ParseException e) {
									LOGGER.info("Error converting result to json element"+e);
								}
							}
						}
					}
				} catch (NullPointerException e) {
					LOGGER.info("Error converting result to json element"+e);
				}

				try {
					if ((kind.contains(GET_ORIGINAL_URL) || kind.contains(EXECUTE) || kind.contains(WORKSPACES))
							&& overrideParameters != null && overrideParameters.has(PARAMS)
							&& subParams.has(NORTHEN_LAT) && subParams.has(SOUTHERN_LAT)
							&& subParams.has(WESTERN_LON)
							&& subParams.has(EASTERN_LON)) {
						String NLatitude = subParams.get(NORTHEN_LAT).getAsString();
						String SLatitude = subParams.get(SOUTHERN_LAT).getAsString();
						String WLongitude = subParams.get(WESTERN_LON).getAsString();
						String ELongitude = subParams.get(EASTERN_LON).getAsString();
						for (int i = 0; i < obj.getParameters().size(); i++) {
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals(WESTERN_LON))) {
								obj.getParameters().get(i)
								.setValue((WLongitude == null && obj.getParameters().get(i).isRequired())
										? obj.getParameters().get(i).getDefaultValue()
												: WLongitude);
							}
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals(EASTERN_LON))) {
								obj.getParameters().get(i)
								.setValue((ELongitude == null && obj.getParameters().get(i).isRequired())
										? obj.getParameters().get(i).getDefaultValue()
												: ELongitude);
							}
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals(NORTHEN_LAT))) {
								obj.getParameters().get(i)
								.setValue((NLatitude == null && obj.getParameters().get(i).isRequired())
										? obj.getParameters().get(i).getDefaultValue()
												: NLatitude);
							}
							if (obj.getParameters().get(i).getProperty() != null
									&& (obj.getParameters().get(i).getProperty().equals(SOUTHERN_LAT))) {
								obj.getParameters().get(i)
								.setValue((SLatitude == null && obj.getParameters().get(i).isRequired())
										? obj.getParameters().get(i).getDefaultValue()
												: SLatitude);
							}
						}
					}
				} catch (NullPointerException e) {
					LOGGER.info("Error converting result to json element"+e);
				}

				try {
					if ((kind.contains(GET_ORIGINAL_URL) || kind.contains(EXECUTE) || kind.contains(WORKSPACES))
							&& overrideParameters != null && overrideParameters.has(PARAMS)) {

						JsonObject toJsonParameters = gson.fromJson(overrideParameters.get(PARAMS).getAsString(),
								JsonObject.class);
						for (int i = 0; i < obj.getParameters().size(); i++) {
							if (toJsonParameters.has(obj.getParameters().get(i).getName())) {
								if (!StringUtils.isBlank(
										toJsonParameters.get(obj.getParameters().get(i).getName()).getAsString())
										&& obj.getParameters().get(i).getProperty() != null
										&& obj.getParameters().get(i).getProperty().equals("schema:startDate")) {
									try {
										LOGGER.info("X0 " + obj.getParameters().get(i).getDefaultValue());
										obj.getParameters().get(i)
										.setValue(Utils.convert(
												toJsonParameters.get(obj.getParameters().get(i).getName())
												.getAsString(),
												Utils.EPOS_internal_format,
												obj.getParameters().get(i).getValuePattern()));
										LOGGER.info("X1 " + obj.getParameters().get(i).getDefaultValue());
									} catch (ParseException e) {
										LOGGER.info("Error converting result to json element"+e);
									}
								} else if (!StringUtils.isBlank(
										toJsonParameters.get(obj.getParameters().get(i).getName()).getAsString())
										&& obj.getParameters().get(i).getProperty() != null
										&& obj.getParameters().get(i).getProperty().equals("schema:endDate")) {
									try {
										obj.getParameters().get(i)
										.setValue(Utils.convert(
												toJsonParameters.get(obj.getParameters().get(i).getName())
												.getAsString(),
												Utils.EPOS_internal_format,
												obj.getParameters().get(i).getValuePattern()));
									} catch (ParseException e) {
										LOGGER.info("Error converting result to json element"+e);
									}
								} else if (!StringUtils.isBlank(
										toJsonParameters.get(obj.getParameters().get(i).getName()).getAsString())) {
									obj.getParameters().get(i).setValue(
											toJsonParameters.get(obj.getParameters().get(i).getName()).getAsString());
								}
								LOGGER.info( "XX " + obj.getParameters().get(i).getValue());
							}
						}
					}
					if ((kind.contains(GET_ORIGINAL_URL) || kind.contains(EXECUTE) || kind.contains(WORKSPACES))
							&& overrideParameters != null && overrideParameters.has("format")
							&& !overrideParameters.get("format").getAsString().equals("application/epos.geo+json")) { 
						String format = overrideParameters.get("format").getAsString();
						for (int i = 0; i < obj.getParameters().size(); i++) {
							if (obj.getParameters().get(i).getProperty() != null
									&& obj.getParameters().get(i).getProperty().equals("schema:encodingFormat")) {
								obj.getParameters().get(i).setValue(format);
							}
						}
					}
				} catch (NullPointerException e) {
					LOGGER.info("Error converting result to json element"+e);
				}

			}

			Gson gson = new Gson();
			JsonParser jsonParser = new JsonParser();
			resultJson = jsonParser.parse(gson.toJson(obj)).getAsJsonObject();
		}

		if (kind.contains(EXECUTE))
			resultJson.add(PARAMS, superParameters);

		System.out.println("********** MAPPED *********\n"+resultJson);

		return resultJson;
	}

	private static JsonElement generatePartialTree(JsonElement tree, ArrayList<DDSS> ddssList) {
		// BASE CASE
		if (tree.isJsonObject() && tree.getAsJsonObject().has("ddss")) {
			JsonArray valueOut = new JsonArray();
			ddssList.forEach(ddss -> {
				String[] ddssS = tree.getAsJsonObject().get("ddss").getAsString().split("\\,");
				for (String ds : ddssS) {
					if (ds.trim().equals(ddss.getDdss())) {
						for (JsonElement i : gson.toJsonTree(ddss.getDistributions()).getAsJsonArray())
							valueOut.getAsJsonArray().add(i);
					}
				}

			});
			if (valueOut.size() > 0) {
				tree.getAsJsonObject().add(DISTRIBUTION, valueOut);
			}
		}

		// RECURSIVE
		if (tree.isJsonObject() && tree.getAsJsonObject().has(CHILDREN)) {
			for (int i = 0; i < tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().size(); i++) {
				generatePartialTree(tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject(),
						ddssList);
				if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject().has("ddss")) {
					if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
							.has(DISTRIBUTION))
						if (tree.getAsJsonObject().has(DISTRIBUTIONS))
							tree.getAsJsonObject().get(DISTRIBUTIONS).getAsJsonArray()
							.addAll(tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i)
									.getAsJsonObject().get(DISTRIBUTION).getAsJsonArray());
						else
							tree.getAsJsonObject().add(DISTRIBUTIONS, tree.getAsJsonObject().get(CHILDREN)
									.getAsJsonArray().get(i).getAsJsonObject().get(DISTRIBUTION).getAsJsonArray());
					tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().remove(i);
					i--;
				} else if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.has(CHILDREN)
						&& tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.get(CHILDREN).getAsJsonArray().size() == 0) {
					tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject().remove(CHILDREN);
					i--;
				} else if (!tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.has(CHILDREN)
						&& !tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.has(DISTRIBUTIONS)) { 
					tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().remove(i).getAsJsonObject().remove("name");
					i--;
				}
			}
		}

		return tree;
	}

	private static JsonElement generateTree(JsonElement tree, ArrayList<DDSS> ddssList) {
		// BASE CASE
		if (tree.isJsonObject() && tree.getAsJsonObject().has("ddss")) {
			JsonArray valueOut = new JsonArray();
			ddssList.forEach(ddss -> {
				String[] ddssS = tree.getAsJsonObject().get("ddss").getAsString().split("\\,");
				for (String ds : ddssS) {
					if (ds.trim().equals(ddss.getDdss())) {
						for (JsonElement i : gson.toJsonTree(ddss.getDistributions()).getAsJsonArray())
							valueOut.getAsJsonArray().add(i);
					}
				}
			});
			tree.getAsJsonObject().add(DISTRIBUTION, valueOut);
		}

		// RECURSIVE
		if (tree.isJsonObject() && tree.getAsJsonObject().has(CHILDREN)) {
			for (int i = 0; i < tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().size(); i++) {
				generateTree(tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject(),
						ddssList);
				if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject().has("ddss")) {
					if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
							.has(DISTRIBUTION))
						if (tree.getAsJsonObject().has(DISTRIBUTIONS))
							tree.getAsJsonObject().get(DISTRIBUTIONS).getAsJsonArray()
							.addAll(tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i)
									.getAsJsonObject().get(DISTRIBUTION).getAsJsonArray());
						else
							tree.getAsJsonObject().add(DISTRIBUTIONS, tree.getAsJsonObject().get(CHILDREN)
									.getAsJsonArray().get(i).getAsJsonObject().get(DISTRIBUTION).getAsJsonArray());
					tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().remove(i);
					i--;
				} else if (tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.has(CHILDREN)
						&& tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject()
						.get(CHILDREN).getAsJsonArray().size() == 0) {
					tree.getAsJsonObject().get(CHILDREN).getAsJsonArray().get(i).getAsJsonObject().remove(CHILDREN);
					i--;
				}
			}
		}

		return tree;
	}
}
