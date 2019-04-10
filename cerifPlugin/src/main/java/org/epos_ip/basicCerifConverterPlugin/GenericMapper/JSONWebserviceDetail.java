package org.epos_ip.basicCerifConverterPlugin.GenericMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.epos_ip.beans.ServiceParameter;
import org.epos_ip.beans.Webservice;
import org.epos_ip.utility.Check;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONWebserviceDetail {
    public static void generate(JsonArray result, ArrayList<Webservice> wsl, int i, HashMap<String, ServiceParameter> parametersMap)
    {
	boolean exist = false;
	Webservice ws = null;
	ServiceParameter spp = null;
	JsonObject value = result.get(i).getAsJsonObject();
	for(Webservice wsx : wsl)
	{
	    if(wsx.getIdentifier().equals(Check.checkString(value.get("webserviceid"))))
	    {
		exist = true;
		ws = wsx;
	    }
	    
	}
	if(exist)
	{
	    if(parametersMap.containsKey((Check.checkString(value.get("cfmeasid")))))
	    {
		
		spp = parametersMap.get((Check.checkString(value.get("cfmeasid"))));
		parameterContext(spp, value);
		ws.setParameter(parametersMap.values().stream().collect(Collectors.toList()));
	    }
	    else{
		//System.out.println("add new parameter");
		ServiceParameter sp = new ServiceParameter();
		parameterContext(sp, value);
		parametersMap.put(Check.checkString(value.get("cfmeasid")), sp);
	    } 
	}
	else{
	    ws = new Webservice();
	    ArrayList<String> titles = new ArrayList<String>();
	    titles.add(Check.checkString(value.get("title")));
	    ws.setTitle(titles); 
	    
	    ws.setIdentifier(Check.checkString(value.get("webserviceid")));

	    ws.setURI(Check.checkString(value.get("uri")));
	    
	    ArrayList<String> formats = new ArrayList<String>();
	    formats.add(Check.checkString(value.get("format")));
	    ws.setFormat(formats);
	    
	    System.out.println(Check.checkString(value.get("ddssid")));
	    ws.setDdssid(Check.checkString(value.get("ddssid")));
	    
	    ws.setActions(Check.checkString(value.get("action")));
	    
	    try{
		ServiceParameter sp = new ServiceParameter();
		parameterContext(sp, value);
		parametersMap.put(Check.checkString(value.get("cfmeasid")), sp);
		
	    }catch(Exception e){ e.printStackTrace();}
	    ArrayList<ServiceParameter> list = new ArrayList<ServiceParameter>();
	    
	    ws.setParameter(list);
	    wsl.add(ws);
	    
	}
    }
    
    private static void parameterContext(ServiceParameter spp, JsonObject value)
    {
	if(Check.checkString(value.get("cfterm")).equals("Parameter Name")) spp.setName(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Label")) spp.setLabel(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Type")) spp.setType(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Minimum Value")) spp.setMinValue(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Maximum Value")) spp.setMaxValue(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Valid Value")) spp.setValue(Check.checkString(value.get("relatedmeasure")));
	if(Check.checkString(value.get("cfterm")).equals("Parameter Version")) spp.setVersion(Check.checkString(value.get("relatedmeasure")));
    }
}
