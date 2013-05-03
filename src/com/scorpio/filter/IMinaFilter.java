package com.scorpio.filter;

import java.util.Map;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

public interface IMinaFilter {
	IoAcceptor addServerFilter(Map<String, IoFilter> filter);
	IoConnector addClientFilter(Map<String, IoFilter> filter);
}
