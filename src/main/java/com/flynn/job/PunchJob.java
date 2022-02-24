package com.flynn.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;

@Component
@EnableScheduling//可以在启动类上注解也可以在当前文件
@Slf4j
public class PunchJob {

    @Autowired
    private RestTemplate restTemplate;

    // 周一到周五 早上09:15 运行
//    @Scheduled(cron = "0 10 9 ? * MON-FRI")
//    @Scheduled(cron = "0/2 * * * * ?")、
    public void demoTimer() throws Exception {

        //打卡
        punch();
    }


    // 周一到周五 早上09:15 运行
    @Scheduled(cron = "0 45 18 ? * MON-FRI")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void demoTimerOut() throws Exception {

        //签出
        checkOut();
    }

    private void checkOut() throws InterruptedException {
        Date date = new Date();
        int i = RandomUtil.randomInt(15);
        log.info("=== 下班当前时间: {}===", DateUtil.formatDateTime(date));
        log.info("=== 下班随机数: {} ===", i);
        DateTime dateTime = DateUtil.offsetMinute(date, i);

        //do Something
        log.info("=== 下班打卡时间: {} ===", DateUtil.formatDateTime(dateTime));
        Thread.sleep(i*1000*60);
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("http://om.weoa.com/Attendance/Save?type=end&city=54&building=257&loginCode=637813154100017081&remark=&method=click")
                    .method("POST", body)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "0")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36")
                    .addHeader("Origin", "http://om.weoa.com")
                    .addHeader("Referer", "http://om.weoa.com/Home")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Cookie", "WB_OM_LOGINTICKET=ixeZC%2fL3qbnsx4iZJ%2bzT9C5VrruNuKQfkry4pxY1L1MXMLNkLVdrkdXp%2fpnIo6JaSBtjfprkQqazWtzXyEQ3kdDKvl5F0JO45Y81E7%2fQuErGwfzQWFqSFZlcPyUxPpj8DkcQR087l%2b%2fx77JZdBCM%2bmLcWbuW3UDkxWCSgFN7bWN0zusHBIk5nV5hkax%2bfW7DSinoxxp%2bT7Y4%2b8oN1CnjRxepK0h7U3t78%2fTZDY8%2fRWztRQ0a7012lJHsulxeQKxNNl6Hb7JqJxv9FaNqFHHiGcwsyDURTc5cMkuzhoR6yOE6DjeLXxm8ZdVBnB%2bUqDjiFtemEioin%2fSXa97tWdJdqn1a78Q9eN%2buhHlB8rIXOp9a5P3S9ZnI2MijCIUXml8dvD%2fej9bgvnOdQoBoV0xv3fi8AdPmy1%2b8")
                    .build();
            Response response = client.newCall(request).execute();
            log.info("=== 下班返回结果: === {}", response.body().string());
        } catch (Exception e) {
            log.error(" === 下班打卡失败 ===");
            e.printStackTrace();
        }
    }

    private void punch() throws InterruptedException {
        Date date = new Date();
        int i = RandomUtil.randomInt(15);
        log.info("=== 上班当前时间: {}===", DateUtil.formatDateTime(date));
        log.info("=== 上班随机数: {} ===", i);
        DateTime dateTime = DateUtil.offsetMinute(date, i);

        //do Something
        log.info("=== 上班打卡时间: {} ===", DateUtil.formatDateTime(dateTime));
        Thread.sleep(i*1000*60);

        OkHttpClient client = new OkHttpClient();

        try {
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
            Request request = new Request.Builder()
                    .url("http://om.weoa.com/Attendance/Save?type=begin&city=54&building=206&loginCode=637734291285871168&remark=&method=click")
                    .post(RequestBody.create("", mediaType))
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "0")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                    .addHeader("Origin", "http://om.weoa.com")
                    .addHeader("Referer", "http://om.weoa.com/Home")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Cookie", "WB_OM_LOGINTICKET=ixeZC%2fL3qbl2KyNicif6NVyjjFMw%2fLttA4lj9B0Q%2brdiViQHqzjkUwBS%2bM3XZ6njjDxSv%2bdKEg5tnHjwU7BBI6kjQ6eX3cYB%2buYxdsnbyTvT8mvPeKBgt%2f3vrEnBswIu8gYoAAWVRHP0YpgIJzFByDzOIlnwjk0r4a3uho9Ra6ecV2ZtD5Fye5cR%2bnmkQ2GFfe0Z20RGc4%2f6qZ%2fxsDUSDxS6w0QtYfUtyqbHPEHkTTuRnJj46c2pW2rS0RlMgqEINNsLnWuCtP5ytN9iR%2fK0E3ZX0e0h9Bjd0%2fvnQmN%2f0ajhONS%2bxUChNLVUsfJfJtui3gD1D6vNZD52lcg%2fQDAXp%2bFTqB1aCkEv6MU%2f%2bwiwBtrdpVrwMikBsPUe%2bQH9xHWp4MZbPMJdXDBzY0sm%2fhL7DrPy7vUXdYIs")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "c511e6d1-15fb-4814-83e5-9292ff44c0ea")
                    .build();

            Response response = client.newCall(request).execute();
            log.info("=== 上班打卡详情返回结果: === {}",response.body().string());
//            log.info("=== 打卡详情返回结果: === {}");
        } catch (Exception e) {
            log.error(" === 上班打卡失败 ===");
            e.printStackTrace();
        }
    }


}
