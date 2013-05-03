package com.scorpio.service;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

public interface IMinaService {
	IoConnector createClientService();
	IoAcceptor createServerService();
}
