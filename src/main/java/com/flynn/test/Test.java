package com.flynn.test;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flynn.test.impl.RetryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


/**
 * 　　@Description:
 * 　　@author: v_fuxincao
 * 　　@version: 1.0
 * 　　@date: 2021-11-08 20:23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Test {

    @Autowired
    private RetryServiceImpl retryService;

    @org.junit.Test
    @Retryable(value = Exception.class, maxAttempts = 4, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public void doRetry() throws Exception {
        // 模拟测试
        if(true){
            log.info("重试查询下载链接: ==");
            throw new Exception("正在导出文件,请稍后");
        }
    }


    @org.junit.Test
    public void init(){
        Object object = null;
        JSONArray objects = JSONUtil.parseArray(object);
    }

    @org.junit.Test
    public void Test() throws IOException, InterruptedException {
        String url = "https://tool.bitefu.net/jiari/?d={}&info=2&back=json";

        String end = "2023-01-01";
        DateTime parse = DateUtil.parse(end);
        DateTime date = DateUtil.date();

        while (date.isBefore(parse)){
            String format = DateUtil.format(date, "yyyyMMdd");
            String urlStr = StrUtil.format(url, format);
            获取节假日(urlStr);
            Thread.sleep(100);
            System.out.println("urlStr = " + urlStr);
            date = DateUtil.offsetDay(date, 1);
            break;
        }
    }

    public static void 获取节假日(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("response = " + response.body().string());

    }





}