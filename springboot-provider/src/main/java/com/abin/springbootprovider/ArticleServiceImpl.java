package com.abin.springbootprovider;

import com.abin.common.service.ArticleService;
import com.abin.rpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class ArticleServiceImpl implements ArticleService {
    @Override
    public String getAuthor() {
        return new String("ikun");
    }

    @Override
    public String getContent() {
        return "啊吧啊吧啊吧啊吧\n啊吧啊吧啊吧啊吧\n啊吧啊吧啊吧啊吧\n啊吧啊吧啊吧啊吧\n啊吧啊吧啊吧啊吧\n";
    }
}
