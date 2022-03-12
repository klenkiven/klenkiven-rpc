package xyz.klenkiven.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPC 客户端调用代理
 * @author KlenKiven
 */
public class RpcClientProxy<T> implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(RpcClientProxy.class);

    private final String host;
    private final int port;
    private final String serviceName;

    private RpcClientProxy(String host, int port, String serviceName) {
        this.host = host;
        this.port = port;
        this.serviceName = serviceName;
    }

    public static <T> T getProxy(String host, int port, Class<T> serviceType) {
        return getProxy(host, port, serviceType, serviceType.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(String host, int port, Class<T> serviceType, String serviceName) {
        log.info("服务代理已创建：{}", serviceName);
        return (T) Proxy.newProxyInstance(
                serviceType.getClassLoader(),
                new Class[]{serviceType},
                new RpcClientProxy<>(host, port,serviceName)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setParameterTypes(method.getParameterTypes());
        request.setServiceName(serviceName);
        log.debug("服务代理请求服务 [{}] 的方法 [{}]", serviceName, method.getName());
        RpcClient rpcClient = RpcClient.getClient(host, port);
        return rpcClient.doRemoteProcedureCall(request);
    }
}
