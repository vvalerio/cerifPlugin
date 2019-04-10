package org.epos_ip.beans;

import javax.xml.bind.annotation.adapters.XmlAdapter;

@SuppressWarnings("restriction")
public class StringTrimXmlAdapter extends XmlAdapter<String, String> {
	@Override
	public String unmarshal(String v) throws Exception {
		if (v == null)
			return null;
		return v.trim();
	}

	@Override
	public String marshal(String v) throws Exception {
		if (v == null)
			return null;
		return v.trim();
	}
}
