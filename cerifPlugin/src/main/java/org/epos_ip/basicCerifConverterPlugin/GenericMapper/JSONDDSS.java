/*******************************************************************************
 * Copyright 2021 EPOS ERIC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;


public class JSONDDSS {

	private static final String DISTRIBUTION_ID  = "distributionid";
	private static final String PRODUCT_ID  = "productid";
	private static final String LABELCLASS  = "labelclass";
	private static final String DDSS_ID  = "DDSS-ID";
	private static final String TXTVALUE  = "txtvalue";
	private static final String LABELSCHEME  = "labelscheme";

	private JSONDDSS() {}

	public static void generate(ArrayList<DDSS> ddssList, ArrayList<Distribution> distributionList, JsonArray result, int i)
	{
		boolean exists = false;
		boolean existsDist = false;
		DDSS ddss = null;
		Distribution distribution = null;
		
		System.out.println(result.get(i));
		
		for(Distribution distributionItem : distributionList) {
			if(distributionItem.getDistributionid().equals(result.get(i).getAsJsonObject().get(DISTRIBUTION_ID).getAsString()) && distributionItem.getProductid().equals(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString())) {
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
		
		System.out.println(exists);
		System.out.println(distributionList);

		if(!result.get(i).getAsJsonObject().has(DISTRIBUTION_ID) || result.get(i).getAsJsonObject().get(DISTRIBUTION_ID).isJsonNull()) {
			for(DDSS item : ddssList) {
				if(item.getProductid().get(0).equals(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString()))  {
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
			if(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals(DDSS_ID)) {
				for(DDSS item : ddssList) {
					if(item.getProductid().get(0).equals(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString())) 
						setInformationDDSS(item, result, i);
				}
			}
		}
		else {
			for(Distribution distributionItem : distributionList) {
				if(distributionItem.getDistributionid().equals(result.get(i).getAsJsonObject().get(DISTRIBUTION_ID).getAsString()) && distributionItem.getProductid().equals(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString())) {
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

		if(result.get(i).getAsJsonObject().has(PRODUCT_ID)) ddss.addProductid(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("identifier") && !result.get(i).getAsJsonObject().has(DISTRIBUTION_ID)) 
			ddss.setId(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals(DDSS_ID)) ddss.setDdss(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
	}

	private static void setInformationDistribution(Distribution distribution, JsonArray result, int i) {
		if(result.get(i).getAsJsonObject().has(PRODUCT_ID)) distribution.setProductid(result.get(i).getAsJsonObject().get(PRODUCT_ID).getAsString());
		if(result.get(i).getAsJsonObject().has(DISTRIBUTION_ID)) distribution.setDistributionid(result.get(i).getAsJsonObject().get(DISTRIBUTION_ID).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("title")) distribution.setTitle(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("identifier")) distribution.setId(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELCLASS) && result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().equals("template")) distribution.setServiceEndpoint(result.get(i).getAsJsonObject().get(TXTVALUE).getAsString());
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("File Types")) {
			if(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString().toLowerCase().equals("png")) {
				distribution.getAvailableFormats().add(new AvailableFormat("WMS", "application/vnd.ogc.wms_xml","", "original"));
			}
			else distribution.getAvailableFormats().add(new AvailableFormat(result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), result.get(i).getAsJsonObject().get(LABELCLASS).getAsString(), "", "original"));
		}
		if(result.get(i).getAsJsonObject().has(LABELSCHEME) && !result.get(i).getAsJsonObject().get(LABELSCHEME).isJsonNull() && result.get(i).getAsJsonObject().get(LABELSCHEME).getAsString().equals("plugin")) {
			distribution.getAvailableFormats().add(new AvailableFormat("GEOJSON","application/epos.geo+json", "", "converted"));
		}
	}
}
