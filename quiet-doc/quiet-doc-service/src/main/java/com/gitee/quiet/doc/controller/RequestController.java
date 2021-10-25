package com.gitee.quiet.doc.controller;

import com.gitee.quiet.service.result.Result;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {
    
    private final OkHttpClient client = new OkHttpClient();
    
    public Result<Object> get() {
        return null;
    }
}
