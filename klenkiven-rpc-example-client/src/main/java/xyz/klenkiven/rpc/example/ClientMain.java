package xyz.klenkiven.rpc.example;

import xyz.klenkiven.rpc.client.RpcClientProxy;

public class ClientMain {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 2540;
        EchoService echoService = RpcClientProxy.getProxy(host, port, EchoService.class);
        Echo echo = echoService.echo("I'm Client");
        System.out.println("RPC Server Echo: " + echo.getMessage() + "\n" +
                "RPC Service Time: " + echo.getHandleTime());
    }

}
