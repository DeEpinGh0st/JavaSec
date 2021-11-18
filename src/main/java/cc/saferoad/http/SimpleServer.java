package cc.saferoad.http;/*
@auther S0cke3t
@date 2021-11-18
*/

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        //创建一个HttpServer实例，并绑定到指定的IP地址和端口号
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8989), 0);

        //创建一个HttpContext，将路径为/myserver请求映射到MyHttpHandler处理器
        httpServer.createContext("/myserver", new MyHttpHandler());

        //设置服务器的线程池对象
        httpServer.setExecutor(newFixedThreadPool(10));

        //启动服务器
        httpServer.start();
    }
}
