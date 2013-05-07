package com.scorpio.handler;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.scorpio.util.DoMessage;

public class ClientHandler extends IoHandlerAdapter {
	private DoMessage domsg;
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(session.getRemoteAddress()+":"+message.toString());
//		Object ob = domsg.doMessage(message);
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}
}
