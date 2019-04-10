package org.epos_ip.basicCerifConverterPlugin.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface MapperInterface 
{
    JsonElement generate(JsonObject resultSet, String researchType, String format);
    
}
