package com.scorpio.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.scorpio.filter.IMinaFilter;
import com.scorpio.filter.impl.MinaFilterImpl;
import com.scorpio.util.MinaUtil;

public class MinaHandler{
	private IMinaFilter filter = new MinaFilterImpl();
	private ConnectFuture future;
	private IoSession session;
	private IoAcceptor acceptor;
	private Map<String, IoSession> sessions = new HashMap<String, IoSession>();

	public IMinaFilter getFilter() {
		return filter;
	}

	public void setFilter(IMinaFilter filter) {
		this.filter = filter;
	}
	
	public ConnectFuture getFuture() {
		return future;
	}

	public void setFuture(ConnectFuture future) {
		this.future = future;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
		this.sessions.put(session.getRemoteAddress().toString(), session);
	}

	public Map<String, IoSession> getSessions() {
		return sessions;
	}

	public void setSessions(Map<String, IoSession> sessions) {
		this.sessions = sessions;
	}

	public IoAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(IoAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public void Server(MinaHandler minaHandler) throws IOException{
		this.acceptor = filter.addServerFilter(MinaUtil.addfilters());
		ServerHandler serverHandler = new ServerHandler(minaHandler);
		this.acceptor.setHandler(serverHandler);
		this.acceptor.bind(new InetSocketAddress(4321));
		
//		Thread th = new ServerThread(minaHandler);
//		th.start();
	}
	
	public void Client(){
		IoConnector connector = filter.addClientFilter(MinaUtil.addfilters());
		ClientHandler clientHandler = new ClientHandler();
		connector.setConnectTimeoutMillis(5000);
		connector.setHandler(clientHandler);
//		future = connector.connect(new InetSocketAddress("192.168.60.188", 4321));
		future = connector.connect(new InetSocketAddress("192.168.60.207", 60004));
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
//		System.out.println("是否启动服务器？(Y/N):");
//		String str = scan.next();
		MinaHandler minaHandler = new MinaHandler();
//		if("Y".equalsIgnoreCase(str)){
//		minaHandler.Server(minaHandler);
//		System.out.println("服务器已经启动！");
//		}
		
//		System.out.println("是否启动客户端？(Y/N):");
//		str = scan.next();
//		if("Y".equalsIgnoreCase(str)){
			minaHandler.Client();
			System.out.println("客户端已经启动！");
//		}
		while(scan.hasNext()){
			String str = scan.next();
			if(str.equals("menu")){
				System.out.println("--------------请选择要聊天的对象---------------");
				Map<String, IoSession> sessions = new HashMap<String, IoSession>();
				sessions = minaHandler.getSessions();
				Set<String> keys = sessions.keySet();
				int i = 1;
				for (String key : keys) {
					System.out.println(i + ":" + sessions.get(key));
					i ++;
				}
				System.out.println("请输入序号:");
				str = scan.next();
				String key = (String) keys.toArray()[(Integer.parseInt(str) - 1)];
				minaHandler.setSession(sessions.get(key));
				System.out.println("切换完毕！当前聊天对象为：" + minaHandler.getSession().getRemoteAddress());
			}else if(str.equals("close")){
				return;
			}else{
				minaHandler.getFuture().getSession().write(str);
			}
		}
	}
}
