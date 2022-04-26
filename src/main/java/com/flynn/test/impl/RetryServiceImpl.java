package com.flynn.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class RetryServiceImpl{

    private final AtomicInteger count = new AtomicInteger(1);


    @Retryable(value = Exception.class, maxAttempts = 4, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public void retry() {
        log.info("start to retry : " + count.getAndIncrement());

      int a = 1/0;
    }
 
    @Recover
    public void recover(RemoteAccessException t) {
        log.info("SampleRetryService.recover:{}", t.getClass().getName());
    }        
}