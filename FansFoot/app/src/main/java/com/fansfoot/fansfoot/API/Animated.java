
package com.fansfoot.fansfoot.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Animated {

    @SerializedName("post")
    @Expose
    private List<GhostPost> post = null;

    public List<GhostPost> getPost() {
        return post;
    }

    public void setPost(List<GhostPost> post) {
        this.post = post;
    }

}
