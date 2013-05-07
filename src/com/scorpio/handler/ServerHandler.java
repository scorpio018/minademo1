package com.scorpio.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter{
	private IoSession session;
	private MinaHandler minaHandler;
	private ThreadPoolExecutor tp = new ThreadPoolExecutor(10, 10, 5000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
	
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
		session.write("connect on!");
		this.minaHandler.setSession(session);
		
	}
	@Override
	public void messageReceived(final IoSession session, final Object message)
			throws Exception {
		System.out.println(session.getRemoteAddress()+":"+message.toString() + "时间：" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
		session.write(session.getRemoteAddress()+":"+message.toString());
		tp.execute(new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.write(session.getRemoteAddress() + ":" + message.toString() + "时间：" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
			}
		});
		
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}
	
}
