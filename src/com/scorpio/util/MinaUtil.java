package com.scorpio.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

public class MinaUtil {
	public static Map<String, IoFilter> addfilters(){
		Map<String, IoFilter> map = new HashMap<String, IoFilter>();
		map.put("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
//		map.put("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		map.put("logger", new LoggingFilter());
		return map;
	}
}
