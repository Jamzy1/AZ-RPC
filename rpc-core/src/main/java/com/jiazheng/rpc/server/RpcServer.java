package com.jiazheng.rpc.server;

import com.jiazheng.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 远程方法调用的提供者（服务端）
 * <p>
 * 使用一个ServerSocket监听某个端口，循环接收连接请求，如果发来了请求就创建一个线程，在新线程中处理调用。
 *
 * @author Jamzy
 */
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    //  创建一个线程池，客户端发来请求后创建新线程并提交给线程池管理
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;


    /**
     * 为了降低耦合度，不要把 ServiceRegistry 和某一个 RpcServer 绑定在一起，而是在创建 RpcServer
     * 对象时，传入一个 ServiceRegistry 作为这个服务的注册表。
     */
    public RpcServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue =
                new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动...");
            Socket socket;
            //  监听accept()方法，有连接请求时创建新线程并提交给线程池处理
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接：{}:{}",
                        socket.getInetAddress(), socket.getPort());
                //向工作线程传入socket,requestHandler, serviceRegistry，并将线程提交到线程池
                threadPool.execute(new RequestHandlerThread(socket,
                        requestHandler, serviceRegistry));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

}
