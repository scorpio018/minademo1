package com.scorpio.minademo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaHelloWorldServer {
	public static void main(String[] args) throws IOException {
		//建立mina监听服务器端
		IoAcceptor acceptor = new NioSocketAcceptor();
		//给服务器端增加两个监听器，分别用来记录日志和转换字符编码
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf-8"))));
		//给服务器端增加业务逻辑处理器类，处理具体业务逻辑
		acceptor.setHandler(new HelloWorldServerHandler());
		//服务器端开启监听
		acceptor.bind(new InetSocketAddress(4321));
	}
}
