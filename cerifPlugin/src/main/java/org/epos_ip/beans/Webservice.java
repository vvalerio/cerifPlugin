package org.epos_ip.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlAccessorType(XmlAccessType.FIELD)
public class Webservice {
	
	@XmlPath("dct:title/text()")
	private List<String> title;

	@XmlPath("dct:description/text()")
	private List<String> description;

	@XmlPath("dct:issued/text()")
	private XMLGregorianCalendar published;

	@XmlPath("dct:modified/text()")
	private XMLGregorianCalendar modified;

	@XmlPath("dct:license/text()")
	private List<String> license;

	@XmlPath("foaf:page/foaf:primaryTopic/text()")
	private String URI;

	// ignore accessurl

	@XmlPath("dct:format/dct:MediaTypeOrExtent/text()")
	private List<String> format;
	
	@XmlPath("dct:rights/dct:RightsStatement/text()")
	private String accesslimit;

	@XmlPath("dct:conformsTo/text()")
	private String spatialReferenceSystem;

	@XmlPath("dct:identifier/text()")
	private String identifier;

	@XmlPath("dct:created/text()")
	private XMLGregorianCalendar created;

	@XmlPath("domain/text()")
	private String domain;
	
	@XmlPath("subDomain/text()")
	private String subDomain;
	
	@XmlPath("dcat:keyword/text()")
	private List<String> keyword;

	@XmlPath("eposap:operation/text()")
	private List<String> operation;
	
	@XmlPath("dct:hasVersion/text()")
	private String version;

	@XmlPath("eposap:parameter")
	private List<ServiceParameter> parameter;

	@XmlPath("schema:documentation/text()")
	private String documentation;
	
	@XmlPath("dcat:contactPoint/text()")
	private List<String> contactpoint;

	@XmlPath("eposap:publisher/text()")
	private List<String> responsibleparty;

	@XmlPath("dct:spatial/dct:Location")
	private Location location;

	@XmlPath("adms:representationTechnique/text()")
	private String spatialrepresentation;

	@XmlPath("dct:temporal")
	private TimePeriod timeperiod;
	
	@XmlPath("eposap:DDSS-ID/text()")
	private String ddssid;

	@XmlPath("eposap:actions/text()")
	private String actions;

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public XMLGregorianCalendar getPublished() {
		return published;
	}

	public void setPublished(XMLGregorianCalendar published) {
		this.published = published;
	}

	public XMLGregorianCalendar getModified() {
		return modified;
	}

	public void setModified(XMLGregorianCalendar modified) {
		this.modified = modified;
	}

	public List<String> getLicense() {
		return license;
	}

	public void setLicense(List<String> license) {
		this.license = license;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public List<String> getFormat() {
		return format;
	}

	public void setFormat(List<String> format) {
		this.format = format;
	}

	public String getAccesslimit() {
		return accesslimit;
	}

	public void setAccesslimit(String accesslimit) {
		this.accesslimit = accesslimit;
	}

	public String getSpatialReferenceSystem() {
		return spatialReferenceSystem;
	}

	public void setSpatialReferenceSystem(String spatialReferenceSystem) {
		this.spatialReferenceSystem = spatialReferenceSystem;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public XMLGregorianCalendar getCreated() {
		return created;
	}

	public void setCreated(XMLGregorianCalendar created) {
		this.created = created;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<ServiceParameter> getParameter() {
		return parameter;
	}

	public List<String> getContactpoint() {
		return contactpoint;
	}

	public void setContactpoint(List<String> contactpoint) {
		this.contactpoint = contactpoint;
	}

	public List<String> getResponsibleparty() {
		return responsibleparty;
	}

	public void setResponsibleparty(List<String> responsibleparty) {
		this.responsibleparty = responsibleparty;
	}

	public Location getSpatialextent() {
		return location;
	}

	public void setSpatialextent(Location location) {
		this.location = location;
	}

	public String getSpatialrepresentation() {
		return spatialrepresentation;
	}

	public void setSpatialrepresentation(String spatialrepresentation) {
		this.spatialrepresentation = spatialrepresentation;
	}

	public TimePeriod getTemporalextent() {
		return timeperiod;
	}

	public void setTemporalextent(TimePeriod timeperiod) {
		this.timeperiod = timeperiod;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public List<String> getOperation() {
	    return operation;
	}

	public void setOperation(List<String> operation) {
	    this.operation = operation;
	}

	public String getVersion() {
	    return version;
	}

	public void setVersion(String version) {
	    this.version = version;
	}

	public String getDocumentation() {
	    return documentation;
	}

	public void setDocumentation(String documentation) {
	    this.documentation = documentation;
	}

	public Location getLocation() {
	    return location;
	}

	public void setLocation(Location location) {
	    this.location = location;
	}
	
	public String getDdssid() {
		return ddssid;
	}

	public void setDdssid(String ddssid) {
		this.ddssid = ddssid;
	}
	
	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public void setParameter(List<ServiceParameter> collect) {
			this.parameter = collect;
	}

}
