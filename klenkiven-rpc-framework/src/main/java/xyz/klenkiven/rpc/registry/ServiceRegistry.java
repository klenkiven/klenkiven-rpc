package xyz.klenkiven.rpc.registry;

import java.util.Locale;

/**
 * 服务注册
 * @author KlenKiven
 */
public interface ServiceRegistry {

    /**
     * 首字母小写注册为服务的名字
     * @param service 服务名
     */
    default <T> void register(T service, Class<?> serviceType) {
        register(serviceType.getSimpleName(), service);
    }

    /**
     * 服务注册接口，注册服务端的服务
     * @param service 服务
     * @param <T> 服务接口类型
     */
    <T> void register(String serviceName, T service);

    /**
     * 根据服务的类型获取服务
     * @param serviceName 服务名称
     * @return 返回服务实例对象
     */
    Object getService(String serviceName);
}
