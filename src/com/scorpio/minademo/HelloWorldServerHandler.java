package com.scorpio.minademo;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class HelloWorldServerHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("HelloWorldServerHandler receive message!");
		//直接转换object对象为string对象并打印
		String msg = message.toString();
		System.out.println("message:" + msg);
	}
}
