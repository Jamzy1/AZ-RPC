package com.jiazheng.rpc.codec;

import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.enumeration.PackageType;
import com.jiazheng.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 通用的编码拦截器
 * @author Jamzy
 */
public class CommonEncoder extends MessageToByteEncoder {

    //4 字节魔数，表识一个协议包
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg,
                          ByteBuf out) throws Exception {
        //加上魔数
        out.writeInt(MAGIC_NUMBER);

        //判断消息类型，加上标识
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        }else{
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }

        //加上编码方式
        out.writeInt(serializer.getCode());
        //对消息序列化
        byte[] bytes = serializer.serialize(msg);

        //加上加上消息长度和序列化后的消息
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
