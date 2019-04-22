package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.text.ParseException;
import java.util.ArrayList;
import org.epos_ip.beans.DDSS;
import org.epos_ip.beans.Distribution;
import org.epos_ip.utility.Utils;

import com.google.gson.JsonArray;


public class JSONDDSS {

	/*public static void main(String[] args) {
		String payload = "{\"ResultSet_ddss\":[{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-001\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-002\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-026\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-025\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-024\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-051\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"labelscheme\":\"Identifier Types\",\"labelclass\":\"DDSS-ID\",\"txtvalue\":\"WP08-DDSS-034\"},{\"productid\":\"ef0e288c-a519-4b52-9823-40732682e98e\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"cc1302c5-8eba-4011-a90a-a2407ea9b699\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"6d9003c0-cff2-413f-879b-8841a92776b9\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"71ce71b5-e6d0-4ff9-ae94-b95af6098391\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"labelclass\":\"temporal\",\"startdate\":\"0999-12-27 00:00:00\",\"enddate\":\"0999-12-27 00:00:00\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"labelclass\":\"temporal\",\"startdate\":\"2010-04-01 00:00:00\",\"enddate\":\"2010-04-01 00:00:00\"},{\"productid\":\"8095aed3-a287-4764-8e3b-1089bf46927a\",\"labelclass\":\"temporal\",\"startdate\":\"1988-01-01 00:00:00\",\"enddate\":\"1988-01-01 00:00:00\"},{\"productid\":\"64b6ed00-6b9b-42f0-80ff-64c1e78970c6\",\"labelclass\":\"temporal\",\"startdate\":\"1990-01-01 00:00:00\",\"enddate\":\"1990-01-01 00:00:00\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"labelclass\":\"temporal\",\"startdate\":\"1988-01-01 00:00:00\",\"enddate\":\"1988-01-01 00:00:00\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"labelclass\":\"temporal\",\"startdate\":\"1990-01-01 00:00:00\",\"enddate\":\"1990-01-01 00:00:00\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"c8c3d9f5-192c-4f9a-b9c0-23244fcb332a\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"c8c3d9f5-192c-4f9a-b9c0-23244fcb332a\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"c8c3d9f5-192c-4f9a-b9c0-23244fcb332a\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (WFS)\",\"lang\":\"en\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"c8c3d9f5-192c-4f9a-b9c0-23244fcb332a\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/SHEEC/distribution/OGC/WFS\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"c8c3d9f5-192c-4f9a-b9c0-23244fcb332a\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/OGC/WFS/events/Operation\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (FDSN-event)\",\"lang\":\"en\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/distribution/restful\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/fdsnws-event/Operation\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (FDSN-event)\",\"lang\":\"en\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/distribution/restful\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/fdsnws-event/Operation\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"3bb2b5a4-5888-41b0-8b54-41d9525ed74d\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"500d8818-c95b-4220-ad32-fd6ad3a4fd29\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD macroseismic data distribution\",\"lang\":\"en\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/macroseismic/distribution/restful\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/macroseismic/Operation\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"1000-01-01T00:00:00\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"e72c9441-b0b0-4bb1-a5b8-38d4579dbd1c\",\"distributionid\":\"77fc1805-7d23-43b8-bed0-c362c9c3524f\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"1899-12-31T23:59:59\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"distributionid\":\"16670a25-0ba7-49fc-911b-633476e274f3\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"distributionid\":\"16670a25-0ba7-49fc-911b-633476e274f3\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"distributionid\":\"16670a25-0ba7-49fc-911b-633476e274f3\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD data sources distribution\",\"lang\":\"en\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"distributionid\":\"16670a25-0ba7-49fc-911b-633476e274f3\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/bibliography/distribution/restful\"},{\"productid\":\"841e7e28-da34-46cf-894b-10973dd425f1\",\"distributionid\":\"16670a25-0ba7-49fc-911b-633476e274f3\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/restful/bibliography/Operation\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"cee10220-3cc4-4a10-8d9c-7c7e7f9a4375\",\"labelscheme\":\"File Types\",\"labelclass\":\"PNG\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"cee10220-3cc4-4a10-8d9c-7c7e7f9a4375\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"cee10220-3cc4-4a10-8d9c-7c7e7f9a4375\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"AHEAD event data distribution (WMS)\",\"lang\":\"en\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"cee10220-3cc4-4a10-8d9c-7c7e7f9a4375\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/AHEAD/events/SHEEC/distribution/OGC/WMS\"},{\"productid\":\"57a686d3-aec8-4cdd-85c4-272b6fe54ea8\",\"distributionid\":\"cee10220-3cc4-4a10-8d9c-7c7e7f9a4375\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/AHEAD/OGC/WMS/events/Operation\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"INGV FDSN Event Distribution\",\"lang\":\"en\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/034/INGV/Distribution/001\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/INGV/Event/WebService/001/Operation/001\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"schema\",\"labelclass\":\"minValue\",\"txtvalue\":\"2010-04-01T00:00:00\"},{\"productid\":\"389d4a4e-bb5d-4203-8432-37d58a00cf08\",\"distributionid\":\"0c0b7583-c938-4d6b-bfbb-302920c2e169\",\"labelscheme\":\"schema\",\"labelclass\":\"maxValue\",\"txtvalue\":\"2018-12-31T00:00:00\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"distributionid\":\"f5f6110f-ec60-4d28-88e8-63ac92dbc44b\",\"labelscheme\":\"File Types\",\"labelclass\":\"XML\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"distributionid\":\"f5f6110f-ec60-4d28-88e8-63ac92dbc44b\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"distributionid\":\"f5f6110f-ec60-4d28-88e8-63ac92dbc44b\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/002/Distribution/002/INGV\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"distributionid\":\"f5f6110f-ec60-4d28-88e8-63ac92dbc44b\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/002/Operation/002/INGV\"},{\"productid\":\"0a32f0e4-7086-4e03-9de6-05a32c7abc47\",\"distributionid\":\"f5f6110f-ec60-4d28-88e8-63ac92dbc44b\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"File Types\",\"labelclass\":\"BIN\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"Distribution Types\",\"labelclass\":\"WEB_SERVICE\",\"lang\":\"en\",\"startdate\":\"1901-01-01 00:00:00\",\"enddate\":\"2099-12-31 23:59:59\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"dct\",\"labelclass\":\"title\",\"txtvalue\":\"Seismic Waveform Distribution :: INGV\",\"lang\":\"en\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"dct\",\"labelclass\":\"identifier\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/Dataset/001/Distribution/001/INGV\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"dcat\",\"labelclass\":\"accessURL\",\"txtvalue\":\"https://www.epos-eu.org/epos-dcat-ap/Seismology/WebService/001/Operation/001/INGV\"},{\"productid\":\"a162ee08-0c08-4217-ba9d-17a209a2476d\",\"distributionid\":\"7b76d845-ffe1-4657-8e66-c566a0303b07\",\"labelscheme\":\"schema\",\"labelclass\":\"valuePattern\",\"txtvalue\":\"YYYY-MM-DDThh:mm:ss\"}]}";
		ArrayList<DDSS> ddssList = new ArrayList<DDSS>();
		ArrayList<Distribution> distributionList = new ArrayList<Distribution>();
		Gson gson = new Gson();
		JsonObject result = gson.fromJson(payload, JsonObject.class);
		JsonArray ja = result.getAsJsonArray("ResultSet_ddss");

		for(int i = 0; i<ja.size(); i++)
		{
			JSONDDSS.generate(ddssList, distributionList, ja, i);
		}

		ddssList.forEach(e -> System.out.println(e));
	}*/

	public static void generate(ArrayList<DDSS> ddssList, ArrayList<Distribution> distributionList, JsonArray result, int i)
	{
		boolean exists = false;
		boolean existsDist = false;
		DDSS ddss = null;
		Distribution distribution = null;

		//System.out.println(result.get(i).toString());
		
		
		
		/*if(!result.get(i).getAsJsonObject().has("distributionid")) {
			for(DDSS item : ddssList) {
				if(result.get(i).getAsJsonObject().has("txtvalue")) {
					if(item.getDdss().equals(result.get(i).getAsJsonObject().get("txtvalue").getAsString()) && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("DDSS-ID")) {
						exists = true;
						ddss = item;
					}
				}else if(item.getProductid().get(0).equals(result.get(i).getAsJsonObject().get("productid").getAsString()) && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("temporal")){
					exists = true;
					ddss = item;
				}
			}

			System.out.println(exists);
			if(exists) {
				setInformationDDSS(ddss, result, i);
			} else {
				ddss = new DDSS();
				setInformationDDSS(ddss, result, i);
				ddssList.add(ddss);
			}
		}*/
		if(!result.get(i).getAsJsonObject().has("distributionid")) {
			if(result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("DDSS-ID")) {
				for(DDSS item : ddssList) {
					if(item.getDdss().equals(result.get(i).getAsJsonObject().get("txtvalue").getAsString()))  {
						exists = true;
						ddss = item;
					}
				}
				if(exists) {
					setInformationDDSS(ddss, result, i);
				} else {
					ddss = new DDSS();
					setInformationDDSS(ddss, result, i);
					ddssList.add(ddss);
				}
			}else if(result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("temporal")) {
				for(DDSS item : ddssList) {
					if(item.getProductid().get(0).equals(result.get(i).getAsJsonObject().get("productid").getAsString())) 
						setInformationDDSS(item, result, i);
				}
			}else if(result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("spatial")) {
				for(DDSS item : ddssList) {
					if(item.getProductid().get(0).equals(result.get(i).getAsJsonObject().get("productid").getAsString())) 
						setInformationDDSS(item, result, i);
				}
			}
			
		}
		else {
			for(Distribution distributionItem : distributionList) {
				if(distributionItem.getDistributionid().equals(result.get(i).getAsJsonObject().get("distributionid").getAsString()) && distributionItem.getProductid().equals(result.get(i).getAsJsonObject().get("productid").getAsString())) {
					existsDist = true;
					distribution = distributionItem;
				}
			}
			if(existsDist) {
				setInformationDistribution(distribution,result,i);
			}
			else {
				distribution = new Distribution();
				setInformationDistribution(distribution, result, i);
				distributionList.add(distribution);
			}
		}
	}

	private static void setInformationDDSS(DDSS ddss, JsonArray result, int i) {
		
		System.out.println(result.get(i).toString());
		if(result.get(i).getAsJsonObject().has("productid")) ddss.addProductid(result.get(i).getAsJsonObject().get("productid").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("title")) ddss.setTitle(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("identifier") && !result.get(i).getAsJsonObject().has("distributionid")) 
			ddss.setId(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("DDSS-ID")) ddss.setDdss(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("temporal")) {
			String startDate = null;
			String endDate = null;
			try {
				startDate = Utils.convert(result.get(i).getAsJsonObject().get("startdate").getAsString().replace(" ", "T"), "YYYY-MM-DDThh:mm:ss", Utils.EPOS_internal_format);
				endDate = Utils.convert(result.get(i).getAsJsonObject().get("enddate").getAsString().replace(" ", "T"), "YYYY-MM-DDThh:mm:ss", Utils.EPOS_internal_format);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//result.get(i).getAsJsonObject().get("startdate").getAsString().replace(" ", "T");
			//String endDate = result.get(i).getAsJsonObject().get("enddate").getAsString().replace(" ", "T");
			if(startDate.equals(endDate)) {
				ddss.setStartDate(startDate);
			}else{
				ddss.setStartDate(startDate);
				ddss.setEndDate(endDate);
			}
			/*String startDate = result.get(i).getAsJsonObject().get("startdate").getAsString().replace(" ", "T");
			String endDate = result.get(i).getAsJsonObject().get("enddate").getAsString().replace(" ", "T");
			if(startDate.equals(endDate)) {
				ddss.setStartDate(startDate);
			}else{
				ddss.setStartDate(startDate);
				ddss.setEndDate(endDate);
			}*/
		}
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("spatial") && result.get(i).getAsJsonObject().get("txtvalue")!=null) {
			System.out.println("HERE");
			ddss.setSpatial(SpatialInformation.doSpatial(result.get(i).getAsJsonObject().get("txtvalue").getAsString()));
		}
	}

	private static void setInformationDistribution(Distribution distribution, JsonArray result, int i) {
		//System.out.println("PRODUCTID: "+result.get(i).getAsJsonObject().get("productid").getAsString());
		if(result.get(i).getAsJsonObject().has("productid")) distribution.setProductid(result.get(i).getAsJsonObject().get("productid").getAsString());
		if(result.get(i).getAsJsonObject().has("distributionid")) distribution.setDistributionid(result.get(i).getAsJsonObject().get("distributionid").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("title")) distribution.setTitle(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("identifier")) distribution.setId(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelscheme").getAsString().equals("Distribution Types")) distribution.setType(result.get(i).getAsJsonObject().get("labelclass").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("accessURL")) distribution.setDefaultURL(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("downloadURL")) distribution.setDefaultURL(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelscheme").getAsString().equals("File Types")) distribution.setDefaultFormat(result.get(i).getAsJsonObject().get("labelclass").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("minValue")) distribution.setStartDate(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("maxValue")) distribution.setEndDate(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelscheme") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("valuePattern")) distribution.setPattern(result.get(i).getAsJsonObject().get("txtvalue").getAsString());
		if(result.get(i).getAsJsonObject().has("labelclass") && result.get(i).getAsJsonObject().get("labelclass").getAsString().equals("spatial")&& result.get(i).getAsJsonObject().get("txtvalue")!=null) {
			System.out.println("HERE");
			distribution.setSpatial(SpatialInformation.doSpatial(result.get(i).getAsJsonObject().get("txtvalue").getAsString()));
		}
	}
}
