package xyz.klenkiven.rpc.server;

import xyz.klenkiven.rpc.constant.RpcConstantEnum;

import java.io.Serializable;

/**
 * RPC 结果响应实体
 * @author KlenKiven
 */
public class RpcResponse<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    private Integer code;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(RpcConstantEnum.RPC_SUCCESS.getCode());
        rpcResponse.setMessage(RpcConstantEnum.RPC_SUCCESS.getMessage());
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail() {
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(RpcConstantEnum.RPC_FAIL.getCode());
        rpcResponse.setMessage(RpcConstantEnum.RPC_FAIL.getMessage());
        return rpcResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
