package org.epos_ip.utility;

public enum EposEntities {
    PERSON("person"),
    ORGANISATION("organisation"),
    WEBSERVICE("webservice");
   
    private String value;
    
    private EposEntities(String value)
    {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }
    
    
}
