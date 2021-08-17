package org.epos_ip.basicCerifConverterPlugin.core;

import java.util.Optional;

import org.epos.converter.common.exception.PayloadProcessingException;
import org.epos.converter.common.exception.PluginConfigurationException;
import org.epos.converter.common.java.CallableJavaPlugin;
import org.epos.converter.common.plugin.type.MappingDescriptor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DDSSInvoker extends CallableJavaPlugin {

	protected DDSSInvoker(MappingDescriptor mapping) throws PluginConfigurationException {
		super(mapping);
		// TODO Auto-generated constructor stub
	}


	/*public static void main(String args[]) throws ClassNotFoundException, PluginConfigurationException, PayloadProcessingException{
		Class.forName("org.epos_ip.basicCerifConverterPlugin.core.DDSSInvoker");
		System.out.println("DDSSInvoker class successfully loaded");
	}*/

	//public DDSSInvoker(ConversionDescriptor conversion) throws PluginConfigurationException {
		// TODO Auto-generated constructor stub
	//}

	private static Gson gson = new Gson();

	@Override
	protected Optional<String> doInvoke(String payload) throws PayloadProcessingException {
		return Optional.of(PluginMapper.convertResult(gson.fromJson(payload, JsonObject.class), null, null, false).toString());
	}


	public static Optional<String> doTest(String payload) {
		return Optional.of(PluginMapper.convertResult(gson.fromJson(payload, JsonObject.class), null, null, false).toString());
	}

}
