package org.epos_ip.utility;
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


public class EnvironmentVariables {

	public static final String BROKER_HOST = System.getenv("BROKER_HOST");
	public static final String BROKER_VHOST = System.getenv("BROKER_VHOST");
	public static final String BROKER_USERNAME = System.getenv("BROKER_USERNAME");
	public static final String BROKER_PASSWORD = System.getenv("BROKER_PASSWORD");
	public static final String API_HOST = System.getenv("APIHOST");
	
	private EnvironmentVariables() {}

}
