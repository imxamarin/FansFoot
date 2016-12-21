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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.internal.WebDialog;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.Post;
import com.fansfoot.fansfoot.DefaultActivities.PostPage;
import com.fansfoot.fansfoot.DefaultActivities.YoutubePlayerActivity;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

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
    public void onBindViewHolder(final AlphaViewHolder holder, int position) {
        holder.ImageDetail.setText(UrlList.get(position).getTital());
        Glide
                .with(context)
                .load(UrlList.get(position).getPic())
                .fitCenter()
                .placeholder(R.drawable.post_img)
                .crossFade()
                .into(holder.ViewImage);
        holder.likesTextView.setText(UrlList.get(position).getTotalLike().toString());
        holder.commentTextView.setText(UrlList.get(position).getComments().toString());
    }

    @Override
    public int getItemCount() {
        return UrlList.size();
    }

    public class AlphaViewHolder extends RecyclerView.ViewHolder {

        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
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

        public void JumpToAlterFacebookAPI(){
            String ModUrl = ConstServer._baseUrl+
                    ConstServer.Register_type+
                    ConstServer.Register_Type_Facebook+
                    ConstServer._ConCat+
                    ConstServer.Facebook_ID+
                    sharedPreferences.getString("FbID","339322503127553")+
                    ConstServer._ConCat+
                    ConstServer.Facebook_UserName+
                    sharedPreferences.getString("FbName","Raj")+
                    ConstServer._ConCat+
                    ConstServer.Facebook_EmailID+sharedPreferences.getString("Fbemail","amit.verma@trigma.in")+
                    ConstServer._ConCat+
                    ConstServer.Facebook_profilePic+sharedPreferences.getString("Fbpicture","https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/15590483_339385113121292_4884085331937605536_n.jpg?oh=7fc509aa2ff6e22729678893bc82c158&oe=58F5283F")+
                    ConstServer._ConCat+
                    ConstServer._deviceToken+"123"+
                    ConstServer._device_type;
            Log.d("newFBI",ModUrl);
        }


      /*  public void DoTheFBComment(){

            Bundle params = new Bundle();
            params.putString("name", vol_name);
            params.putString("caption",
                    "\u00A9 2015 Pocket Toons, All Rights Reserved");

            params.putString("description",
                    "Redefining the art of storytelling");
            params.putString("link",
                    "https://play.google.com/store/apps/details?id=com.pockettoons");
            params.putString("picture", banner);

            WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
                    FrameZoomTechnologyTest.this,
                    Session.getActiveSession(), params))
                    .setOnCompleteListener(new OnCompleteListener() {

                        public void onComplete(Bundle values,
                                               FacebookException error) {
                            if (error == null) {

                                final String postId = values
                                        .getString("post_id");
                                if (postId != null) {

                                    Toast.makeText(getApplicationContext(),
                                            "Posted successfully",
                                            Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(

                                            getApplicationContext(),
                                            "Publish cancelled",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else if (error instanceof FacebookOperationCanceledException) {

                                Toast.makeText(

                                        getApplicationContext(),
                                        "Publish cancelled",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(

                                        getApplicationContext(),
                                        "Error posting story",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }).build();
            feedDialog.show();
        }*/


        public AlphaViewHolder(final View itemView) {

            super(itemView);

            sharedPreferences =MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
            ImageDetail = (TextView) itemView.findViewById(R.id.AlphaTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.AlphaMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.AlphaImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.AlphaImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.Alphalikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Alphadislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Alphacommentbtn);
           // alphaPb = (ProgressBar) itemView.findViewById(R.id.Alphaprogress);


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


            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean fb_status = FacebookStatus.CheckFbLogin();

                    if(fb_status == true)
                    {
                        JumpToAlterFacebookAPI();


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

            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean fb_status = FacebookStatus.CheckFbLogin();

                    if(fb_status == true)
                    {
                        String ModUrl = ConstServer._baseUrl+
                                ConstServer.Register_type+
                                ConstServer.Register_Type_Facebook+
                                ConstServer._ConCat+
                                ConstServer.Facebook_ID+
                                AccessToken.getCurrentAccessToken().getUserId()+
                                ConstServer._ConCat+
                                ConstServer.Facebook_UserName+ AccessToken.getCurrentAccessToken().getUserId()+
                                ConstServer._ConCat+
                                ConstServer._deviceToken+"123456"+
                                ConstServer._ConCat+
                                ConstServer._device_type+
                                ConstServer._ConCat+
                                ConstServer._USERID+"123";


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
    }
}
