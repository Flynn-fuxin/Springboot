package com.flynn.controller;


import cn.hutool.core.util.StrUtil;
import com.flynn.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 　　@Description:
 * 　　@author: v_fuxincao
 * 　　@version: 1.0
 * 　　@date: 2021-12-10 10:43
 */


@Slf4j
@Controller
@ResponseBody
public class DemoController {

    @Autowired
//    private UserService userService;

    @GetMapping
    public User get() {
        log.info("GET方法执行。。。");
        User user = new User();
        user.setUserId("shf");
        user.setUserName("张三");
        return user;
    }

    @GetMapping(value = "/{id}")
//    @ResponseBody
    public User get(@PathVariable String id) {
        log.info("GET..{}...方法执行。。。", id);
//        return userService.getById(id);
        return null;
    }

    @PostMapping
    public void post() {
        log.info("POST方法执行。。。");
    }

    @PutMapping
    public void put() {
        log.info("PUT方法执行。。。");
    }

    @DeleteMapping
    public void delete() {
        log.info("DELETE方法执行。。。");
    }

}
