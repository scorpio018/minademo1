package com.scorpio.niosocket;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MyClient {
	private NioSocketConnector connector;

	/**
	 * Constructor
	 */
	public MyClient() {
		connector = new NioSocketConnector();
		/**
		 * 设置信息交换的IoHandler,负责接收和发送信息的处理
		 */
		connector.setHandler(new ClientIoHandler());
		// 配置过滤器
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		// 增加日志过滤器
		chain.addLast("logger", new LoggingFilter());
		// 增加字符编码过滤器以及设置编码器和解码器
		// chain.addLast("codec", new ProtocolCodecFilter(new
		// TextLineCodecFactory(Charset.forName("UTF-8"))));
		/**
		 * 默认编码器,解码器,遇到\n默认消息结束 当然可以加参数指定解码字符，但解码字符会被截掉 例如:new
		 * TextLineCodecFactory(Charset.forName("UTF-8"),"]","]");
		 * 则会认为"]"为一条消息结束，遇到"]"则截取 比如服务器给你发送的消息是aaaa]aaaa] 会收到两条消息： 1、aaaa
		 * 2、aaaa 后面的"]"则去掉了
		 */
		// acceptor.getFilterChain().addLast(
		// "codec",
		// new ProtocolCodecFilter(new TextLineCodecFactory(Charset
		// .forName("UTF-8"))));// 指定编码过滤器
		DemuxingProtocolCodecFactory pcf = new DemuxingProtocolCodecFactory();
		// 自定义编码器
		pcf.addMessageEncoder(String.class, new MyMessageEncoder());
		// 自定义解码器
		pcf.addMessageDecoder(new MyMessageDecoder());
		ProtocolCodecFilter codec = new ProtocolCodecFilter(pcf);
		chain.addLast("codec", codec);// 指定编码过滤器
		// 设置默认连接的地址和端口
		connector.setDefaultRemoteAddress(new InetSocketAddress("localhost",
				8888));
		/**
		 * 重连机制 如果没有连接，则过30 * 1000毫秒客户端会尝试重新连接服务器 如果连接，则下面的代码不会执行
		 */
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (null != connector && !connector.isActive()) {
					try {
						// 尝试连接默认的地址和端口
						ConnectFuture connFuture = connector.connect();
						connFuture.awaitUninterruptibly();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}, new Date(), 30 * 1000);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyClient();
	}

}
