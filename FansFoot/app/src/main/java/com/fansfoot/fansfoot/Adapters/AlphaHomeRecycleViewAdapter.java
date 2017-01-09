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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FBLike;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.DefaultActivities.CommentPage;
import com.fansfoot.fansfoot.DefaultActivities.PostPage;
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
 * Created by kafir on 10-Dec-16.
 */

public class AlphaHomeRecycleViewAdapter extends RecyclerView.Adapter<AlphaHomeRecycleViewAdapter.AlphaViewHolder> {
     List<Post> UrlList;
    Context context;
    View mainView;
    SharedPreferences sharedPreferencesBeta;
    String result;
    FBLike fblike;
    RequestQueue mRequestQueue;

AlphaHomeRecycleViewAdapter.AlphaViewHolder viewHolders;
    public AlphaHomeRecycleViewAdapter( Context context,List<Post> urlList) {
        UrlList = urlList;
        this.context = context;
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
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
//        holder.JumpToAlterFacebookLikeAPI(UrlList.get(position).getPostId());
//        holder.JumpToAlterFacebookDisLikeAPI(UrlList.get(position).getPostId());
        Picasso.with(context)
                .load(UrlList.get(position).getPic())
                .resize(UrlList.get(position).getWidth(), UrlList.get(position).getHeight())
                .centerCrop()
                .placeholder(R.drawable.post_img)
                .into(holder.ViewImage);
            holder.commentTextView.setText(UrlList.get(position).getComments().toString());
            holder.likesTextView.setText(UrlList.get(position).getTotalLike().toString());


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

                Log.d("like id",ModUrl);

                final boolean fb_status = FacebookStatus.CheckFbLogin();


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
                    if (!value) {


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

                Log.d("dislike id",ModUrl);
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
                                holder.likeBtn.setImageResource(R.drawable.like_icon);
                                holder.dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
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
        SharedPreferences sharedPreferencesBeta;
       FBLike fblike;
        public boolean likeResponce = true;
        public boolean DislikeResponce = true;

        public void JumpToFaceBookForLogin(){
            int x = getPosition();
            LikeTransition likeTransition=new LikeTransition();
            likeTransition.setPos(x);
            likeTransition.setFragName("profile");
            EventBus.getDefault().post(likeTransition);


        }

        public void JumpToAlterFacebookLikeAPI(String post_ID){
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

            Log.d("valuse", "" + ModUrl);

            JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ModUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("responceq",""+response.toString());
                    Gson _Gson = new Gson();
                    fblike = _Gson.fromJson(response.toString(), FBLike.class);
                    String xVal = fblike.getMsg();
                    if(xVal.equals("Already like")) {
                        likeBtn.setImageResource(R.drawable.like_icon_selected);
                    }else {
                        likeBtn.setImageResource(R.drawable.like_icon);
                    }
                   }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });
            mRequestQueue.add(_JsonObjectRequest);



        }


        public void JumpToAlterFacebookDisLikeAPI(String post_ID){

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
                    String xVal = fblike.getMsg();
                    if (xVal.equals("Already unlike")) {
                        dislikeBtn.setImageResource(R.drawable.dislike_icon_selected);
                    }else {
                        dislikeBtn.setImageResource(R.drawable.dislike_icon);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mRequestQueue.add(_JsonObjectRequest);

        }



        public boolean JumpIfAlreadayLiked(boolean value){
            if(value==true){
                Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                return false;
            }
        }

        public boolean JumpIfAlreadayDisLiked(boolean value){
            if(value==true){
                Toast.makeText(context, "Already Liked", Toast.LENGTH_SHORT).show();
                return true;
            }else {
                return false;
            }
        }

        public AlphaViewHolder(final View itemView) {

            super(itemView);

            sharedPreferences =MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
            sharedPreferencesBeta =MainActivity.getContext().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
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
            int xm = getPosition();


            ImageDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    String ImageURL  = UrlList.get(x).getPic();
                    String ImageTitle = UrlList.get(x).getTital();
                    String ImageFBURL = UrlList.get(x).getFbCommnetUrl();
                    Intent intent = new Intent(MainActivity.getContext(), PostPage.class);
                    intent.putExtra("ImageTitle", ImageTitle);
                    intent.putExtra("ImageURL", ImageURL);
                    intent.putExtra("ImageFBURL", ImageFBURL);
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
                        int heighter = UrlList.get(x).getHeight();
                        int widther = UrlList.get(x).getWidth();
                        Log.d("hola", ""+heighter+widther);
                        Intent intent = new Intent(MainActivity.getContext(), CommentPage.class);
                        intent.putExtra("ImageFBURL", ImageFBURL);
                        intent.putExtra("width",widther);
                        intent.putExtra("height",heighter);
                        MainActivity.getContext().startActivity(intent);

                    }else if(!value){

                        int x = getPosition();
                        String ImageFBURL = UrlList.get(x).getFbCommnetUrl();
                        int heighter = UrlList.get(x).getHeight();
                        int widther = UrlList.get(x).getWidth();
                        Log.d("Checkthat", " "+heighter+widther);
                        Intent intent = new Intent(MainActivity.getContext(), CommentPage.class);
                        intent.putExtra("ImageFBURL", ImageFBURL);
                        intent.putExtra("width",widther);
                        intent.putExtra("height",heighter);
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
    }
}
