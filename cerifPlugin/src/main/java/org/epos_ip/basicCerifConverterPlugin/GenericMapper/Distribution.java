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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class Distribution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient String productid;
	private transient String distributionid;
	
	private String href;
	
	private String id;
	private String type;
	private String title;
	private String description;
	private String license;
	private String downloadURL;
	private List<String> keywords;
	private List<String> dataProvider;
	private String frequencyUpdate;
	private List<String> internalID;
	private String DOI;
	private SpatialInfo spatial;
	private TemporalCoverage temporalCoverage;
	private List<AvailableFormat> availableFormats;
	
	// WEBSERVICE
	
	private String serviceName;
	private String serviceDescription;
	private String serviceProvider;
	private SpatialInfo serviceSpatial;
	private TemporalCoverage serviceTemporalCoverage;
	private String serviceEndpoint;
	private String serviceDocumentation;
	private String endpoint;

	private List<ServiceParameter> serviceParameters;
	
	public Distribution() {
		keywords = new ArrayList<>();
		dataProvider = new ArrayList<>();
		internalID = new ArrayList<>();
		availableFormats = new ArrayList<>();
		serviceSpatial = new SpatialInfo();
		spatial = new SpatialInfo();
		temporalCoverage = new TemporalCoverage();
		serviceTemporalCoverage = new TemporalCoverage();
		
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getDistributionid() {
		return distributionid;
	}

	public void setDistributionid(String distributionid) {
		this.distributionid = distributionid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(List<String> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public String getFrequencyUpdate() {
		return frequencyUpdate;
	}

	public void setFrequencyUpdate(String frequencyUpdate) {
		this.frequencyUpdate = frequencyUpdate;
	}

	public List<String> getInternalID() {
		return internalID;
	}

	public void setInternalID(List<String> internalID) {
		this.internalID = internalID;
	}

	public String getDOI() {
		return DOI;
	}

	public void setDOI(String dOI) {
		DOI = dOI;
	}

	public SpatialInfo getSpatial() {
		return spatial;
	}

	public void setSpatial(SpatialInfo spatial) {
		this.spatial = spatial;
	}

	public TemporalCoverage getTemporalCoverage() {
		return temporalCoverage;
	}

	public void setTemporalCoverage(TemporalCoverage temporalCoverage) {
		this.temporalCoverage = temporalCoverage;
	}

	public List<AvailableFormat> getAvailableFormats() {
		return availableFormats;
	}

	public void setAvailableFormats(List<AvailableFormat> availableFormats) {
		this.availableFormats = availableFormats;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public SpatialInfo getServiceSpatial() {
		return serviceSpatial;
	}

	public void setServiceSpatial(SpatialInfo serviceSpatial) {
		this.serviceSpatial = serviceSpatial;
	}

	public TemporalCoverage getServiceTemporalCoverage() {
		return serviceTemporalCoverage;
	}

	public void setServiceTemporalCoverage(TemporalCoverage serviceTemporalCoverage) {
		this.serviceTemporalCoverage = serviceTemporalCoverage;
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	public String getServiceDocumentation() {
		return serviceDocumentation;
	}

	public void setServiceDocumentation(String serviceDocumentation) {
		this.serviceDocumentation = serviceDocumentation;
	}

	public List<ServiceParameter> getParameters() {
		return serviceParameters;
	}

	public void setParameters(List<ServiceParameter> parameters) {
		this.serviceParameters = parameters;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
