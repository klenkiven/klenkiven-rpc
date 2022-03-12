package xyz.klenkiven.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.klenkiven.rpc.constant.RpcConstantEnum;
import xyz.klenkiven.rpc.exception.RpcException;
import xyz.klenkiven.rpc.server.RpcResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {

    private final Socket socket;
    private final Logger log = LoggerFactory.getLogger(RpcClient.class);

    private RpcClient(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            log.error("Socket Create Error: {}", e.getMessage());
            throw new RpcException("服务调用失败：" + e.getMessage());
        }
    }

    public static RpcClient getClient(String host, int port) {
        return new RpcClient(host, port);
    }

    /**
     * 实现远程调用接口
     * @param request 远程调用请求
     * @return 远程调用结果
     */
    public Object doRemoteProcedureCall(RpcRequest request) {
        try(this.socket) {
            // 发生远程调用
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // 获取远程调用结果并解析
            RpcResponse<?> response = (RpcResponse<?>) objectInputStream.readObject();
            if (response == null) {
                log.error("服务端返回空对象，服务 [{}] 调用失败", request.getServiceName());
                throw new RpcException("服务调用失败：" + request.getServiceName());
            } else if (response.getCode() == null || response.getCode().equals(RpcConstantEnum.RPC_SUCCESS.getCode())) {
                log.error("服务 [{}] 调用失败, 错误码：{}，错误：{}",
                        request.getServiceName(),
                        response.getCode(), response.getMessage());
            }

            // 关闭资源
            objectOutputStream.close();
            objectInputStream.close();
            return response.getData();
        } catch (IOException e) {
            log.error("Socket Error: {}", e.getMessage());
            throw new RpcException("服务调用失败：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("服务调用失败: {}", e.getMessage());
            throw new RpcException("服务调用失败：" + e.getMessage());
        }
    }

}
