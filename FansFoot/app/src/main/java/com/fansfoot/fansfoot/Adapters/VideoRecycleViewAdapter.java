package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
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

import com.bumptech.glide.Glide;
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
    public void onBindViewHolder(VideoViewHolder holder, final int position) {

             String[] url = UrlList.get(position).getPic().split("=");
        String thumbnail = "http://img.youtube.com/vi/"+url[0]+"/0.jpg";

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

    }

    @Override
    public int getItemCount() {
        return UrlList.size();
    }



    public static class VideoViewHolder  extends RecyclerView.ViewHolder  {
        public TextView VideoDetail;
        public ImageView YoutubeView;
        public ImageButton YoutubePlay;
     //  public RelativeLayout ViewVideo;
        public TextView likesTextView;
        public TextView commentTextView;
         public ImageButton likeBtn;
         public ImageButton dislikeBtn;
         public ImageButton commentBtn;
        public VideoViewHolder(View itemView) {
            super(itemView);
            VideoDetail = (TextView) itemView.findViewById(R.id.VideoDetail);
            YoutubeView = (ImageView) itemView.findViewById(R.id.imgViewYoutube);
            YoutubePlay = (ImageButton) itemView.findViewById(R.id.btnViewYoutube);
            likesTextView = (TextView) itemView.findViewById(R.id.VideoPointsValue);
            commentTextView = (TextView) itemView.findViewById(R.id.VideoCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.Videolikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Videodislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Videocommentbtn);

            YoutubePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.getContext(), YoutubePlayerActivity.class);
                    MainActivity.getContext().startActivity(intent);

                }
            });


            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT)
                            .setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JumpToFaceBookForLogin();
                                }
                            });

                    snackbar.show();
                }
            });

            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT)
                            .setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JumpToFaceBookForLogin();
                                }
                            });

                    snackbar.show();
                }
            });

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT)
                            .setAction("LOGIN", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JumpToFaceBookForLogin();
                                }
                            });

                    snackbar.show();
                }
            });

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
