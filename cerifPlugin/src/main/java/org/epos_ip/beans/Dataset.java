package org.epos_ip.beans;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dataset {
	
	@XmlPath("dct:identifier/text()")
	private String identifier;

	@XmlPath("dct:title/text()")
	private List<String> title;

	@XmlPath("dct:description/text()")
	private List<String> description;

	@XmlPath("dct:issued/text()")
	private XMLGregorianCalendar issued;

	@XmlPath("dct:modified/text()")
	private XMLGregorianCalendar modified;

	@XmlPath("dct:language/dct:LinguisticSystem/text()")
	private List<String> language;

	@XmlPath("dct:provenance/dct:ProvenaceStatement/text()")
	private List<String> provenance;

	@XmlPath("dct:type[1]/text()")
	private String resourcetype;

	@XmlPath("dcat:keyword/text()")
	private List<String> keyword;

	@XmlPath("dcat:theme/text()")
	private List<String> theme;

	@XmlPath("dct:accessRights/dct:RightsStatement/text()")
	private String accesslimit;

	@XmlPath("dct:publisher/foaf:name/text()")
	private List<String> publisher;

	@XmlPath("dct:conformsTo/text()")
	private List<String> spatialReferenceSystem;

	@XmlPath("dcat:landingPage/foaf:primaryTopic/text()")
	private List<String> onlineresource;

	@XmlPath("dct:spatial/dct:Location")
	private List<Location> location;

	@XmlPath("dct:temporal")
	private List<TimePeriod> timeperiod;

	@XmlPath("eposap:distribution/dcat:Distribution")
	private List<DatasetDistribution> distribution;

	@XmlPath("dct:created/text()")
	private XMLGregorianCalendar created;

	@XmlPath("dct:subject/text()")
	private List<String> subject;

	@XmlPath("cnt:characterEncoding/text()")
	private String characterset;

	@XmlPath("dcat:contactPoint/text()")
	private List<String> contact;

	@XmlPath("eposap:responsibleParty/text()")
	private String responsibleparty;

	@XmlPath("rdf:comment/text()")
	private String comment;

	@XmlPath("adms:representationTechnique/text()")
	private String spatialrepresentation;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

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

	public XMLGregorianCalendar getIssued() {
		return issued;
	}

	public void setIssued(XMLGregorianCalendar issued) {
		this.issued = issued;
	}

	public XMLGregorianCalendar getModified() {
		return modified;
	}

	public void setModified(XMLGregorianCalendar modified) {
		this.modified = modified;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	public List<String> getProvenance() {
		return provenance;
	}

	public void setProvenance(List<String> provenance) {
		this.provenance = provenance;
	}

	public String getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	public List<String> getTheme() {
		return theme;
	}

	public void setTheme(List<String> theme) {
		this.theme = theme;
	}

	public String getAccesslimit() {
		return accesslimit;
	}

	public void setAccesslimit(String accesslimit) {
		this.accesslimit = accesslimit;
	}

	public List<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	public List<String> getSpatialReferenceSystem() {
		return spatialReferenceSystem;
	}

	public void setSpatialReferenceSystem(List<String> spatialReferenceSystem) {
		this.spatialReferenceSystem = spatialReferenceSystem;
	}

	public List<String> getOnlineresource() {
		return onlineresource;
	}

	public void setOnlineresource(List<String> onlineresource) {
		this.onlineresource = onlineresource;
	}

	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}

	public List<TimePeriod> getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(List<TimePeriod> timeperiod) {
		this.timeperiod = timeperiod;
	}

	public List<DatasetDistribution> getDistribution() {
		return distribution;
	}

	public void setDistribution(List<DatasetDistribution> distribution) {
		this.distribution = distribution;
	}

	public XMLGregorianCalendar getCreated() {
		return created;
	}

	public void setCreated(XMLGregorianCalendar created) {
		this.created = created;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public String getCharacterset() {
		return characterset;
	}

	public void setCharacterset(String characterset) {
		this.characterset = characterset;
	}

	public List<String> getContact() {
		return contact;
	}

	public void setContact(List<String> contact) {
		this.contact = contact;
	}

	public String getResponsibleparty() {
		return responsibleparty;
	}

	public void setResponsibleparty(String responsibleparty) {
		this.responsibleparty = responsibleparty;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSpatialrepresentation() {
		return spatialrepresentation;
	}

	public void setSpatialrepresentation(String spatialrepresentation) {
		this.spatialrepresentation = spatialrepresentation;
	}

}
