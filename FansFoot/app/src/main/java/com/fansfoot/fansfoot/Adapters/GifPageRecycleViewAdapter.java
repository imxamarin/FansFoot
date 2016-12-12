package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 07/12/16.
 */

public class GifPageRecycleViewAdapter extends RecyclerView.Adapter<GifPageRecycleViewAdapter.GifPageImageViewHolder> {


    String[] ImageTitle;
    int[] ImageAvaliable;
    String[] ImagePoints;
    String[] ImageComments;
    Context context;
    View mainView;
    GifPageRecycleViewAdapter.GifPageImageViewHolder viewHolder;

    public GifPageRecycleViewAdapter(String[] imageTitle, int[] imageAvaliable, String[] imagePoints, String[] imageComments,  Context context) {
        ImageTitle = imageTitle;
        ImageAvaliable = imageAvaliable;
        ImagePoints = imagePoints;
        ImageComments = imageComments;
        this.context = context;
    }

    @Override
    public GifPageImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.gif_ls_elements,parent,false);
        viewHolder = new GifPageRecycleViewAdapter.GifPageImageViewHolder (mainView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GifPageImageViewHolder holder, int position) {
        holder.ImageDetail.setText(ImageTitle[position]);
        holder.ViewImage.setImageResource(ImageAvaliable[position]);
        holder.likesTextView.setText(ImagePoints[position]);
        holder.commentTextView.setText(ImageComments[position]);
    }

    @Override
    public int getItemCount() {
        return ImageTitle.length;
    }

    public static class GifPageImageViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public GifPageImageViewHolder(View itemView) {
            super(itemView);
            ImageDetail = (TextView) itemView.findViewById(R.id.GifTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.GifMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.GifImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.GifImageCommentPoints);
        }
    }
}
