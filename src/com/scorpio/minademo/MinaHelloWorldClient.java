package com.scorpio.minademo;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaHelloWorldClient {
	public static void main(String[] args) {
		//创建客户端连接器
		IoConnector connector = new NioSocketConnector();
		//设置超时时间
		connector.setConnectTimeoutMillis(5000);
		//设置字符串编码过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
		//设置业务逻辑处理类
		connector.setHandler(new HelloWorldClientHandler("Hello world,mina!!"));
		//连接
		connector.connect(new InetSocketAddress("192.168.60.188", 4321));
	}
}
