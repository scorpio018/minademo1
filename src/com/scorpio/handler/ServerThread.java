package com.scorpio.handler;

import java.io.IOException;

import org.apache.mina.core.service.IoAcceptor;

import com.scorpio.filter.IMinaFilter;
import com.scorpio.filter.impl.MinaFilterImpl;

public class ServerThread extends Thread{
	private IoAcceptor acceptor;
	private IMinaFilter filter = new MinaFilterImpl();
	private MinaHandler minaHandler;
	
	public IoAcceptor getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(IoAcceptor acceptor) {
		this.acceptor = acceptor;
	}
	public IMinaFilter getFilter() {
		return filter;
	}
	public void setFilter(IMinaFilter filter) {
		this.filter = filter;
	}
	public MinaHandler getMinaHandler() {
		return minaHandler;
	}
	public void setMinaHandler(MinaHandler minaHandler) {
		this.minaHandler = minaHandler;
	}
	public ServerThread(MinaHandler minaHandler) {
		this.minaHandler = minaHandler;
		this.minaHandler = new MinaHandler();
		this.minaHandler.Client();
		System.out.println("客户端已经启动！");
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(2000);
				/*if(!minaHandler.getAcceptor().isActive()){
					minaHandler.Server(minaHandler);
					System.out.println("服务器已经启动！");
				}*/
				System.out.println("1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
