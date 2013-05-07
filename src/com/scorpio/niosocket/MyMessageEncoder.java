package com.scorpio.niosocket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MyMessageEncoder implements MessageEncoder<String> {

	@Override
	public void encode(IoSession session, String msg, ProtocolEncoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buf = IoBuffer.allocate(msg.getBytes().length);  
        buf.put(msg.getBytes());  
        buf.flip();  
        out.write(buf);
	}

}
