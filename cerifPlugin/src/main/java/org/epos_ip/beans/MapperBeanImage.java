package org.epos_ip.beans;

import java.util.HashMap;

public class MapperBeanImage {
	
	private HashMap<Object, Object> properties;
	private double latitudeNorth;
	private double longitudeEast;
	private double latitudeSouth;
	private double longitudeWest;
	private String type;
	
	public HashMap<Object, Object> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<Object, Object> properties) {
		this.properties = properties;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLongitudeWest() {
		return longitudeWest;
	}
	public void setLongitudeWest(double longitudeWest) {
		this.longitudeWest = longitudeWest;
	}
	public double getLatitudeNorth() {
		return latitudeNorth;
	}
	public void setLatitudeNorth(double latitudeNorth) {
		this.latitudeNorth = latitudeNorth;
	}
	public double getLongitudeEast() {
		return longitudeEast;
	}
	public void setLongitudeEast(double longitudeEast) {
		this.longitudeEast = longitudeEast;
	}
	public double getLatitudeSouth() {
		return latitudeSouth;
	}
	public void setLatitudeSouth(double latitudeSouth) {
		this.latitudeSouth = latitudeSouth;
	}
	
	
	
	
}
