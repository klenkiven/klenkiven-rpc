package xyz.klenkiven.rpc;

import xyz.klenkiven.rpc.example.EchoService;
import xyz.klenkiven.rpc.registry.ServiceRegistry;
import xyz.klenkiven.rpc.registry.impl.DefaultServiceRegistry;
import xyz.klenkiven.rpc.server.RpcServer;
import xyz.klenkiven.rpc.service.EchoServiceImpl;

public class RpcServerMain {

    public static void main(String[] args) {
        // 创建注册中心并注册服务
        ServiceRegistry registry = new DefaultServiceRegistry();
        EchoService echoService = new EchoServiceImpl();
        registry.register(echoService, EchoService.class);

        // 创建RPC服务端，并启动服务
        RpcServer server = new RpcServer(registry);
        server.start(2540);
    }

}
