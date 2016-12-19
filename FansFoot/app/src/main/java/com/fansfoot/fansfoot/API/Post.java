
package com.fansfoot.fansfoot.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tital")
    @Expose
    private String tital;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
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
    private String uploadUser;
    @SerializedName("total_like ")
    @Expose
    private Integer totalLike;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("fbCommnetUrl")
    @Expose
    private String fbCommnetUrl;
    @SerializedName("post_url")
    @Expose
    private String postUrl;

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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getFbCommnetUrl() {
        return fbCommnetUrl;
    }

    public void setFbCommnetUrl(String fbCommnetUrl) {
        this.fbCommnetUrl = fbCommnetUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

}
