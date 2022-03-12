package xyz.klenkiven.rpc.constant;

/**
 * RPC 调用相关常量枚举类
 * @author KlenKiven
 */
public enum RpcConstantEnum {
    RPC_SUCCESS(200, "RPC Success"),
    RPC_FAIL(500, "RPC Fail");

    private Integer code;
    private String message;

    RpcConstantEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
