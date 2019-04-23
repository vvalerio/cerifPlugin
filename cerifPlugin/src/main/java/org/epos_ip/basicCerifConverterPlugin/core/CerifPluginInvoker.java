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

		//System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_update\":[]}"));
		//System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_facets_keywords\":[],\"ResultSet_facets_organisation\":[]}"));

		//System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_distribution_details\":[{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"file:///Seismology/EDSF/WFS-CrustalFaults\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dcat\",\"labelclass\":\"Distribution\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"description\",\"txtvalue\":\"The European Database of Seismogenic Faults (EDSF) was compiled in the framework of the EU Project SHARE, Work Package 3, Task 3.2. EDSF includes only faults that are deemed to be capable of generating earthquakes of magnitude equal to or larger than 5.5 and aims at ensuring a homogeneous input for use in ground-shaking hazard assessment in the Euro-Mediterranean area. Several research institutions participated in this effort with the contribution of many scientists (see the Database section for a full list). The EDSF database and website are hosted and maintained by INGV.\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"Seismology/EDSF/Distribution/WFS_CrustalFaults\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"license\",\"txtvalue\":\"https://creativecommons.org/licenses/by-sa/4.0/\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"European Database of Seismogenic Faults - WFS - Crustal Faults\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclass\",\"txtvalue\":\"Distribution\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclass\",\"txtvalue\":\"WEB_SERVICE\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclass\",\"txtvalue\":\"XML\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"dcat\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"Distribution Types\"},{\"productid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelclass\":\"cfclassscheme\",\"txtvalue\":\"File Types\"}],\"ResultSet_parameters_details\":[{\"srcid\":\"db89e1fe-13f1-4525-9333-661d2f2e859f\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum rake base 60\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32630\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"e244747f-f540-41bd-8458-2ed7de784602\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum strike base 60\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"maxValue\",\"txtvalue\":\"180.0\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"label\",\"txtvalue\":\"Feature layer to load\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32628\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"csv\"},{\"srcid\":\"e244747f-f540-41bd-8458-2ed7de784602\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"45640ff9-1587-49e4-ba2e-1b655c5f55aa\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum number of features to retrieve\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"-12.3925075531006\"},{\"srcid\":\"45640ff9-1587-49e4-ba2e-1b655c5f55aa\",\"labelclass\":\"variable\",\"txtvalue\":\"count\"},{\"srcid\":\"9dd5be40-7b87-4d52-be9f-cd4fcd1686e9\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"e244747f-f540-41bd-8458-2ed7de784602\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dstrikemax\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32636\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"maxValue\",\"txtvalue\":\"90.0\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"minValue\",\"txtvalue\":\"-90.0\"},{\"srcid\":\"13f50436-5534-4aa4-bbfa-0564afed49e6\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"WFS\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"variable\",\"txtvalue\":\"maxlongitude\"},{\"srcid\":\"9dd5be40-7b87-4d52-be9f-cd4fcd1686e9\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dstrikemin\"},{\"srcid\":\"5c98b0ff-2daa-4cda-8e68-1c52cf10e2b6\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum rake base 60\"},{\"srcid\":\"54d928dd-4305-42b5-a420-12fdd3532518\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum slip rate (mm/yr)\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32629\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"text/xml; subtype\\u003dgml/2.1.2\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"49.0013656616211\"},{\"srcid\":\"16a1cdde-b2f9-4539-bf2a-80c46164c6a8\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003ddipmin\"},{\"srcid\":\"16a1cdde-b2f9-4539-bf2a-80c46164c6a8\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum dip base 60\"},{\"srcid\":\"76482144-7c4b-4d59-b4b4-1c0361b45dba\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32632\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"paramValue\",\"txtvalue\":\"2.0.0\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"EDSF:crustal_fault_sources_top\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"30.303092956543\"},{\"srcid\":\"5c98b0ff-2daa-4cda-8e68-1c52cf10e2b6\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32635\"},{\"srcid\":\"6f04a0d0-03b1-43a5-af5b-d6a9ad18ca97\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003ddipmax\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"EPSG:4326\"},{\"srcid\":\"9dd5be40-7b87-4d52-be9f-cd4fcd1686e9\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum strike base 60\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum Longitude\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"paramValue\",\"txtvalue\":\"1.1.0\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"variable\",\"txtvalue\":\"typeNames\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"minValue\",\"txtvalue\":\"-180.0\"},{\"srcid\":\"5b7eb85d-1d1d-4a62-b3e9-656b76b7f995\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dmindepth\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"property\",\"txtvalue\":\"epos:westernmostLongitude\"},{\"srcid\":\"c177f0f0-d3c2-4a58-af70-cbd5ca36da62\",\"labelclass\":\"xsd\",\"txtvalue\":\"decimal\"},{\"srcid\":\"5e89e86d-c9d0-40d2-8fce-05965e9bba82\",\"labelclass\":\"template\",\"txtvalue\":\"http://services.seismofaults.eu/geoserver/EDSF/ows{?service, version, request, typeNames, outputFormat, count, srsName}\\u0026bbox\\u003d{minlatitude, minlongitude, maxlatitude, maxlongitude}\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32637\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32638\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"variable\",\"txtvalue\":\"outputFormat\"},{\"srcid\":\"16a1cdde-b2f9-4539-bf2a-80c46164c6a8\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"76482144-7c4b-4d59-b4b4-1c0361b45dba\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"GetFeature\"},{\"srcid\":\"13f50436-5534-4aa4-bbfa-0564afed49e6\",\"labelclass\":\"label\",\"txtvalue\":\"Service\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"label\",\"txtvalue\":\"SRS to use\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32639\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"2.0.0\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EDSF:crustal_fault_sources_top\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum Longitude\"},{\"srcid\":\"db89e1fe-13f1-4525-9333-661d2f2e859f\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"76482144-7c4b-4d59-b4b4-1c0361b45dba\",\"labelclass\":\"variable\",\"txtvalue\":\"request\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"text/xml; subtype\\u003dgml/2.1.2\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"property\",\"txtvalue\":\"epos:easternmostLongitude\"},{\"srcid\":\"13f50436-5534-4aa4-bbfa-0564afed49e6\",\"labelclass\":\"paramValue\",\"txtvalue\":\"WFS\"},{\"srcid\":\"db89e1fe-13f1-4525-9333-661d2f2e859f\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003drakemax\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32634\"},{\"srcid\":\"0bd901e8-4476-4bd2-9f13-040673090bac\",\"labelclass\":\"xsd\",\"txtvalue\":\"decimal\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32640\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"application/json\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum Latitude\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"gml3\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"label\",\"txtvalue\":\"Output format\"},{\"srcid\":\"6f04a0d0-03b1-43a5-af5b-d6a9ad18ca97\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum dip base 60\"},{\"srcid\":\"615ddfa0-82c1-445f-8717-4b3e558c9438\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum fault depth\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"defaultValue\",\"txtvalue\":\"45.2800407409668\"},{\"srcid\":\"615ddfa0-82c1-445f-8717-4b3e558c9438\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dmaxdepth\"},{\"srcid\":\"13f50436-5534-4aa4-bbfa-0564afed49e6\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"c177f0f0-d3c2-4a58-af70-cbd5ca36da62\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum slip rate (mm/yr)\"},{\"srcid\":\"5c98b0ff-2daa-4cda-8e68-1c52cf10e2b6\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003drakemin\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"application/kml\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EDSF:crustal_fault_sources_pln\"},{\"srcid\":\"dc7bb0e9-0664-4817-b185-fada5893fba6\",\"labelclass\":\"maxValue\",\"txtvalue\":\"180.0\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"5b7eb85d-1d1d-4a62-b3e9-656b76b7f995\",\"labelclass\":\"xsd\",\"txtvalue\":\"decimal\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"variable\",\"txtvalue\":\"minlongitude\"},{\"srcid\":\"76482144-7c4b-4d59-b4b4-1c0361b45dba\",\"labelclass\":\"paramValue\",\"txtvalue\":\"GetFeature\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"label\",\"txtvalue\":\"Service version\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"maxValue\",\"txtvalue\":\"90.0\"},{\"srcid\":\"c177f0f0-d3c2-4a58-af70-cbd5ca36da62\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dsrmax\"},{\"srcid\":\"76482144-7c4b-4d59-b4b4-1c0361b45dba\",\"labelclass\":\"label\",\"txtvalue\":\"Request type\"},{\"srcid\":\"0bd901e8-4476-4bd2-9f13-040673090bac\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dmaxmag\"},{\"srcid\":\"5b7eb85d-1d1d-4a62-b3e9-656b76b7f995\",\"labelclass\":\"label\",\"txtvalue\":\"Minimum fault depth\"},{\"srcid\":\"615ddfa0-82c1-445f-8717-4b3e558c9438\",\"labelclass\":\"xsd\",\"txtvalue\":\"decimal\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"variable\",\"txtvalue\":\"minlatitude\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32631\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"variable\",\"txtvalue\":\"srsName\"},{\"srcid\":\"6f04a0d0-03b1-43a5-af5b-d6a9ad18ca97\",\"labelclass\":\"xsd\",\"txtvalue\":\"integer\"},{\"srcid\":\"45640ff9-1587-49e4-ba2e-1b655c5f55aa\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"023cf989-97b6-4a74-8fe2-abce34e06345\",\"labelclass\":\"minValue\",\"txtvalue\":\"-180.0\"},{\"srcid\":\"0bd901e8-4476-4bd2-9f13-040673090bac\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum magnitude\"},{\"srcid\":\"40aada7f-0392-4e0b-abfa-6d760eab18b2\",\"labelclass\":\"paramValue\",\"txtvalue\":\"SHAPE-ZIP\"},{\"srcid\":\"13f50436-5534-4aa4-bbfa-0564afed49e6\",\"labelclass\":\"variable\",\"txtvalue\":\"service\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:32633\"},{\"srcid\":\"54d928dd-4305-42b5-a420-12fdd3532518\",\"labelclass\":\"xsd\",\"txtvalue\":\"decimal\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"variable\",\"txtvalue\":\"version\"},{\"srcid\":\"1a075506-e5da-4c0b-94cf-5e938a14f94c\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"variable\",\"txtvalue\":\"maxlatitude\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"label\",\"txtvalue\":\"Maximum Latitude\"},{\"srcid\":\"54d928dd-4305-42b5-a420-12fdd3532518\",\"labelclass\":\"variable\",\"txtvalue\":\"CQL_FILTER\\u003dsrmin\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:4326\"},{\"srcid\":\"b8978773-fd11-4bf3-afdc-19ff2bc8b3d3\",\"labelclass\":\"property\",\"txtvalue\":\"epos:southernmostLatitude\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"minValue\",\"txtvalue\":\"-90.0\"},{\"srcid\":\"26691630-0279-400c-b67a-475133963fb4\",\"labelclass\":\"paramValue\",\"txtvalue\":\"EPSG:5837\"},{\"srcid\":\"4eec4352-7869-4cda-a272-28158c38f6e3\",\"labelclass\":\"property\",\"txtvalue\":\"epos:northernmostLatitude\"},{\"srcid\":\"5c2ed132-5401-4448-a5a6-6bca6bc6ed0f\",\"labelclass\":\"xsd\",\"txtvalue\":\"string\"}]}"));		
		//System.out.println(CerifPluginInvoker.doTest("{\"ResultSet_ddss\":[{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-026\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-025\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-024\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-051\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-038\"},{\"productid\":\"7399decd-d0e0-44b7-83ed-25ba7dbdf470\",\"labelclass\":\"temporal\",\"startdate\":\"2017-01-01 00:00:00\",\"enddate\":\"2017-01-01 00:00:00\"},{\"productid\":\"26a100d0-d8f7-48a4-871b-64c1dfb5f868\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"24849f4d-33fb-4568-86b9-c425dc99b807\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"30305b44-728e-4f5b-b6c2-d953d3db3213\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"efbaefbf-19a3-4611-bf83-75cc61a675e7\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"labelclass\":\"temporal\",\"startdate\":\"2013-05-31 00:00:00\",\"enddate\":\"2013-05-31 00:00:00\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"2ca3eb58-74b4-4226-8a57-7f06dba8c50a\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"2ca3eb58-74b4-4226-8a57-7f06dba8c50a\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"2ca3eb58-74b4-4226-8a57-7f06dba8c50a\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (OGC WFS)\",\"lang\":\"en\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"2ca3eb58-74b4-4226-8a57-7f06dba8c50a\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/SHEEC/distribution/OGC/WFS\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"2ca3eb58-74b4-4226-8a57-7f06dba8c50a\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/OGC/WFS/events/Operation\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (FDSN-event)\",\"lang\":\"en\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/distribution/restful\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/fdsnws-event/Operation\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (FDSN-event)\",\"lang\":\"en\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/distribution/restful\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/fdsnws-event/Operation\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"f8003e60-41fb-4aad-b884-238b772fb43f\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm\"},{\"productid\":\"04f1e264-4dc3-417f-982c-dfd7fcab3401\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm\"},{\"productid\":\"e54eb203-651d-43db-9cfc-e35ab34179a5\",\"distributionid\":\"463da282-3d7f-4ee7-b98b-38d2bb1ee474\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD data sources distribution\",\"lang\":\"en\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/bibliography/distribution/restful\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/bibliography/Operation\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY\"},{\"productid\":\"a421afce-12d3-4d51-9d0b-49cc1116d52d\",\"distributionid\":\"0284862b-e868-4e73-884b-d217cff4cd46\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1900\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"29779c1d-b2ef-45e1-81a7-62dc956a7b3e\",\"labelscheme\":\"File Types\",\"labelclass\":\"PNG\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"29779c1d-b2ef-45e1-81a7-62dc956a7b3e\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"29779c1d-b2ef-45e1-81a7-62dc956a7b3e\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (OGC WMS)\",\"lang\":\"en\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"29779c1d-b2ef-45e1-81a7-62dc956a7b3e\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/SHEEC/distribution/OGC/WMS\"},{\"productid\":\"4a43f01a-13a7-43fd-a45d-ed937d9e5359\",\"distributionid\":\"29779c1d-b2ef-45e1-81a7-62dc956a7b3e\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/OGC/WMS/events/Operation\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"fcd1dc9d-34c3-4f9a-8132-c56b474e4c7d\",\"labelscheme\":\"File Types\",\"labelclass\":\"PNG\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"fcd1dc9d-34c3-4f9a-8132-c56b474e4c7d\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"fcd1dc9d-34c3-4f9a-8132-c56b474e4c7d\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"European Database of Seismogenic Faults\",\"lang\":\"en\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"fcd1dc9d-34c3-4f9a-8132-c56b474e4c7d\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"Seismology/EDSF/Distribution/WMS\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"fcd1dc9d-34c3-4f9a-8132-c56b474e4c7d\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"file:///Seismology/EDSF/Operation/WMS\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"a5cf0d14-e6db-4cf1-b489-fcca2eb8da50\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"a5cf0d14-e6db-4cf1-b489-fcca2eb8da50\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"a5cf0d14-e6db-4cf1-b489-fcca2eb8da50\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"European Database of Seismogenic Faults - WFS - Subductions\",\"lang\":\"en\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"a5cf0d14-e6db-4cf1-b489-fcca2eb8da50\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"Seismology/EDSF/Distribution/WFS_Subductions\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"a5cf0d14-e6db-4cf1-b489-fcca2eb8da50\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"file:///Seismology/EDSF/WFS-Subductions\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"European Database of Seismogenic Faults - WFS - Crustal Faults\",\"lang\":\"en\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"Seismology/EDSF/Distribution/WFS_CrustalFaults\"},{\"productid\":\"9406b95f-53aa-4de6-b92a-11914f69c889\",\"distributionid\":\"09c2effd-8768-4083-9055-db9c5effb021\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"file:///Seismology/EDSF/WFS-CrustalFaults\"}]}"));
		System.out.println(CerifPluginInvoker.doTest(""));
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
		HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
		ArrayList<Webservice> wsl = new ArrayList<Webservice>();
		Distribution obj = new Distribution();
		boolean distributionCreated = false;
		String format = "";

		JsonArray results = new JsonArray();

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
				case "webservices":
					JSONWebserviceDetail.generate(result, wsl, i, parametersMap);
					break;
				case "distribution":
					distributionCreated = true;
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equalsIgnoreCase("DEFAULT")) {
						format = "DEFAULT";
						JSONDistributionDefault.generate(result, obj, i);
					}
					else {
						JSONDistribution.generate(result, obj, i);
						format = "DETAILS";
					}
					System.out.println("DISTRIBUTION GENERATED");
					break;
				case "parameters":
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equalsIgnoreCase("DEFAULT")) {
						format = "DEFAULT";
						JSONDistributionDefault.generate(result, parametersMap, i, obj);
					}
					else {
						JSONDistribution.generate(result, parametersMap, i, obj);
						format = "DETAILS";
					}

					System.out.println("PARAMETERS GENERATED");
					break;
				case "ddss":
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
					//System.out.println(dist.getProductid().toString());
					if(ds.getProductid().contains(dist.getProductid())) {
						ds.getDistributions().add(dist);
					}
				});
			});

			ddssList.forEach(ds -> {
				//System.out.println(ds.toString());
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
						if(distr.getPattern()!=null) {
							if(distr.getStartDate()!=null) {
								distr.setStartDate(Utils.convert(distr.getStartDate(),distr.getPattern(),Utils.EPOS_internal_format));
							}
							if(distr.getEndDate()!=null) {
								distr.setEndDate(Utils.convert(distr.getEndDate(),distr.getPattern(),Utils.EPOS_internal_format));
							}
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				});

			});
			resultJson.getAsJsonObject().add("ddss", jsonParser.parse(gson.toJson(ddssList)).getAsJsonArray());
		}
		if(format.equals("DEFAULT")) {
			ArrayList<String> keysToRemove = new ArrayList<>();
			parametersMap.keySet().forEach(pm -> {
				if(parametersMap.get(pm).getDefaultValue()==null) 
					keysToRemove.add(pm);
			});
			keysToRemove.forEach(k -> parametersMap.remove(k));
			//System.out.println("distributionid: "+obj.getId());
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

		}
		if(distributionCreated) {
			//System.out.println(obj.toString());
			if(!parametersMap.isEmpty()) {
				//System.out.println(parametersMap.toString());
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
		return Optional.of(resultJson.toString());
	}

	protected static Optional<String> doTest(String payload) {
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
		HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
		ArrayList<Webservice> wsl = new ArrayList<Webservice>();
		Distribution obj = new Distribution();
		boolean distributionCreated = false;
		String format = "";

		JsonArray results = new JsonArray();

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
				case "webservices":
					JSONWebserviceDetail.generate(result, wsl, i, parametersMap);
					break;
				case "distribution":
					distributionCreated = true;
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equalsIgnoreCase("DEFAULT")) {
						format = "DEFAULT";
						JSONDistributionDefault.generate(result, obj, i);
					}
					else {
						JSONDistribution.generate(result, obj, i);
						format = "DETAILS";
					}
					//System.out.println("DISTRIBUTION GENERATED");
					break;
				case "parameters":
					if(entries.getKey().replaceAll("ResultSet_", "").split("_")[1].equalsIgnoreCase("DEFAULT")) {
						format = "DEFAULT";
						JSONDistributionDefault.generate(result, parametersMap, i, obj);
					}
					else {
						JSONDistribution.generate(result, parametersMap, i, obj);
						format = "DETAILS";
					}

					//System.out.println("PARAMETERS GENERATED");
					break;
				case "ddss":
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
					//System.out.println(dist.getProductid().toString());
					if(ds.getProductid().contains(dist.getProductid())) {
						ds.getDistributions().add(dist);
					}
				});
			});

			ddssList.forEach(ds -> {
				//System.out.println(ds.toString());
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
						if(distr.getPattern()!=null) {
							if(distr.getStartDate()!=null) {
								distr.setStartDate(Utils.convert(distr.getStartDate(),distr.getPattern(),Utils.EPOS_internal_format));
							}
							if(distr.getEndDate()!=null) {
								distr.setEndDate(Utils.convert(distr.getEndDate(),distr.getPattern(),Utils.EPOS_internal_format));
							}
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}


				});

			});
			resultJson.getAsJsonObject().add("ddss", jsonParser.parse(gson.toJson(ddssList)).getAsJsonArray());
		}
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
		return Optional.of(resultJson.toString());
	}
}
