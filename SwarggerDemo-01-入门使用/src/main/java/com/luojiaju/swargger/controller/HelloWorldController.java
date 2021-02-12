package com.luojiaju.swargger.controller;

import com.luojiaju.swargger.model.UserModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String doHello(){
        return "helloWorld";
    }



    @PostMapping(value = "/user")
    public UserModel doUserQuery(){
        return new UserModel();
    }


    @ApiOperation("这是接口的方法描述：获取用户名称")
    @PostMapping(value = "/user2")
    public UserModel doUserQuery2(@ApiParam("用户名称") String name){
        return new UserModel();
    }



}