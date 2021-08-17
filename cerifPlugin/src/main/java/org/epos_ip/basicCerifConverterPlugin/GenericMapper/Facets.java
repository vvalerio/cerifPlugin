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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Facets {

	private static File[] getResourceFolderFiles(String folder) {
		return new File(folder).listFiles();
	}

	private Facets() {}

	public static JsonObject getFacets() throws IOException {
		Gson gson = new Gson();
		JsonObject facetsObject = new JsonObject();
		JsonArray domainsFacets = new JsonArray();
		JsonObject json;

		for (File f : getResourceFolderFiles("facets")) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			json = gson.fromJson(bufferedReader, JsonArray.class).get(0).getAsJsonObject();
			domainsFacets.add(json);
		}


		facetsObject.addProperty("name", "domains");
		facetsObject.add("children", domainsFacets);

		return facetsObject;
	}
}
