package org.epos_ip.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class Check 
{
	public static String checkString(JsonElement s)
	{
		return s!=JsonNull.INSTANCE? s.getAsString() : "null";
	}

	public static Double checkDouble(JsonElement s)
	{
		return s!=null? s.getAsDouble() : 00.00;
	}
}
