
package com.fansfoot.fansfoot.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YouTube {

    @SerializedName("post")
    @Expose
    private List<YoutubePost> post = null;

    public List<YoutubePost> getPost() {
        return post;
    }

    public void setPost(List<YoutubePost> post) {
        this.post = post;
    }

}
