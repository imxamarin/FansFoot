package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.fansfoot.fansfoot.DefaultActivities.CommentPage;
import com.fansfoot.fansfoot.DefaultActivities.PostPage;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.DefaultPages.LoginPage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.models.LikeTransition;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by xamarin on 07/12/16.
 */

public class MemesRecycleViewAdapter extends RecyclerView.Adapter<MemesRecycleViewAdapter.MemesImageViewHolder> {

    List<Post> UrlList;
    Context context;
    View mainView;
    MemesImageViewHolder viewHolder;
    SharedPreferences sharedPreferencesBeta;
    FBLike fblike;
    RequestQueue mRequestQueue;


    public MemesRecycleViewAdapter( Context context,List<Post> urlList) {
        UrlList = urlList;
        this.context = context;
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mRequestQueue != null) {
            mRequestQueue.stop();
        }
    }


    @Override
    public MemesImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.memes_ls_elements,parent,false);
        viewHolder = new MemesImageViewHolder (mainView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MemesImageViewHolder holder, final int position) {
        holder.ImageDetail.setText(UrlList.get(position).getTital());
        Picasso.with(context)
                .load(UrlList.get(position).getPic())
                .resize(UrlList.get(position).getWidth(), UrlList.get(position).getHeight())
                .placeholder(R.drawable.post_img)
                .into(holder.ViewImage);
        holder.likesTextView.setText(UrlList.get(position).getTotalLike().toString());
        holder.commentTextView.setText(UrlList.get(position).getComments().toString());
        sharedPreferencesBeta =context.getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String ModUrl = ConstServer._baseUrl +
                        ConstServer._type +
                        ConstServer._typePostLike +
                        ConstServer._ConCat +
                        ConstServer._PostID +
                        UrlList.get(position).getPostId() +
                        ConstServer._ConCat +
                        ConstServer._PostUserID +
                        sharedPreferencesBeta.getString("FbFFID", "") +
                        ConstServer._ConCat +
                        ConstServer.LikeStatus;

                boolean fb_status = FacebookStatus.CheckFbLogin();


                if (fb_status == true) {


                    JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            ModUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("responceq", "" + response.toString());
                            Gson _Gson = new Gson();
                            fblike = _Gson.fromJson(response.toString(), FBLike.class);
                            String xVal = fblike.getMsg();
                            if (xVal.equals("like")) {
                                holder.likeBtn.setImageResource(R.drawable.like_icon_selected);
                                holder.dislikeBtn.setImageResource(R.drawable.dislike_icon);
                                int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                                Log.d("value", "" + vals);
                                int m = vals + 1;
                                Log.d("val", "" + m);
                                holder.likesTextView.setText(m + "");

                            } else {
                                holder.likeBtn.setImageResource(R.drawable.like_icon_selected);
                                holder.dislikeBtn.setImageResource(R.drawable.dislike_icon);
                                Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Text", xVal);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    mRequestQueue.add(_JsonObjectRequest);


                } else {
                    boolean value = sharedPreferencesBeta.getString("FbFFID", "").isEmpty();
                    if(!value){
                        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                ModUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("responceq", "" + response.toString());
                                Gson _Gson = new Gson();
                                fblike = _Gson.fromJson(response.toString(), FBLike.class);
                                String xVal = fblike.getMsg();
                                if (xVal.equals("like")) {
                                    holder.likeBtn.setImageResource(R.drawable.like_icon_selected);
                                    holder.dislikeBtn.setImageResource(R.drawable.dislike_icon);
                                    int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                                    Log.d("value", "" + vals);
                                    int m = vals + 1;
                                    Log.d("val", "" + m);
                                    holder.likesTextView.setText(m + "");

                                } else {
                                    holder.likeBtn.setImageResource(R.drawable.like_icon_selected);
                                    holder.dislikeBtn.setImageResource(R.drawable.dislike_icon);
                                    Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
                                }
                                Log.d("Text", xVal);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        mRequestQueue.add(_JsonObjectRequest);
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(view, "Login using Facebook", Snackbar.LENGTH_SHORT)
                                .setAction("LOGIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        holder.JumpToFaceBookForLogin();
                                    }
                                });

                        snackbar.show();
                    }
                }
            }

        });

        holder.dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String ModUrl = ConstServer._baseUrl+
                        ConstServer._type+
                        ConstServer._typePostLike+
                        ConstServer._ConCat+
                        ConstServer._PostID+
                        UrlList.get(position).getPostId()+
                        ConstServer._ConCat+
                        ConstServer._PostUserID+
                        sharedPreferencesBeta.getString("FbFFID","")+
                        ConstServer._ConCat+
                        ConstServer.DisLikeStatus;


                final boolean fb_status = FacebookStatus.CheckFbLogin();
                if(fb_status == true) {
                    Log.d("valueMain", fb_status+"");

                    JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            ModUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("responceq",""+response.toString());
                            Gson _Gson = new Gson();
                            fblike = _Gson.fromJson(response.toString(), FBLike.class);
                            String xVal = fblike.getMsg();
                            if(xVal.equals("unlike")){
                                holder.dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
                                holder.likeBtn.setImageResource(R.drawable.like_icon);
                                int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                                Log.d("value", "" + vals);
                                if(vals > 0) {
                                    int m = vals - 1;
                                    Log.d("val", "" + m);
                                    holder.likesTextView.setText(m + "");
                                }

                            }else{
                                holder.dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
                                holder.likeBtn.setImageResource(R.drawable.like_icon);
                                Toast.makeText(context, "Already Disliked", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Text",xVal);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    mRequestQueue.add(_JsonObjectRequest);
                }else {
                    boolean value = sharedPreferencesBeta.getString("FbFFID", "").isEmpty();
                    if(!value){
                        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                ModUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("responceq", "" + response.toString());
                                Gson _Gson = new Gson();
                                fblike = _Gson.fromJson(response.toString(), FBLike.class);
                                String xVal = fblike.getMsg();
                                if (xVal.equals("unlike")) {
                                    holder.dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
                                    holder.likeBtn.setImageResource(R.drawable.like_icon);
                                    int vals = Integer.parseInt(holder.likesTextView.getText().toString());
                                    Log.d("value", "" + vals);
                                    if(vals > 0) {
                                        int m = vals - 1;
                                        Log.d("val", "" + m);
                                        holder.likesTextView.setText(m + "");
                                    }

                                } else {
                                    holder.dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
                                    holder.likeBtn.setImageResource(R.drawable.like_icon);
                                    Toast.makeText(context, "Already unliked", Toast.LENGTH_SHORT).show();
                                }
                                Log.d("Text", xVal);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        mRequestQueue.add(_JsonObjectRequest);
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(view, "Login using Facebook", Snackbar.LENGTH_SHORT)
                                .setAction("LOGIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        holder.JumpToFaceBookForLogin();
                                    }
                                });

                        snackbar.show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return UrlList.size();
    }

    public class MemesImageViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
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
        public MemesImageViewHolder(View itemView) {
            super(itemView);

            sharedPreferences =MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
            sharedPreferencesBeta =MainActivity.getContext().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
            if (mRequestQueue == null) {
                Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                mRequestQueue = new RequestQueue(cache, network);
                mRequestQueue.start();
            }
            ImageDetail = (TextView) itemView.findViewById(R.id.memesTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.memesMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.memesImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.memesImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.memeslikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.memesdislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.memescommentbtn);

            ImageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    String ImageURL  = UrlList.get(x).getPic();
                    String ImageTitle = UrlList.get(x).getTital();
                    String ImageFBURL = UrlList.get(x).getFbCommnetUrl();
                    int heighter = UrlList.get(x).getHeight();
                    int widther = UrlList.get(x).getWidth();
                    Intent intent = new Intent(MainActivity.getContext(), PostPage.class);
                    intent.putExtra("ImageTitle", ImageTitle);
                    intent.putExtra("ImageURL", ImageURL);
                    intent.putExtra("ImageFBURL", ImageFBURL);
                    intent.putExtra("width",widther);
                    intent.putExtra("height",heighter);
                    MainActivity.getContext().startActivity(intent);

                }
            });





            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean fb_status = FacebookStatus.CheckFbLogin();
                    boolean value = sharedPreferencesBeta.getString("FbFFID","").isEmpty();
                    if(fb_status == true)
                    {
                        int x = getPosition();
                        String ImageFBURL = UrlList.get(x).getFbCommnetUrl();
                        Intent intent = new Intent(MainActivity.getContext(), CommentPage.class);
                        intent.putExtra("ImageFBURL", ImageFBURL);
                        MainActivity.getContext().startActivity(intent);

                    }else if(!value){

                        int x = getPosition();
                        String ImageFBURL = UrlList.get(x).getFbCommnetUrl();
                        Intent intent = new Intent(MainActivity.getContext(), CommentPage.class);
                        intent.putExtra("ImageFBURL", ImageFBURL);
                        MainActivity.getContext().startActivity(intent);

                    }else {

                        Snackbar snackbar = Snackbar
                                .make(view,R.string.LoginFacebookSnak,Snackbar.LENGTH_SHORT)
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
            String userID = "";
            if(FacebookStatus.CheckFbLogin()){
                userID = AccessToken.getCurrentAccessToken().getUserId();
            }
            String ModUrl = ConstServer._baseUrl+
                    ConstServer._type+
                    ConstServer._typePostLike+
                    ConstServer._ConCat+
                    ConstServer._PostID+
                    post_ID+
                    ConstServer._ConCat+
                    ConstServer._PostUserID+
                    sharedPreferencesBeta.getString("FbFFID",userID)+
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
            String userID = "";
            if(FacebookStatus.CheckFbLogin()){
                userID = AccessToken.getCurrentAccessToken().getUserId();
            }
            String ModUrl = ConstServer._baseUrl+
                    ConstServer._type+
                    ConstServer._typePostLike+
                    ConstServer._ConCat+
                    ConstServer._PostID+
                    post_ID+
                    ConstServer._ConCat+
                    ConstServer._PostUserID+
                    sharedPreferencesBeta.getString("FbFFID",userID)+
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
            LikeTransition likeTransition=new LikeTransition();
            likeTransition.setPos(x);
            likeTransition.setFragName("profile");
            EventBus.getDefault().post(likeTransition);
        }
    }
}
