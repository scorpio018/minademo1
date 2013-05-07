package com.scorpio.filter.impl;

import java.util.Map;
import java.util.Set;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

import com.scorpio.filter.IMinaFilter;
import com.scorpio.handler.ServerThread;
import com.scorpio.service.IMinaService;
import com.scorpio.service.impl.MinaServiceImpl;

public class MinaFilterImpl implements IMinaFilter {
	private IMinaService minaService = new MinaServiceImpl();

	public IMinaService getMinaService() {
		return minaService;
	}

	public void setMinaService(IMinaService minaService) {
		this.minaService = minaService;
	}
	@Override
	public IoConnector addClientFilter(Map<String, IoFilter> filter) {
		IoConnector connector = minaService.createClientService();
		Set<String> key = filter.keySet();
		for (String str : key) {
			connector.getFilterChain().addLast(str, filter.get(str));
		}
		return connector;
	}
	@Override
	public IoAcceptor addServerFilter(Map<String, IoFilter> filter) {
		IoAcceptor acceptor = minaService.createServerService();
		Set<String> key = filter.keySet();
		for (String str : key) {
			acceptor.getFilterChain().addLast(str, filter.get(str));
		}
		return acceptor;
	}
	
}
