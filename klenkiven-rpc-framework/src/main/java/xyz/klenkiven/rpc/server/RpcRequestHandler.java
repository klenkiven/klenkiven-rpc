package xyz.klenkiven.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.klenkiven.rpc.client.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 处理RPC传来的请求
 * @author KlenKiven
 */
public class RpcRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(RpcRequestHandler.class);

    /**
     * 处理方法的调用
     * @param request 来自客户端的请求
     * @param service 服务注册处发现服务
     * @return 返回响应结果
     */
    public RpcResponse<?> handle(RpcRequest request, Object service) {
        try {
            Object result = invokeServiceMethod(request, service);
            log.debug("服务 [{}].{} 调用成功", request.getServiceName(), request.getMethodName());
            return RpcResponse.success(result);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error("方法调用失败：{}", e.getMessage());
            return RpcResponse.fail();
        }
    }

    private Object invokeServiceMethod(RpcRequest request, Object service) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        return method.invoke(service, request.getParameters());
    }
}
