package org.epos_ip.beans;

public class SeismicEventBean {
	private String id;
	private String eventType;
	private String location;
	private String country;
	private String date;
	private double magnitude;
	private String mag_uncertainty;
	private String mag_type;
	private double deep;
	private double latitude;
	private double longitude;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	public String getMag_uncertainty() {
		return mag_uncertainty;
	}
	public void setMag_uncertainty(String mag_uncertainty) {
		this.mag_uncertainty = mag_uncertainty;
	}
	public String getMag_type() {
		return mag_type;
	}
	public void setMag_type(String mag_type) {
		this.mag_type = mag_type;
	}
	public double getDeep() {
		return deep;
	}
	public void setDeep(double deep) {
		this.deep = deep;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "SeismicEventBean [id=" + id + ", location=" + location + ", date=" + date + ", magnitude=" + magnitude
				+ ", deep=" + deep + ", latitude=" + latitude + ", longitude=" + longitude 
				+ "]";
	}

	
}
