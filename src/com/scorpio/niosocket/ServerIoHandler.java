package com.scorpio.niosocket;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerIoHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession session, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		// TODO Auto-generated method stub
		// 收到的信息
		System.out.println(obj.toString());
	}

	@Override
	public void messageSent(IoSession session, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		session.write("[Server: Client,I'm server.][Server: Client,I'm server.]");
	}

}
