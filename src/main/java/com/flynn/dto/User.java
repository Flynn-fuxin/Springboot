package com.flynn.dto;

import lombok.Data;

/**
 * 　　@Description:
 * 　　@author: v_fuxincao
 * 　　@version: 1.0
 * 　　@date: 2021-06-09 11:35
 */
public class User {

    public String userId;
    public String userName;
    public String age;

//    public User(String userId, String userName) {
//        this.userId = userId;
//        this.userName = userName;
////        this.age = age;
//    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "userId='" + userId + '\'' +
//                ", userName='" + userName + '\'' +
//                ", age='" + age + '\'' +
//                '}';
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void spark(){
        System.out.println("userId = " + userId);
    }
}