package com.scorpio.minademo;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class HelloWorldClientHandler extends IoHandlerAdapter {
	private String msg;
	public HelloWorldClientHandler(String msg) {
		this.msg = msg;
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		//发送消息
		session.write(msg);
	}
}
