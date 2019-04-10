package org.epos_ip.beans;

import java.util.ArrayList;

public class DDSS {
	private transient ArrayList<String> productid;
	private String id;
	private String ddss;
	private String title;
	private String type;
	private String startDate;
	private String endDate;
	private ArrayList<Distribution> distributions;
	
	public DDSS(){
		distributions = new ArrayList<>();
		productid = new ArrayList<>();
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

	public ArrayList<Distribution> getDistributions() {
		return distributions;
	}

	public void setDistributions(ArrayList<Distribution> distributions) {
		this.distributions = distributions;
	}

	public ArrayList<String> getProductid() {
		return productid;
	}

	public void setProductid(ArrayList<String> productid) {
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
	
	
	
}
