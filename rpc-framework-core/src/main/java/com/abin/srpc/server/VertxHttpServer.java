package com.abin.srpc.server;

import com.abin.srpc.server.HttpServer;
import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {

    public void init(int port) {
        //  获取 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        //  创建 Http 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //  处理调用请求
        server.requestHandler(new HttpServerHandler());

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is listening on port " + port);
            } else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });
    }
}
