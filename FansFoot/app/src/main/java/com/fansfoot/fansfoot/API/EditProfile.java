
package com.fansfoot.fansfoot.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfile {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
