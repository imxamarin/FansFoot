package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.internal.WebDialog;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FBLike;
import com.fansfoot.fansfoot.API.FacebookFansfoot;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.FansfootServer;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.DefaultActivities.PostPage;
import com.fansfoot.fansfoot.DefaultActivities.YoutubePlayerActivity;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by kafir on 10-Dec-16.
 */

public class AlphaHomeRecycleViewAdapter extends RecyclerView.Adapter<AlphaHomeRecycleViewAdapter.AlphaViewHolder> {
     List<Post> UrlList;
    Context context;
    View mainView;



AlphaHomeRecycleViewAdapter.AlphaViewHolder viewHolders;
    public AlphaHomeRecycleViewAdapter( Context context,List<Post> urlList) {
        UrlList = urlList;
        this.context = context;
    }

    @Override
    public AlphaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.alpha_ls_elements,parent,false);
        viewHolders = new AlphaHomeRecycleViewAdapter.AlphaViewHolder (mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(final AlphaViewHolder holder, final int position) {
        holder.ImageDetail.setText(UrlList.get(position).getTital());
        Glide
                .with(context)
                .load(UrlList.get(position).getPic())
                .fitCenter()
                .placeholder(R.drawable.post_img)
                .crossFade()
                .into(holder.ViewImage);
        holder.commentTextView.setText(UrlList.get(position).getComments().toString());
            holder.likesTextView.setText(UrlList.get(position).getTotalLike().toString());

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

    public class AlphaViewHolder extends RecyclerView.ViewHolder {
        RequestQueue mRequestQueue;
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
       FBLike fblike;

        public boolean likeResponce = true;
        public boolean DislikeResponce = true;

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

        public boolean JumpToAlterFacebookLikeAPI(String post_ID){
            String ModUrl = ConstServer._baseUrl+
                    ConstServer._type+
                    ConstServer._typePostLike+
                    ConstServer._ConCat+
                    ConstServer._PostID+
                    post_ID+
                    ConstServer._ConCat+
                    ConstServer._PostUserID+
                    sharedPreferences.getString("FbFFID",AccessToken.getCurrentAccessToken().getUserId())+
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
                    sharedPreferences.getString("FbFFID",AccessToken.getCurrentAccessToken().getUserId())+
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


        public AlphaViewHolder(final View itemView) {

            super(itemView);

            sharedPreferences =MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
            ImageDetail = (TextView) itemView.findViewById(R.id.AlphaTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.AlphaMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.AlphaImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.AlphaImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.Alphalikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Alphadislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Alphacommentbtn);

            ImageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    String ImageURL  = UrlList.get(x).getPic();
                    String ImageTitle = UrlList.get(x).getTital();
                    Intent intent = new Intent(MainActivity.getContext(), PostPage.class);
                    intent.putExtra("ImageTitle", ImageTitle);
                    intent.putExtra("ImageURL", ImageURL);
                    MainActivity.getContext().startActivity(intent);

                }
            });



        }
    }
}
