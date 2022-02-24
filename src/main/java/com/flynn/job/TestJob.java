package com.flynn.job;

import com.flynn.test.impl.RetryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 　　@Description:
 * 　　@author: v_fuxincao
 * 　　@version: 1.0
 * 　　@date: 2022-01-18 11:11
 */
@Component
@EnableScheduling//可以在启动类上注解也可以在当前文件
@Slf4j
public class TestJob {
    @Autowired
    private RetryServiceImpl retryService;

    //    @Scheduled(cron = "0/10 * * * * ?")
    public void runfirst() throws Exception {
        retryService.retry();
    }

}