package org.epos_ip.beans;

import java.util.List;

public class Distribution {
	
	private transient String productid;
	private transient String distributionid;
	private String id;
	private String title;
	private String originalFormat;
	private String endpoint;
	private String defaultFormat;
	private String defaultURL;
	private String downloadURL;
	private String type;
	private String startDate;
	private String endDate;
	private transient String pattern;
	
	private List<ServiceParameter> parameters;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDistributionid() {
		return distributionid;
	}
	public void setDistributionid(String distributionid) {
		this.distributionid = distributionid;
	}

	public String getDefaultURL() {
		return defaultURL;
	}
	public void setDefaultURL(String defaultURL) {
		this.defaultURL = defaultURL;
	}
	public String getDefaultFormat() {
		return defaultFormat;
	}
	public void setDefaultFormat(String defaultFormat) {
		this.defaultFormat = defaultFormat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
	
	public void setParameter(List<ServiceParameter> parameter) {
		this.parameters = parameter;
	}

	public List<ServiceParameter> getParameter() {
		return parameters;
	}
	public String getOriginalFormat() {
		return originalFormat;
	}
	public void setOriginalFormat(String format) {
		this.originalFormat = format;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String originalURL) {
		this.endpoint = originalURL;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
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
	/**
	 * @return the parameters
	 */
	public List<ServiceParameter> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<ServiceParameter> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern.replace("T", "'T'").replace("Z", "'Z'");
	}
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Distribution [productid=" + productid + ", distributionid=" + distributionid + ", id=" + id + ", title="
				+ title + ", originalFormat=" + originalFormat + ", endpoint=" + endpoint + ", defaultFormat="
				+ defaultFormat + ", defaultURL=" + defaultURL + ", downloadURL=" + downloadURL + ", type=" + type
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", parameters=" + parameters + "]";
	}

	
}
