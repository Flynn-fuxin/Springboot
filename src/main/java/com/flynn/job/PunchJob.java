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
    @Scheduled(cron = "0 10 9 ? * MON-FRI")
//    @Scheduled(cron = "0/10 * * * * ?")˚
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
            log.info(" ");

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

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        try {
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("http://om.weoa.com/Attendance/Save?type=begin&city=54&building=257&loginCode=637817224295108119&remark=&method=click")
                    .method("POST", body)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "0")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")
                    .addHeader("Origin", "http://om.weoa.com")
                    .addHeader("Referer", "http://om.weoa.com/Home")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .addHeader("Cookie", "WB_OM_LOGINTICKET=ixeZC%2fL3qbnsx4iZJ%2bzT9C5VrruNuKQfkry4pxY1L1MXMLNkLVdrkdXp%2fpnIo6JaSBtjfprkQqazWtzXyEQ3kdDKvl5F0JO45Y81E7%2fQuErGwfzQWFqSFZlcPyUxPpj8DkcQR087l%2b%2fx77JZdBCM%2bmLcWbuW3UDkxWCSgFN7bWN4vDoLJjiDH82%2f8aUkRyO40ii%2bSlPHtiyn69ySdQMRn03OKx0bd3iESMAhci%2f0mr8oZvbOKYhq%2f2QpA7zYlreBqllGXDUa1HtPumGWxNW3qJmU%2bhx6VAwPGk7thg376KfhZuBbd7qIM2zidO3d7%2fT932gUwTn%2fR%2b%2b9uFjoG%2bwBicxw5%2btlPE0Oi3TJLD63v4%2f6cRzRSf%2fD5kWZ%2bvMctvFuVUrQugOtfhf%2b7c%2fmlLL9wpmJlv3ip25c")
                    .build();

            Response response = client.newCall(request).execute();
            log.info("=== 上班打卡详情返回结果: === {}",response.body().string());

        } catch (Exception e) {
            log.error(" === 上班打卡失败 ===");
            e.printStackTrace();
        }
    }


}
