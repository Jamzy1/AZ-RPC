package com.jiazheng.rpc.client;


import com.jiazheng.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * client和server包下是发起和处理请求的具体实现方法
 * 远程方法调用的消费者（客户端）
 *
 * @author Jamzy
 */
public class RpcClient {

    private static final Logger logger =
            LoggerFactory.getLogger(RpcClient.class);

//    客户端的请求方法，rpcRequest请求体（确定调用的方法），默认客户端知道服务器的主机和端口
    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            return null;
        }
    }
}
