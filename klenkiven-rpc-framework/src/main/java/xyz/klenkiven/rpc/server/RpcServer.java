package xyz.klenkiven.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.klenkiven.rpc.client.RpcRequest;
import xyz.klenkiven.rpc.registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * RPC远程调用的服务端
 * @author KlenKiven
 */
public class RpcServer {

    private static final Logger log = LoggerFactory.getLogger(RpcServer.class);

    private final ServiceRegistry registry;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final RpcRequestHandler handler;

    public RpcServer(ServiceRegistry registry) {
        this.registry = registry;
        this.handler = new RpcRequestHandler();
        threadPoolExecutor = new ThreadPoolExecutor(
                10,
                10,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("[init] 服务已经启动,端口{}", port);
            Socket clientRequest;
            while ((clientRequest = serverSocket.accept()) != null) {
                log.info("[active] 客户端已连接: {}", clientRequest.getRemoteSocketAddress());
                threadPoolExecutor.execute(new RpcRequestHandleRunnable(registry, clientRequest, handler));
            }
        } catch (IOException e) {
            log.error("[error] 服务异常终止：" + e.getMessage());
        }
    }

    /**
     * RPC 处理 Runnable 类
     */
    static class RpcRequestHandleRunnable implements Runnable {
        private final ServiceRegistry serviceRegistry;
        private final Socket clientRequest;
        private final RpcRequestHandler requestHandler;

        public RpcRequestHandleRunnable(ServiceRegistry serviceRegistry,
                                        Socket clientRequest,
                                        RpcRequestHandler requestHandler) {
            this.serviceRegistry = serviceRegistry;
            this.clientRequest = clientRequest;
            this.requestHandler = requestHandler;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientRequest.getInputStream());
                RpcRequest request = (RpcRequest) objectInputStream.readObject();
                if (request == null) {
                    log.error("客户端请求异常，请求内容为空"); return;
                } else if (request.getServiceName() == null || request.getServiceName().length() == 0) {
                    log.error("客户端请求异常，请求服务内容为空"); return;
                }
                Object service = serviceRegistry.getService(request.getServiceName());

                // Do Handle
                RpcResponse<?> rpcResponse = requestHandler.handle(request, service);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientRequest.getOutputStream());
                objectOutputStream.writeObject(rpcResponse);
                log.info("客户端{}远程调用[{}].{}成功",
                        clientRequest.getRemoteSocketAddress(),
                        request.getServiceName(),
                        request.getMethodName());
                clientRequest.close();
            } catch (IOException | ClassNotFoundException e) {
                log.error("服务处理异常: " + e.getMessage());
            }
        }
    }

}
