package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FBLike;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.API.YoutubePost;
import com.fansfoot.fansfoot.DefaultActivities.YoutubePlayerActivity;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.DefaultPages.HomePage;
import com.fansfoot.fansfoot.DefaultPages.VideoPage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by xamarin on 05/12/16.
 */

public class VideoRecycleViewAdapter extends RecyclerView.Adapter<VideoRecycleViewAdapter.VideoViewHolder>{

    List<YoutubePost> UrlList;
    static Context context;
    View mainView;
    YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    VideoRecycleViewAdapter.VideoViewHolder viewHolders;
//    static YouTubePlayerSupportFragment youTubePlayerFragment;
    public VideoRecycleViewAdapter(Context context,List<YoutubePost> urlList) {
        UrlList = urlList;
        this.context = context;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.video_ls_elements,parent,false);
        viewHolders = new VideoViewHolder(mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, final int position) {


        holder.VideoDetail.setText(UrlList.get(position).getTital());
        Glide
                .with(context)
                .load(UrlList.get(position).getVideoTumb())
                .fitCenter()
                .placeholder(R.drawable.videotemp)
                .crossFade()
                .into(holder.YoutubeView);
        holder.likesTextView.setText(UrlList.get(position).getTotalLike().toString());
        holder.commentTextView.setText(UrlList.get(position).getComments().toString());
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fb_status = FacebookStatus.CheckFbLogin();
                Log.d("valuse", "" + fb_status);
                if(fb_status == true) {
                    boolean val = holder.JumpToAlterFacebookLikeAPI(UrlList.get(position).getPostId());
                    if (val == true) {
                        holder.dislikeBtn.setEnabled(true);
                        int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                        Log.d("value", "" + vals);
                        int m = vals + 1;
                        Log.d("val", "" + m);
                        holder.likesTextView.setText(m + "");
                        view.setEnabled(false);
                    }}else {

                    Snackbar snackbar = Snackbar
                            .make(view,"Login using Facebook",Snackbar.LENGTH_SHORT)
                            .setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });

                    snackbar.show();
                }

            }
        });

        holder.dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean fb_status = FacebookStatus.CheckFbLogin();
                if(fb_status == true) {
                    boolean val = holder.JumpToAlterFacebookDisLikeAPI(UrlList.get(position).getPostId());
                    if (val == true) {
                        holder.likeBtn.setEnabled(true);
                        int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                        Log.d("value", "" + vals);
                        int m = vals - 1;
                        Log.d("val", "" + m);
                        holder.likesTextView.setText(m + "");
                        view.setEnabled(false);
                    }}else {

                    Snackbar snackbar = Snackbar
                            .make(view,"Login using Facebook",Snackbar.LENGTH_SHORT)
                            .setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });

                    snackbar.show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return UrlList.size();
    }



    public class VideoViewHolder  extends RecyclerView.ViewHolder  {
        public TextView VideoDetail;
        public ImageView YoutubeView;
        public ImageButton YoutubePlay;
     //  public RelativeLayout ViewVideo;
        public TextView likesTextView;
        public TextView commentTextView;
         public ImageButton likeBtn;
         public ImageButton dislikeBtn;
         public ImageButton commentBtn;
        SharedPreferences sharedPreferences;
        SharedPreferences sharedPreferencesBeta;
        FBLike fblike;
        public boolean likeResponce = true;
        public boolean DislikeResponce = true;
        RequestQueue mRequestQueue;
        public VideoViewHolder(View itemView) {
            super(itemView);
            sharedPreferences =MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
            sharedPreferencesBeta =MainActivity.getContext().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
            VideoDetail = (TextView) itemView.findViewById(R.id.VideoDetail);
            YoutubeView = (ImageView) itemView.findViewById(R.id.imgViewYoutube);
            YoutubePlay = (ImageButton) itemView.findViewById(R.id.btnViewYoutube);
            likesTextView = (TextView) itemView.findViewById(R.id.VideoPointsValue);
            commentTextView = (TextView) itemView.findViewById(R.id.VideoCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.Videolikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Videodislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Videocommentbtn);
            YoutubeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    String imageId = UrlList.get(x).getPic();
                    String TextId = UrlList.get(x).getTital();
                }
            });

            YoutubePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getPosition();

                    String[] url = UrlList.get(position).getVideoTumb().split("vi/");
                    String value = url[1];
                    String[] videoLink = value.split("/0.jpg");
                    String YoutubeLink = videoLink[0];
                    Intent intent = new Intent(MainActivity.getContext(), YoutubePlayerActivity.class);
                    intent.putExtra("VideoTitle", YoutubeLink);
                    MainActivity.getContext().startActivity(intent);

                }
            });





            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean fb_status = FacebookStatus.CheckFbLogin();

                    if(fb_status == true)
                    {
                        Snackbar snackbar = Snackbar
                                .make(view,"Whatsup",Snackbar.LENGTH_SHORT)
                                .setAction("LOGIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JumpToFaceBookForLogin();
                                    }
                                });

                        snackbar.show();
                    }else {

                        Snackbar snackbar = Snackbar
                                .make(view,"Login using Facebook",Snackbar.LENGTH_SHORT)
                                .setAction("LOGIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JumpToFaceBookForLogin();
                                    }
                                });

                        snackbar.show();
                    }

                }
            });

        }


        public boolean JumpToAlterFacebookLikeAPI(String post_ID){
            String ModUrl = ConstServer._baseUrl+
                    ConstServer._type+
                    ConstServer._typePostLike+
                    ConstServer._ConCat+
                    ConstServer._PostID+
                    post_ID+
                    ConstServer._ConCat+
                    ConstServer._PostUserID+
                    sharedPreferencesBeta.getString("FbFFID", AccessToken.getCurrentAccessToken().getUserId())+
                    ConstServer._ConCat+
                    ConstServer.LikeStatus;


            JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ModUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson _Gson = new Gson();
                    fblike = _Gson.fromJson(response.toString(), FBLike.class);
                    int  x = fblike.getStatus();
                    if (x==1){
                        likeResponce = true;
                    }
                    Log.d("responceq",""+likeResponce);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    likeResponce = false;
                    Log.d("eresponce",""+likeResponce);
                }
            });
            mRequestQueue.add(_JsonObjectRequest);
            Log.d("responceq",""+likeResponce);
            return likeResponce;

        }


        public boolean JumpToAlterFacebookDisLikeAPI(String post_ID){
            String ModUrl = ConstServer._baseUrl+
                    ConstServer._type+
                    ConstServer._typePostLike+
                    ConstServer._ConCat+
                    ConstServer._PostID+
                    post_ID+
                    ConstServer._ConCat+
                    ConstServer._PostUserID+
                    sharedPreferencesBeta.getString("FbFFID",AccessToken.getCurrentAccessToken().getUserId())+
                    ConstServer._ConCat+
                    ConstServer.DisLikeStatus;

            Log.d("Makes",ModUrl);
            JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ModUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Makes",response.toString());
                    Gson _Gson = new Gson();
                    fblike = _Gson.fromJson(response.toString(), FBLike.class);
                    int x = fblike.getStatus();
                    if(x==1){
                        DislikeResponce = true;
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    DislikeResponce = false;
                }
            });
            mRequestQueue.add(_JsonObjectRequest);
            return DislikeResponce;
        }

        public void JumpToFaceBookForLogin(){
            int x = getPosition();
            FragmentTransaction fragmentTransaction;
            FragmentManager manager = MainActivity.getBaseFragmentManager();
            manager.popBackStackImmediate();
            fragmentTransaction = manager.beginTransaction();
            FbLikePage fbLikePage = new FbLikePage();
            manager.popBackStackImmediate();
            fragmentTransaction.replace(R.id.frag,fbLikePage);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
