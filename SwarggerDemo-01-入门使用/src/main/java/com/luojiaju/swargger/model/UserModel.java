package com.luojiaju.swargger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel("这是实体类")
public class UserModel {
    @ApiModelProperty("这是实体类的属性注释")
    private String username;
    @ApiModelProperty("这是实体类的属性注释")
    private String password;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
