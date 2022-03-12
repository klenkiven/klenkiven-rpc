package xyz.klenkiven.rpc.registry.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.klenkiven.rpc.exception.RpcException;
import xyz.klenkiven.rpc.registry.ServiceRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册中心的实现
 * @author KlenKiven
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private final Map<String, Object> serviceRegistry = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(ServiceRegistry.class);

    @Override
    public synchronized void register(String serviceName, Object service) {
        if (serviceRegistry.containsKey(serviceName)) {
            return;
        }
        serviceRegistry.put(serviceName, service);
        log.debug("服务 [{}] 已经成功注册", serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceRegistry.get(serviceName);
        if (service == null) {
            log.error("服务 [{}] 找不到", serviceName);
            throw new RpcException("服务找不到");
        }
        return service;
    }
}
