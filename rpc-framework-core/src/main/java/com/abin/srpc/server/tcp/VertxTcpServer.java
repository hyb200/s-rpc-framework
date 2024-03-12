package com.abin.srpc.server.tcp;

import com.abin.srpc.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        //  处理请求的逻辑，根据 requestData 构造响应数据并返回
        return "hello, tcp client".getBytes();
    }

    @Override
    public void init(int port) {
        Vertx vertx = Vertx.vertx();

        //  创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        //  处理请求
        server.connectHandler(socket -> {
            //  处理连接
            socket.handler(buffer -> {
                byte[] requestData = buffer.getBytes();
                //  自定义处理逻辑，解析请求、调用服务、构造响应等

                byte[] responseData = handleRequest(requestData);
                //  发送响应
                socket.write(Buffer.buffer(responseData));
            });
        });

        //  启动服务器并监听端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.out.println("Failed to start TCP server: " + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().init(8888);
    }
}
