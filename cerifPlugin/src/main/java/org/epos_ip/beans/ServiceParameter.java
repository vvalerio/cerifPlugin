package org.epos_ip.beans;

import java.util.ArrayList;

public class ServiceParameter {

	private String name;
	private String label;
	private String type;
	private ArrayList<String> Enum;
	private String minValue;
	private String maxValue;
	private String version;
	private String defaultValue;
	private String property;
	private String valuePattern;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getValue() {
		return Enum;
	}

	public void setValue(String value) {
		if(this.Enum==null) this.Enum = new ArrayList<>();
		this.Enum.add(value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLabel() {
	    return label;
	}

	public void setLabel(String label) {
	    this.label = label;
	}

	public String getMinValue() {
	    return minValue;
	}

	public void setMinValue(String minValue) {
	    this.minValue = minValue;
	}

	public String getMaxValue() {
	    return maxValue;
	}

	public void setMaxValue(String maxValue) {
	    this.maxValue = maxValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	
	/**
	 * @return the enum
	 */
	public ArrayList<String> getEnum() {
		return Enum;
	}

	/**
	 * @param enum1 the enum to set
	 */
	public void setEnum(ArrayList<String> enum1) {
		Enum = enum1;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the valuePattern
	 */
	public String getValuePattern() {
		return valuePattern;
	}

	/**
	 * @param valuePattern the valuePattern to set
	 */
	public void setValuePattern(String valuePattern) {
		this.valuePattern = valuePattern;
	}

	@Override
	public String toString() {
	    return "ServiceParameter [name=" + name + ", label=" + label + ", type=" + type + ", value=" + Enum
		    + ", minValue=" + minValue + ", maxValue=" + maxValue + ", version=" + version + "]";
	}

	/*public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}*/
	
	public boolean isNull() {
		return name==null && label==null && type==null && Enum==null && minValue==null && maxValue==null && version==null;
	}
}
