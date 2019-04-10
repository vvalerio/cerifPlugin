package org.epos_ip.basicCerifConverterPlugin.core;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.epos_ip.beans.Address;
import org.epos_ip.beans.Baseline;
import org.epos_ip.beans.Location;
import org.epos_ip.beans.Organisation;
import org.epos_ip.beans.Person;
import org.epos_ip.beans.ServiceParameter;
import org.epos_ip.beans.TimePeriod;
import org.epos_ip.beans.Webservice;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
@Deprecated
public class Mapper 
{
    
    public JAXBContext jaxbContext = initContext();
    private Baseline bsl;
    private String xmlOut;
    
    private JAXBContext initContext() {
	try {
	    return JAXBContext.newInstance(org.epos_ip.beans.Baseline.class);
	} catch (JAXBException e) {
	    return null;
	}
    }
    
    
    public Mapper() {}
    
    public void getXML(JsonObject resultset, String researchType) throws XMLStreamException, FactoryConfigurationError, JAXBException
    {
	jaxbContext = initContext();
	StringWriter swr = new StringWriter();
	@SuppressWarnings("unused")
	XMLStreamWriter sw = XMLOutputFactory.newInstance().createXMLStreamWriter(swr);
	
	bsl = new Baseline();
	ArrayList<Webservice> wsl = null;
	ArrayList<Person> pl = null;
	ArrayList<Organisation> ol = null;
	HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
	
	for(Entry<String, JsonElement> entries : resultset.entrySet())
	{
	    
	    String type = entries.getKey().replaceAll("ResultSet_", "");
	    if(type.equals("person")) pl = new ArrayList<Person>();
	    if(type.equals("organisation")) ol = new ArrayList<Organisation>();
	    if(type.equals("webservice_with_param")) wsl = new ArrayList<Webservice>();
	    JsonArray result = (JsonArray) entries.getValue();
	    for(int i = 0; i<result.size(); i++)
	    {
		if(type.equals("person"))
		{
		    Person p = null;
		    boolean exist = false;
		    for(Person px : pl)
		    {
			if(px.getIdentifier().equals(checkAndInsert(result.get(i).getAsJsonObject().get("identifier"))))
			{
			    
			    exist = true;
			    p = px;
			}
			
		    }
		    if(!exist)
		    {
			//System.out.println("insert person");
			//PERSON
			
			p = new Person();
			p.setName(checkAndInsert(result.get(i).getAsJsonObject().get("fullname"))); //MANDATORY
			
			Address a = new Address();
			a.setStreet(checkAndInsert(result.get(i).getAsJsonObject().get("street")));
			a.setLocality(checkAndInsert(result.get(i).getAsJsonObject().get("locality")));
			a.setPostcode(checkAndInsert(result.get(i).getAsJsonObject().get("postcode")));
			a.setCountry(checkAndInsert(result.get(i).getAsJsonObject().get("country")));
			p.setAddress(a); //MANDATORY
			
			p.setEmail(checkAndInsert(result.get(i).getAsJsonObject().get("email")));
			p.setPhone(checkAndInsert(result.get(i).getAsJsonObject().get("phone")));
			p.setIdentifier(checkAndInsert(result.get(i).getAsJsonObject().get("identifier"))); //MANDATORY
			p.setAffiliation(checkAndInsert(result.get(i).getAsJsonObject().get("affiliation"))); //MANDATORY
			p.setLanguage(checkAndInsert(result.get(i).getAsJsonObject().get("language"))); //MANDATORY
			p.setQualification(checkAndInsert(result.get(i).getAsJsonObject().get("qualification")));
			p.setCv(checkAndInsert(result.get(i).getAsJsonObject().get("cv")));
			pl.add(p);
			//System.out.println("PERSON inserted");
		    }
		    
		}
		if(type.equals("organisation"))
		{
		    Organisation o = null;
		    boolean exist = false;
		    for(Organisation ox : ol)
		    {
			if(ox.getIdentifier().equals(checkAndInsert(result.get(i).getAsJsonObject().get("identifier"))))
			{
			    
			    exist = true;
			    o = ox;
			}
			
		    }
		    if(!exist)
		    {
			//System.out.println("inserisco organisation");
			//ORGANISATION
			o = new Organisation();
			o.setName_en(checkAndInsert(result.get(i).getAsJsonObject().get("name"))); //MANDATORY
			o.setName_native(checkAndInsert(result.get(i).getAsJsonObject().get("nativename")));
			
			
			Address a = new Address();
			a.setStreet(checkAndInsert(result.get(i).getAsJsonObject().get("street")));
			a.setLocality(checkAndInsert(result.get(i).getAsJsonObject().get("locality")));
			a.setPostcode(checkAndInsert(result.get(i).getAsJsonObject().get("postcode")));
			a.setCountry(checkAndInsert(result.get(i).getAsJsonObject().get("country")));
			o.setAddress(a); //MANDATORY
			
			o.setEmail(checkAndInsert(result.get(i).getAsJsonObject().get("email")));
			o.setWebsite(checkAndInsert(result.get(i).getAsJsonObject().get("website")));
			o.setPhone(checkAndInsert(result.get(i).getAsJsonObject().get("phone")));
			o.setLogo(checkAndInsert(result.get(i).getAsJsonObject().get("logo")));
			
			o.setIdentifier(checkAndInsert(result.get(i).getAsJsonObject().get("identifier"))); //MANDATORY
			o.setScientificcontact(checkAndInsert(result.get(i).getAsJsonObject().get("scientificcontact"))); //MANDATORY
			Location l = new Location();
			l.setMinelev(checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation")));
			l.setMinlat(checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude")));
			l.setMinlon(checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude")));
			l.setMaxelev(checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation")));
			l.setMaxlat(checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude")));
			l.setMaxlon(checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude")));
			o.setLocation(l);
			ArrayList<String> types = new ArrayList<String>();
			types.add(checkAndInsert(result.get(i).getAsJsonObject().get("orgatype")));
			o.setType(types); //MANDATORY
			//System.out.println("inserisco altro");
			o.setLegalcontact(checkAndInsert(result.get(i).getAsJsonObject().get("legalcontact"))); //MANDATORY
			o.setFinancialcontact(checkAndInsert(result.get(i).getAsJsonObject().get("fincancialcontact"))); //MANDATORY
			o.setParentorganisation(checkAndInsert(result.get(i).getAsJsonObject().get("parentid")));
			
			ol.add(o);
			//System.out.println("ORGANISATION inserita");
		    }
		    
		}
		
		if(type.equals("webservice_without_param") || type.equals("webservice_with_param"))
		{
		    //WEBSERVICE
		    //System.out.println("calculating");
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    boolean exist = false;
		    Webservice ws = null;
		    ServiceParameter spp = null;
		    //System.out.println("created elements");
		    for(Webservice wsx : wsl)
		    {
			//System.out.println("CHECK: "+wsx.getIdentifier());
			if(wsx.getIdentifier().equals(checkAndInsert(result.get(i).getAsJsonObject().get("webserviceid"))))
			{
			    //System.out.println("FOUND: "+wsx.getIdentifier());
			    exist = true;
			    ws = wsx;
			}
			
		    }
		    if(exist)
		    {
			//System.out.println("adding");
			if(parametersMap.containsKey((checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")))))
			{
			    spp = parametersMap.get((checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid"))));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) spp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) spp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) spp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) spp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) spp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) spp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) spp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    for(String sp : parametersMap.keySet())
			    {
				//System.out.println(sp+"   "+parametersMap.get(sp));
			    }
			    ws.setParameter(parametersMap.values().stream().collect(Collectors.toList()));
			}
			else{
			    //System.out.println("add new parameter");
			    ServiceParameter sp = new ServiceParameter();
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) sp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) sp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) sp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) sp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) sp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) sp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) sp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    parametersMap.put(checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")), sp);
			} 
		    }
		    else{
			ws = new Webservice();
			//System.out.println("add title");
			ArrayList<String> titles = new ArrayList<String>();
			titles.add(checkAndInsert(result.get(i).getAsJsonObject().get("title")));
			ws.setTitle(titles); 
			
			//System.out.println("add description");
			ArrayList<String> descriptions = new ArrayList<String>();
			descriptions.add(checkAndInsert(result.get(i).getAsJsonObject().get("description")));
			ws.setDescription(descriptions); //MANDATORY
			
			
			//System.out.println("add dates");
			GregorianCalendar c = new GregorianCalendar();
			XMLGregorianCalendar date2 = null;
			
			try {
			    c.setTime(df.parse(checkAndInsert(result.get(i).getAsJsonObject().get("published"))));
			    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			    ws.setPublished(date2); //MANDATORY
			    
			    c.setTime(df.parse(checkAndInsert(result.get(i).getAsJsonObject().get("modified"))));
			    
			    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			    ws.setModified(date2); //MANDATORY
			    
			    c.setTime(df.parse(checkAndInsert(result.get(i).getAsJsonObject().get("created"))));
			    
			    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			    ws.setCreated(date2);
			    
			    TimePeriod tp = new TimePeriod();
			    c.setTime(df.parse(checkAndInsert(result.get(i).getAsJsonObject().get("temporalstartdate"))));
			    
			    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			    tp.setStart(date2);
			    c.setTime(df.parse(checkAndInsert(result.get(i).getAsJsonObject().get("temporalenddate"))));
			    
			    date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			    tp.setEnd(date2);
			    ws.setTemporalextent(tp);
			} catch (ParseException e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			} catch (DatatypeConfigurationException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			
			
			//System.out.println("add lineage");
			ArrayList<String> licenses = new ArrayList<String>();
			licenses.add(checkAndInsert(result.get(i).getAsJsonObject().get("accessanduserestriction")));
			ws.setLicense(licenses);
			//System.out.println("add uri");
			ws.setURI(checkAndInsert(result.get(i).getAsJsonObject().get("uri"))); //MANDATORY
			
			ArrayList<String> formats = new ArrayList<String>();
			formats.add(checkAndInsert(result.get(i).getAsJsonObject().get("format")));
			ws.setFormat(formats); //MANDATORY
			
			//System.out.println("add accessanduserestriction");
			ws.setAccesslimit(checkAndInsert(result.get(i).getAsJsonObject().get("publicaccesslimit")));
			
			//System.out.println("add spatialreferencesystem");
			ws.setSpatialReferenceSystem(checkAndInsert(result.get(i).getAsJsonObject().get("spatialreferencesystem")));
			
			//System.out.println("add webserviceid");
			ws.setIdentifier(checkAndInsert(result.get(i).getAsJsonObject().get("webserviceid")));
			
			
			//System.out.println("add domain");
			ws.setDomain(checkAndInsert(result.get(i).getAsJsonObject().get("domain")));
			
			//System.out.println("add subdomain");
			ws.setSubDomain(checkAndInsert(result.get(i).getAsJsonObject().get("subdomain")));
			
			//System.out.println("add keywords");
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add(checkAndInsert(result.get(i).getAsJsonObject().get("keyword")));
			ws.setKeyword(keywords);
			
			//System.out.println("add operations");
			ArrayList<String> operations = new ArrayList<String>();
			operations.add(checkAndInsert(result.get(i).getAsJsonObject().get("operation")));
			ws.setOperation(operations);
			
			//System.out.println("add version");
			ws.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("version")));
			try{
			    //System.out.println("add parameters");
			    ServiceParameter sp = new ServiceParameter();
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) sp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) sp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) sp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) sp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) sp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) sp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) sp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
			    parametersMap.put(checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")), sp);
			    
			}catch(Exception e){ e.printStackTrace();}
			//System.out.println("add doc");
			//temp = result.get(i).getAsJsonObject().get("");
			ws.setDocumentation(checkAndInsert(result.get(i).getAsJsonObject().get("documentation")));
			
			//System.out.println("add contact");
			ArrayList<String> contactpoints = new ArrayList<String>();
			contactpoints.add(checkAndInsert(result.get(i).getAsJsonObject().get("contactid")));
			ws.setContactpoint(contactpoints); //MANDATORY
			
			//System.out.println("add responsible");
			ArrayList<String> responsibleparties = new ArrayList<String>();
			responsibleparties.add(checkAndInsert(result.get(i).getAsJsonObject().get("publisherid")));
			ws.setResponsibleparty(responsibleparties); //MANDATORY
			
			//System.out.println("add location");
			Location l = new Location();
			l.setMaxelev(checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation_max")));
			l.setMaxlat(checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude_max")));
			l.setMaxlon(checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude_max")));
			l.setMinelev(checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation_min")));
			l.setMinlat(checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude_min")));
			l.setMinlon(checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude_min")));
			ws.setLocation(l);
			
			//System.out.println("add spatial representation");
			ws.setSpatialrepresentation(checkAndInsert(result.get(i).getAsJsonObject().get("spatialrepresentation")));
			
			//System.out.println("add to list");
			ArrayList<ServiceParameter> list = new ArrayList<ServiceParameter>();
			
			ws.setParameter(list);
			wsl.add(ws);
			
			//System.out.println("added to list");
			
		    }
		    
		}
		
	    }
	    
	    if(type.equals("webservice_with_param")) bsl.setWebservices(wsl);
	    if(type.equals("person")) bsl.setPersons(pl);
	    
	    if(type.equals("organisation")) bsl.setOrganisations(ol);;
	    
	    //System.out.println("added to bsl");
	    //bsl.setProjects(pjl);
	    
	    //System.out.println("write xml");
	    //writeXML(bsl, sw);
	    
	    xmlOut = asString(jaxbContext, bsl);
	    //System.out.println("xml written");
	    
	}
    }
    
    public void getJSONDetails(JsonObject resultset, String researchType)
    {
	JsonObject resultJson = new JsonObject();
	//System.out.println("test print");
	//System.out.println(resultset.toString());
	//System.out.println("test success");
	
	HashMap<String, ServiceParameter> parametersMap = new HashMap<String, ServiceParameter>();
	ArrayList<Webservice> wsl = new ArrayList<Webservice>();
	for(Entry<String, JsonElement> entries : resultset.entrySet())
	{
	    if(entries.getValue().getAsJsonArray().size()==0) continue;
	    else
	    {
		JsonArray result = (JsonArray) entries.getValue();
		for(int i = 0; i<result.size(); i++)
		{
		    String type = entries.getKey().replaceAll("ResultSet_", "");
		    
		    if(type.split("_")[0].equals("webservice"))
		    {
			//WEBSERVICE
			boolean exist = false;
			Webservice ws = null;
			ServiceParameter spp = null;
			//System.out.println("created elements");
			for(Webservice wsx : wsl)
			{
			    //System.out.println("CHECK: "+wsx.getIdentifier());
			    if(wsx.getIdentifier().equals(checkAndInsert(result.get(i).getAsJsonObject().get("webserviceid"))))
			    {
				//System.out.println("FOUND: "+wsx.getIdentifier());
				exist = true;
				ws = wsx;
			    }
			    
			}
			if(exist)
			{
			    //System.out.println("adding");
			    if(parametersMap.containsKey((checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")))))
			    {
				spp = parametersMap.get((checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid"))));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) spp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) spp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) spp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) spp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) spp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) spp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) spp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				for(String sp : parametersMap.keySet())
				{
				    //System.out.println(sp+"   "+parametersMap.get(sp));
				}
				ws.setParameter(parametersMap.values().stream().collect(Collectors.toList()));
			    }
			    else{
				//System.out.println("add new parameter");
				ServiceParameter sp = new ServiceParameter();
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) sp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) sp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) sp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) sp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) sp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) sp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) sp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				parametersMap.put(checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")), sp);
			    } 
			}
			else{
			    ws = new Webservice();
			    //System.out.println("add title");
			    ArrayList<String> titles = new ArrayList<String>();
			    titles.add(checkAndInsert(result.get(i).getAsJsonObject().get("title")));
			    ws.setTitle(titles); 
			    
			    ws.setIdentifier(checkAndInsert(result.get(i).getAsJsonObject().get("webserviceid")));
			    
			    //System.out.println("add webserviceid");
			    ws.setURI(checkAndInsert(result.get(i).getAsJsonObject().get("uri")));
			    
			    ArrayList<String> formats = new ArrayList<String>();
			    formats.add(checkAndInsert(result.get(i).getAsJsonObject().get("format")));
			    ws.setFormat(formats);
			    
			    try{
				//System.out.println("add parameters");
				ServiceParameter sp = new ServiceParameter();
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Name")) sp.setName(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Label")) sp.setLabel(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Type")) sp.setType(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Minimum Value")) sp.setMinValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Maximum Value")) sp.setMaxValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Valid Value")) sp.setValue(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				if(checkAndInsert(result.get(i).getAsJsonObject().get("cfterm")).equals("Parameter Version")) sp.setVersion(checkAndInsert(result.get(i).getAsJsonObject().get("relatedmeasure")));
				parametersMap.put(checkAndInsert(result.get(i).getAsJsonObject().get("cfmeasid")), sp);
				
			    }catch(Exception e){ e.printStackTrace();}
			    
			    //System.out.println("add to list");
			    ArrayList<ServiceParameter> list = new ArrayList<ServiceParameter>();
			    
			    ws.setParameter(list);
			    wsl.add(ws);
			    
			    //System.out.println("added to list");
			    
			}
			
		    }
		}
		
	    }
	}
	if(!wsl.isEmpty())
	{
	    //System.out.println("webservice created");
	    JsonArray result = new JsonArray();
	    JsonObject jj = new JsonObject();
	    jj.addProperty("name", wsl.get(0).getTitle().get(0));
	    jj.addProperty("endpoint", wsl.get(0).getURI());
	    jj.addProperty("response", wsl.get(0).getFormat().get(0));
	    
	    JsonArray parameters = new JsonArray();
	    
	    for(ServiceParameter sp : wsl.get(0).getParameter())
	    {
		//System.out.println("PARAMETER ---> "+sp.getName());
		JsonObject singleParameter = new JsonObject();
		try{
		    if(sp.getName()!=null) singleParameter.addProperty("name", sp.getName());
		    if(sp.getType()!=null) singleParameter.addProperty("type", sp.getType());
		    if(sp.getLabel()!=null) singleParameter.addProperty("label", sp.getLabel());
		    JsonArray values = new JsonArray();
		    if(sp.getMaxValue()!=null) values.add(sp.getMaxValue());
		    if(sp.getMinValue()!=null)  values.add(sp.getMinValue());
		    if(sp.getMinValue()!=null && sp.getMaxValue()!=null) singleParameter.add("value", values);
		    if(!sp.getValue().isEmpty()) {
			sp.getValue().forEach(e -> values.add(e));
			//values.add(sp.getValue());
			singleParameter.add("value", values);
		    }
		    if(sp.getVersion()!=null) singleParameter.addProperty("version", sp.getVersion());
		}catch(Exception e) {
		    //System.out.println("ECCEZIONE: "+e.getMessage());
		}
		parameters.add(singleParameter);
	    }
	    jj.add("parameters", parameters);
	    result.add(jj);
	    resultJson.add("operations", result);
	    
	    xmlOut = resultJson.toString();
	    
	}
	else xmlOut = "{}";
    }
    
    
    public void getJSON(JsonObject resultset, String researchType)
    {
	JsonObject resultJson = new JsonObject();
	JsonObject summary = null;
	JsonArray keywords = null;
	JsonArray domains = null;
	//adding summary
	if(researchType.equals("search"))
	{
	    summary = new JsonObject();
	    summary.addProperty("Page", 1);
	    summary.addProperty("resultsPerPage", 25);
	}
	else if(researchType.equals("domains")){
	    domains = new JsonArray();
	}
	else if(researchType.equals("keywords")){
	    keywords = new JsonArray();
	}
	
	int counter = 0;
	
	JsonArray results = new JsonArray();
	
	for(Entry<String, JsonElement> entries : resultset.entrySet())
	{
	    JsonArray result = (JsonArray) entries.getValue();
	    for(int i = 0; i<result.size(); i++)
	    {
		counter++;
		String type = entries.getKey().replaceAll("ResultSet_", "");
		if(type.equals("person"))
		{
		    
		}
		if(type.equals("organisation"))
		{
		    JsonObject element = new JsonObject();
		    element.addProperty("name", checkAndInsert(result.get(i).getAsJsonObject().get("name")));
		    element.addProperty("id", checkAndInsert(result.get(i).getAsJsonObject().get("identifier")));
		    element.addProperty("type", type);
		    JsonObject geometry = new JsonObject();
		    geometry.addProperty("elev", checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation")));
		    geometry.addProperty("lat", checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude")));
		    geometry.addProperty("long", checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude")));
		    element.add("geometry", geometry);
		    
		    if(results.size()==0) results.add(element);
		    else if(!results.contains(element)) results.add(element);
		}
		if(type.split("_")[0].equals("webservice"))
		{
		    JsonObject element = new JsonObject();
		    element.addProperty("name", checkAndInsert(result.get(i).getAsJsonObject().get("title")));
		    element.addProperty("id", checkAndInsert(result.get(i).getAsJsonObject().get("webserviceid")));
		    element.addProperty("description", checkAndInsert(result.get(i).getAsJsonObject().get("description")));
		    element.addProperty("type", type.split("_")[0]);
		    JsonObject geometry = new JsonObject();
		    geometry.addProperty("minElev", checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation_min")));
		    geometry.addProperty("minLat", checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude_max")));
		    geometry.addProperty("minLong", checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude_min")));
		    geometry.addProperty("maxElev", checkAndInsertDouble(result.get(i).getAsJsonObject().get("elevation_max")));
		    geometry.addProperty("maxLat", checkAndInsertDouble(result.get(i).getAsJsonObject().get("latitude_min")));
		    geometry.addProperty("maxLong", checkAndInsertDouble(result.get(i).getAsJsonObject().get("longitude_max")));
		    element.add("geometry", geometry);
		    if(results.size()==0) results.add(element);
		    else if(!results.contains(element)) results.add(element);
		    
		}
		if(type.equals("domains"))
		{
		    
		    try{
			String domainList = result.get(i).getAsJsonObject().get("domain").getAsString().toLowerCase();
			for(String dom : domainList.split(", "))
			{
			    boolean found = false;
			    for(JsonElement e : domains)
			    {
				if(e.getAsString().equals(dom)) found = true;
			    }
			    if(!found) domains.add(dom);
			    
			    JsonArray subdomainList = new JsonArray();
			    String subDomList = result.get(i).getAsJsonObject().get("subdomain").getAsString().toLowerCase();
			    for(String s : subDomList.split(", "))
			    {
				subdomainList.add(s.toLowerCase());
			    }
			    if(!found) resultJson.add(dom,subdomainList);
			    else{
				for(JsonElement je : subdomainList)
				{
				    if(resultJson.get(dom).getAsJsonArray().contains(je)) continue;
				    else resultJson.get(dom).getAsJsonArray().add(je);
				    
				}
			    }
			}
			
		    }
		    catch(Exception e){}
		    
		}
		if(type.equals("keywords"))
		{
		    try{
			String keyList = result.get(i).getAsJsonObject().get("keyword").getAsString();
			for(String s : keyList.split(", |,"))
			{
			    boolean found = false;
			    for(JsonElement e : keywords)
			    {
				if(e.getAsString().equals(s.toLowerCase())) found = true;
			    }
			    if(!found) keywords.add(s.toLowerCase());
			    
			}
		    }
		    catch(Exception e){}
		}
	    }
	}
	if(researchType.equals("search"))
	{
	    summary.addProperty("numberOfResults", counter);
	    resultJson.add("summary", summary);
	    resultJson.add("results", results);
	}
	else if(researchType.equals("domains")){
	    resultJson.add("domains", domains);
	}
	else if(researchType.equals("keywords")){
	    resultJson.add(researchType, keywords);
	}
	
	xmlOut = resultJson.toString();
    }
    
    public static String checkAndInsert(JsonElement s)
    {
	return s!=JsonNull.INSTANCE? s.getAsString() : "null";
    }
    
    public static Double checkAndInsertDouble(JsonElement s)
    {
	//System.out.println("Print -> "+s);
	return s!=JsonNull.INSTANCE? s.getAsDouble() : 00.00;
    }
    
    
    public void writeXML(Baseline obj, XMLStreamWriter sw) throws JAXBException {
	Marshaller marshaller = jaxbContext.createMarshaller();
	//marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.epos-ip.org/terms.html https://raw.githubusercontent.com/epos-eu/EPOS-DCAT-AP/master/schemas/EPOS-DCAT-AP.xsd");
	//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	marshaller.marshal(obj, sw);
    }
    
    public String asString(JAXBContext pContext, Object pObject)
	    throws JAXBException {
	java.io.StringWriter sw = new StringWriter();
	Marshaller marshaller = pContext.createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.epos-ip.org/terms.html https://raw.githubusercontent.com/epos-eu/EPOS-DCAT-AP/master/schemas/EPOS-DCAT-AP.xsd");
	marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	marshaller.marshal(pObject, sw);
	return sw.toString();
    }
    
    
    public String getOut() {
	return xmlOut;
    }
    
    public void setOut(String xmlOut) {
	this.xmlOut = xmlOut;
    }   
}
