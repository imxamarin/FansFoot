
package com.fansfoot.fansfoot.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FansfootServer {

    @SerializedName("post")
    @Expose
    private List<Post> post = null;

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

}
