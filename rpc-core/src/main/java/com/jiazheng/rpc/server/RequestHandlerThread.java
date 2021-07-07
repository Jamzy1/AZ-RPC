package com.jiazheng.rpc.server;

import com.jiazheng.rpc.entity.RpcRequest;
import com.jiazheng.rpc.entity.RpcResponse;
import com.jiazheng.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 处理RpcRequest的工作线程
 * @author Jamzy
 */
public class RequestHandlerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            //从客户端的请求体中拿到要调用的服务名称
            String interfaceName = rpcRequest.getInterfaceName();
            //从服务注册表中拿到要调用的服务端的具体服务
            Object service = serviceRegistry.getService(interfaceName);
            //将请求和服务传入处理器
            Object result = requestHandler.handle(rpcRequest, service);

            //请求的回应信息，会传给客户端
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }

}