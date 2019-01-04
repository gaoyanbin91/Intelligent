package com.gao.intelligent.model;

import java.io.Serializable;

/**
 * Created by gaoyanbin on 2018/5/21.
 * 描述:登录返回Token
 */
public class LoginBean implements Serializable {


    /**
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiYXV0aCI6W10sImNyZWF0ZWQiOjE1MjgyNjk3OTU1NzgsImV4cCI6MTUyODg3NDU5NX0.di4WEdCnE1VmDm74jijx-3uwrNtsX1AAP5R8uoS4Z7Y_um0fotRqDadXSID4Jza_3MZKS-6Mp2rWJkjiKnFu3w
     * success : true
     * desc : 登陆成功！
     */

    private String token;
    private String success;
    private String desc;
    private String status;
    private String currentAuthority;
    private String type;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentAuthority(String currentAuthority) {
        this.currentAuthority = currentAuthority;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {

        return status;
    }

    public String getCurrentAuthority() {
        return currentAuthority;
    }

    public String getType() {
        return type;
    }

    public String getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
