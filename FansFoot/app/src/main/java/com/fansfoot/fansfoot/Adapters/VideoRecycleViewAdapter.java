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

/**
 * Created by xamarin on 05/12/16.
 */

public class VideoRecycleViewAdapter extends RecyclerView.Adapter<VideoRecycleViewAdapter.VideoViewHolder>{

    String[] VideoTitle;
    String[] VideoAvaliable;
    String[] VideoPoints;
    String[] VideoComments;
    static Context context;
    View mainView;
    YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    VideoRecycleViewAdapter.VideoViewHolder viewHolders;
//    static YouTubePlayerSupportFragment youTubePlayerFragment;
    public VideoRecycleViewAdapter(String[] videoTitle, String[] videoAvaliable, String [] videoPoints, String[] videoComments, Context context) {
        VideoTitle = videoTitle;
        VideoAvaliable = videoAvaliable;
        VideoPoints = videoPoints;
        VideoComments = videoComments;
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
        holder.VideoDetail.setText(VideoTitle[position]);
//        youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
//        FragmentTransaction transaction = VideoPage.getChildFragment().beginTransaction();
//        transaction.add(holder.ViewVideo.getId(),youTubePlayerSupportFragment).addToBackStack(null).commit();
//        youTubePlayerSupportFragment.initialize("AIzaSyDKVkI5QRYt486jYFoKUW4npL0wt6dDGAo", new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                if (!b) {
//                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//                    youTubePlayer.loadVideo(VideoAvaliable[position]);
//                    youTubePlayer.play();
//
//                }else {
//                    Toast.makeText(context,"Thor", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//                String errorMessage = youTubeInitializationResult.toString();
//                Toast.makeText(context, errorMessage+""+position, Toast.LENGTH_LONG).show();
//            }
//        });



             String[] url = VideoAvaliable[position].split("=");
        String thumbnail = "http://img.youtube.com/vi/"+url[0]+"/0.jpg";
//
//        Glide.with(context).load(thumbnail).into(holder.ViewVideo);

        Glide
                .with(context)
                .load(thumbnail)
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.videotemp)
                .into(holder.YoutubeView);

//        holder.ViewVideo.setId(youTubePlayerSupportFragment.getId());
        holder.likesTextView.setText(VideoPoints[position]);
        holder.commentTextView.setText(VideoComments[position]);
    }

    @Override
    public int getItemCount() {
        return VideoTitle.length;
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
