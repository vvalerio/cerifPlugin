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


import org.epos_ip.basicCerifConverterPlugin.interfaces.MapperInterface;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GenericMapper implements MapperInterface {
	private String mappedResult;

	public GenericMapper()
	{
		mappedResult = "";
	}

	@Override
	public JsonElement generate(JsonObject resultSet, String researchType, String format) {
		return resultSet;}

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
		return mappedResult;
	}



}
