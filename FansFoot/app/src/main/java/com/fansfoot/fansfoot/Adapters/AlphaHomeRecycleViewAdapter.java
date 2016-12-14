package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.R;

/**
 * Created by kafir on 10-Dec-16.
 */

public class AlphaHomeRecycleViewAdapter extends RecyclerView.Adapter<AlphaHomeRecycleViewAdapter.AlphaViewHolder> {
    String[] ImageTitle;
    String[] ImageAvaliable;

    String[] ImagePoints;
    String[] ImageComments;
    Context context;
    View mainView;
AlphaHomeRecycleViewAdapter.AlphaViewHolder viewHolders;
    public AlphaHomeRecycleViewAdapter(String[] imageTitle, String[] imageAvaliable, String[] imagePoints, String[] imageComments, Context context) {
        ImageTitle = imageTitle;
        ImageAvaliable = imageAvaliable;
        ImagePoints = imagePoints;
        ImageComments = imageComments;
        this.context = context;
    }

    @Override
    public AlphaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.alpha_ls_elements,parent,false);
        viewHolders = new AlphaHomeRecycleViewAdapter.AlphaViewHolder (mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(AlphaViewHolder holder, int position) {
        holder.ImageDetail.setText(ImageTitle[position]);
    //    holder.ViewImage.setImageResource(ImageAvaliable[position]);
        Glide
                .with(context)
                .load(ImageAvaliable[position])
                .centerCrop()
                .placeholder(R.drawable.back_icon)
                .crossFade()
                .into(holder.ViewImage);
        holder.likesTextView.setText(ImagePoints[position]);
        holder.commentTextView.setText(ImageComments[position]);
    }

    @Override
    public int getItemCount() {
        return ImageTitle.length;
    }

    public static class AlphaViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;


        public AlphaViewHolder(View itemView) {
            super(itemView);
            ImageDetail = (TextView) itemView.findViewById(R.id.AlphaTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.AlphaMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.AlphaImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.AlphaImageCommentPoints);

            likeBtn = (ImageButton) itemView.findViewById(R.id.Alphalikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Alphadislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Alphacommentbtn);





            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT).show();
                }
            });

            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT).show();
                }
            });

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,"Login Using Facebook",Snackbar.LENGTH_SHORT).show();
                }
            });

        }
    }
}
