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

import java.util.HashSet;
import java.util.Map;

import com.google.gson.JsonArray;

public class JSONFacetsOrganisations {
	
	private JSONFacetsOrganisations() {}
	
	public static void generate(Map<String, HashSet<String>> organisations, JsonArray result, int i)
	{
		String keyname = result.get(i).getAsJsonObject().get("organisation").getAsString();
		String ddssname = result.get(i).getAsJsonObject().get("ddss").getAsString();
		 
		if(organisations.containsKey(keyname)) {
			organisations.get(keyname).add(ddssname);
		}
		else {
			HashSet<String> hs = new HashSet<String>();
			hs.add(ddssname);
			organisations.put(keyname, hs);
		}

	}
}
