package com.scorpio.handler;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(session.getRemoteAddress()+":"+message.toString());
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}
}
