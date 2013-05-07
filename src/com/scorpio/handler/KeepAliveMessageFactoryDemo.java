package com.scorpio.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class KeepAliveMessageFactoryDemo implements KeepAliveMessageFactory,KeepAliveRequestTimeoutHandler {

	@Override
	public Object getRequest(IoSession arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResponse(IoSession arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequest(IoSession arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResponse(IoSession arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
