package xyz.klenkiven.rpc.service;

import xyz.klenkiven.rpc.example.Echo;
import xyz.klenkiven.rpc.example.EchoService;

import java.util.Date;

/**
 * 公共服务的实现类
 */
public class EchoServiceImpl implements EchoService {

    @Override
    public Echo echo(String saySomething) {
        Echo echo = new Echo();
        echo.setMessage("Server: " + saySomething);
        echo.setHandleTime(new Date());
        return echo;
    }
}
