package com.fansfoot.fansfoot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by xamarin on 05/12/16.
 */

public class VideoRecycleViewAdapter extends RecyclerView.Adapter<VideoRecycleViewAdapter.VideoViewHolder> {

    String[] VideoTitle;
    String[] VideoAvaliable;
    String[] VideoPoints;
    String[] VideoComments;
    Context context;
    View mainView;
    VideoRecycleViewAdapter.VideoViewHolder viewHolders;

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
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.VideoDetail.setText(VideoTitle[position]);
       holder.ViewVideo.setVideoPath(VideoAvaliable[position]);
        holder.likesTextView.setText(VideoPoints[position]);
        holder.commentTextView.setText(VideoComments[position]);
    }

    @Override
    public int getItemCount() {
        return VideoTitle.length;
    }

    public static class VideoViewHolder  extends RecyclerView.ViewHolder  {
        public TextView VideoDetail;
       public VideoView ViewVideo;
        public TextView likesTextView;
        public TextView commentTextView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            VideoDetail = (TextView) itemView.findViewById(R.id.VideoDetail);
          ViewVideo = (VideoView) itemView.findViewById(R.id.MainVideo);
            likesTextView = (TextView) itemView.findViewById(R.id.VideoPointsValue);
            commentTextView = (TextView) itemView.findViewById(R.id.VideoCommentPoints);
        }
    }
}
