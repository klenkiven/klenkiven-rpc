package xyz.klenkiven.rpc.example;

/**
 * 测试远程调用接口
 * @author KlenKiven
 */
public interface EchoService {

    /**
     * Test Interface
     * @return echo
     */
    Echo echo(String saySomething);

}
