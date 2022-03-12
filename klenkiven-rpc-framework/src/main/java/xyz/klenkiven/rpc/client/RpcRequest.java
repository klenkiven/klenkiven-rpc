package xyz.klenkiven.rpc.client;

import java.io.Serializable;

/**
 * RPC服务请求实体
 * @author KlenKiven
 */
public class RpcRequest implements Serializable {
    public static final long serialVersionUID = 42L;

    private String serviceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }


    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
