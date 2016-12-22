
package com.fansfoot.fansfoot.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBLike {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("tital")
    @Expose
    private String tital;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("date_added ")
    @Expose
    private String dateAdded;
    @SerializedName("Upload_user ")
    @Expose
    private Object uploadUser;
    @SerializedName("total_like ")
    @Expose
    private Integer totalLike;

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

    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(Object uploadUser) {
        this.uploadUser = uploadUser;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

}
