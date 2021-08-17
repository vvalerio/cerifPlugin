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

public class DDSS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient List<String> productid;
	private String id;
	private String ddss;
	private String title;
	private String type;
	private String startDate;
	private String endDate;
	private List<Distribution> distributions;
	private SpatialInfo spatial;
	private String license;
	private String description;
	

	public DDSS(){
		distributions = new ArrayList<>();
		productid = new ArrayList<>();
		spatial = new SpatialInfo();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDdss() {
		return ddss;
	}

	public void setDdss(String ddss) {
		this.ddss = ddss;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Distribution> getDistributions() {
		return distributions;
	}

	public void setDistributions(List<Distribution> distributions) {
		this.distributions = distributions;
	}

	public List<String> getProductid() {
		return productid;
	}

	public void setProductid(List<String> productid) {
		this.productid = productid;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DDSS [productid=" + productid + ", id=" + id + ", ddss=" + ddss + ", title=" + title + ", type=" + type
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", distributions=" + distributions
				+ ", getId()=" + getId() + ", getDdss()=" + getDdss() + ", getTitle()=" + getTitle() + ", getType()="
				+ getType() + ", getStartDate()=" + getStartDate() + ", getEndDate()=" + getEndDate()
				+ ", getDistributions()=" + getDistributions() + ", getProductid()=" + getProductid() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public void addProductid(String asString) {
		if(!this.productid.contains(asString)) this.productid.add(asString);
	}

	public SpatialInfo getSpatial() {
		return spatial;
	}

	public void setSpatial(SpatialInfo spatial) {
		this.spatial = spatial;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
