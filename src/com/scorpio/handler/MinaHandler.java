package com.scorpio.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

import com.scorpio.filter.IMinaFilter;
import com.scorpio.filter.impl.MinaFilterImpl;

public class MinaHandler{
	private IMinaFilter filter = new MinaFilterImpl();
	private ConnectFuture future;
	private IoSession session;
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

	public void Server(MinaHandler minaHandler) throws IOException{
		IoAcceptor acceptor = filter.addServerFilter(addfilters());
		ServerHandler serverHandler = new ServerHandler(minaHandler);
		acceptor.setHandler(serverHandler);
		acceptor.bind(new InetSocketAddress(4321));
	}
	
	public void Client(){
		IoConnector connector = filter.addClientFilter(addfilters());
		connector.setHandler(new ClientHandler());
		future = connector.connect(new InetSocketAddress("192.168.60.188", 4321));
		session = future.getSession();
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
//		System.out.println("是否启动服务器？(Y/N):");
//		String str = scan.next();
		MinaHandler minaHandler = new MinaHandler();
//		if("Y".equalsIgnoreCase(str)){
			minaHandler.Server(minaHandler);
			System.out.println("服务器已经启动！");
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
			}else{
				minaHandler.getSession().write(str);
			}
		}
	}
	
	public Map<String, IoFilter> addfilters(){
		Map<String, IoFilter> map = new HashMap<String, IoFilter>();
		map.put("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
//		map.put("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		map.put("logger", new LoggingFilter());
		return map;
	}
}
