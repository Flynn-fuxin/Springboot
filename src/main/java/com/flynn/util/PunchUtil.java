package com.flynn.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.flynn.dto.punch.Datum;
import com.flynn.dto.punch.ResponseData;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PunchUtil {

    @Autowired
    private RestTemplate restTemplate;


    //false 打卡  true 不打卡
    public static Boolean extracted() {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("2022-04-01");
        arrayList.add("2022-04-02");

        Date date = new Date();

        String s = DateUtil.formatDate(date);
//        String s = "2022-04-01";
//        String s = "2022-04-02";
        System.out.println("s = " + s);

        List<String> collect = arrayList.stream().filter(it -> it.equals(s)).collect(Collectors.<String>toList());
        return !CollUtil.isEmpty(collect);
    }



    private boolean isPunch() throws Exception {
        boolean dayPunch;
        //用RestTemplate请求第三方接口
        SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
        reqfac.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.153.103", 3128)));
        this.restTemplate.setRequestFactory(reqfac);
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity("http://1.12.244.29:8080/getPunchStatus", Boolean.class);
        log.info("=== isPunch返回值: {}===", responseEntity);
        dayPunch = responseEntity.getBody();
        log.info("=== dayPunch: {}===", dayPunch);
        return dayPunch;
    }

    private boolean queryPunch() throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"aoData\"\r\n\r\n[\n  {\n    \"name\": \"draw\",\n    \"value\": 1\n  },\n  {\n    \"name\": \"columns\",\n    \"value\": [\n      {\n        \"data\": \"BeginDateWeek\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"BeginDate\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"BeginIP\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"BeginRemark\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"EndDate\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"EndIP\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"EndRemark\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      },\n      {\n        \"data\": \"Time\",\n        \"name\": \"\",\n        \"searchable\": true,\n        \"orderable\": false,\n        \"search\": {\n          \"value\": \"\",\n          \"regex\": false\n        }\n      }\n    ]\n  },\n  {\n    \"name\": \"order\",\n    \"value\": []\n  },\n  {\n    \"name\": \"start\",\n    \"value\": 0\n  },\n  {\n    \"name\": \"length\",\n    \"value\": 20\n  },\n  {\n    \"name\": \"search\",\n    \"value\": {\n      \"value\": \"\",\n      \"regex\": false\n    }\n  },\n  {\n    \"name\": \"week\",\n    \"value\": \"0\"\n  }\n]\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("http://om.weoa.com/Attendance/List")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .addHeader("Origin", "http://om.weoa.com")
                .addHeader("Referer", "http://om.weoa.com/Home")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", "WB_OM_LOGINTICKET=ixeZC%2fL3qbl2KyNicif6NVyjjFMw%2fLttA4lj9B0Q%2brdiViQHqzjkUwBS%2bM3XZ6njjDxSv%2bdKEg5tnHjwU7BBI6kjQ6eX3cYB%2buYxdsnbyTvT8mvPeKBgt%2f3vrEnBswIu8gYoAAWVRHP0YpgIJzFByDzOIlnwjk0r4a3uho9Ra6dAO%2f5qKMel1IcGoKDW%2bKVSkvdfKUECkO%2f%2bhvo2OKHNzWBbirpMwLtHDsfwQFYUpL29ubgdsjjSPWXRhZsszt22h0l0Dm6kpK1bPx2N7oeFDKrcQOUv0hD4hkO408lNEcb2MO%2bg9woxeWJY8G70vjVGRioeoR7GksocxliJ39ZMxWBxk9YlI%2f2sUFgnZ3ZUapvYYmjNHeavcdNoM5mUfKsD6dp66bF51qASV2VZffMMXKuBUmkrkh7X")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "d385a8d4-9e34-4521-b1ae-e2a653f56262")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("=== 时候打卡返回结果: === {}", responseBody);

        String beginDate = null;
        try {
            ResponseData responseData = JSONUtil.toBean(responseBody, ResponseData.class);
            Datum datum = responseData.getData().get(0);
            beginDate = datum.getBeginDate();
        } catch (Exception e) {
            log.error("=== 解析返回值报错,可能未打卡 ===", e);
            e.printStackTrace();
            return false;
        }
        if (!ObjectUtil.isEmpty(beginDate)) {
            System.out.println("beginDate = " + beginDate);
            log.info("=== 已经打卡,不需要打卡了 ===");
            return true;
        }
        return false;
    }

}
