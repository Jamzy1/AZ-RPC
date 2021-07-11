package com.jiazheng.rpc.transport.socket.server;

import com.jiazheng.rpc.enumeration.RpcError;
import com.jiazheng.rpc.exception.RpcException;
import com.jiazheng.rpc.handler.RequestHandler;
import com.jiazheng.rpc.hook.ShutdownHook;
import com.jiazheng.rpc.provider.ServiceProvider;
import com.jiazheng.rpc.provider.ServiceProviderImpl;
import com.jiazheng.rpc.registry.NacosServiceRegistry;
import com.jiazheng.rpc.serializer.CommonSerializer;
import com.jiazheng.rpc.transport.AbstractRpcServer;
import com.jiazheng.rpc.transport.RpcServer;
import com.jiazheng.rpc.registry.ServiceRegistry;
import com.jiazheng.rpc.factory.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Socket方式远程方法调用的提供者（服务端）
 * <p>
 * 使用一个ServerSocket监听某个端口，循环接收连接请求，如果发来了请求就创建一个线程，在新线程中处理调用。
 *
 * @author Jamzy
 */
public class SocketServer extends AbstractRpcServer {

    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();



    /**
     * 为了降低耦合度，不要把 ServiceRegistry 和某一个 RpcServer 绑定在一起，而是在创建 RpcServer
     * 对象时，传入一个 ServiceRegistry 作为这个服务的注册表。
     */
    public SocketServer(String host, int port){
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }

    //服务器启动后调用钩子函数，函数会在关闭服务时执行
    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

}
