package com.scorpio.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
	private IoSession session;
	private MinaHandler minaHandler;
	
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	
	public MinaHandler getMinaHandler() {
		return minaHandler;
	}
	public void setMinaHandler(MinaHandler minaHandler) {
		this.minaHandler = minaHandler;
	}
	public ServerHandler(MinaHandler minaHandler) {
		// TODO Auto-generated constructor stub
		this.minaHandler = minaHandler;
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		session.write("what does the hero truly need?");
		this.minaHandler.setSession(session);
	}
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(session.getRemoteAddress()+":"+message.toString());
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}
}
