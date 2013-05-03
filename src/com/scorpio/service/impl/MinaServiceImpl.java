package com.scorpio.service.impl;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.scorpio.service.IMinaService;

public class MinaServiceImpl implements IMinaService {

	@Override
	public IoConnector createClientService() {
		return new NioSocketConnector();
	}

	@Override
	public IoAcceptor createServerService() {
		return new NioSocketAcceptor();
	}

}
