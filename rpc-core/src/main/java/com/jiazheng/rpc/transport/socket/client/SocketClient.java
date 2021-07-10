package com.jiazheng.rpc.transport.socket.client;



import com.jiazheng.rpc.registry.NacosServiceRegistry;
import com.jiazheng.rpc.registry.ServiceRegistry;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.RpcClient;
import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.entity.RpcResponse;
import com.jiazheng.rpc.enumeration.ResponseCode;
import com.jiazheng.rpc.enumeration.RpcError;
import com.jiazheng.rpc.exception.RpcException;
import com.jiazheng.rpc.transport.socket.util.ObjectReader;
import com.jiazheng.rpc.transport.socket.util.ObjectWriter;
import com.jiazheng.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * client和server包下是发起和处理请求的具体实现方法
 * Socket方式远程方法调用的消费者（客户端）
 *
 * @author Jamzy
 */
public class SocketClient implements RpcClient {

    private static final Logger logger =
            LoggerFactory.getLogger(SocketClient.class);

    private final ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;

    public SocketClient() {
        this.serviceRegistry = new NacosServiceRegistry();
    }


    //    客户端的请求方法，rpcRequest请求体（确定调用的方法），默认客户端知道服务器的主机和端口
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
        System.out.println(inetSocketAddress.getAddress()+"++++"+inetSocketAddress.getPort());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
