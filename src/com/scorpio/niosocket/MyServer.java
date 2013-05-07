package com.scorpio.niosocket;

import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyServer {
	private NioSocketAcceptor   acceptor;  
    /** 
     * Constructor 
     */  
    public MyServer() {  
        try {  
            acceptor = new NioSocketAcceptor();  
            acceptor.getFilterChain().addLast("threadPool",  
                    new ExecutorFilter(new OrderedThreadPoolExecutor()));// 设置线程池，以支持多线程  
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());  
            /** 
             * 默认编码器,解码器,遇到\n默认消息结束 
             * 当然可以加参数指定解码字符，但解码字符会被截掉 
             * 例如:new TextLineCodecFactory(Charset.forName("UTF-8"),"]","]"); 
             * 则会认为"]"为一条消息结束，遇到"]"则截取 
             * 比如服务器给你发送的消息是aaaa]aaaa] 
             * 会收到两条消息： 
             * 1、aaaa 
             * 2、aaaa 
             * 后面的"]"则去掉了 
             */  
//            acceptor.getFilterChain().addLast(  
//                    "codec",  
//                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset  
//                            .forName("UTF-8"))));// 指定编码过滤器  
            DemuxingProtocolCodecFactory pcf = new DemuxingProtocolCodecFactory();  
            //自定义编码器  
            pcf.addMessageEncoder(String.class, new MyMessageEncoder());  
            //自定义解码器  
            pcf.addMessageDecoder(new MyMessageDecoder());  
            ProtocolCodecFilter codec = new ProtocolCodecFilter(pcf);  
            acceptor.getFilterChain().addLast("codec",codec);// 指定编码过滤器  
              
            acceptor.setReuseAddress(true);  
            acceptor.setHandler(new ServerIoHandler());// 指定业务逻辑处理器  
            acceptor.setDefaultLocalAddress(new InetSocketAddress(8888));// 设置端口号  
            acceptor.bind();// 启动监听  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        new MyServer();  
    } 
}
