package com.abin.springbootconsumer;

import com.abin.common.service.ArticleService;
import com.abin.rpcspringbootstarter.annotation.RpcReference;
import com.abin.rpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerExample {

    @RpcReference
    private ArticleService articleService;

    public void test() {
        String author = articleService.getAuthor();
        System.out.println(author);

        String content = articleService.getContent();
        System.out.println(content);
    }
}
