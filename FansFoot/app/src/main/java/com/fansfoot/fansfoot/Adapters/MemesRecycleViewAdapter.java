package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 07/12/16.
 */

public class MemesRecycleViewAdapter extends RecyclerView.Adapter<MemesRecycleViewAdapter.MemesImageViewHolder> {


    String[] ImageTitle;
    int[] ImageAvaliable;
    String[] ImagePoints;
    String[] ImageComments;
    Context context;
    View mainView;
    MemesImageViewHolder viewHolder;


    public MemesRecycleViewAdapter(String[] imageTitle, int[] imageAvaliable, String[] imagePoints, String[] imageComments, Context context) {
        ImageTitle = imageTitle;
        ImageAvaliable = imageAvaliable;
        ImagePoints = imagePoints;
        ImageComments = imageComments;

        this.context = context;
    }

    @Override
    public MemesImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.memes_ls_elements,parent,false);
        viewHolder = new MemesImageViewHolder (mainView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemesImageViewHolder holder, int position) {
        holder.ImageDetail.setText(ImageTitle[position]);
        holder.ViewImage.setImageResource(ImageAvaliable[position]);
        holder.likesTextView.setText(ImagePoints[position]);
        holder.commentTextView.setText(ImageComments[position]);
    }

    @Override
    public int getItemCount() {
        return ImageTitle.length;
    }

    public static class MemesImageViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;
        public MemesImageViewHolder(View itemView) {
            super(itemView);
            ImageDetail = (TextView) itemView.findViewById(R.id.memesTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.memesMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.memesImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.memesImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.memeslikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.memesdislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.memescommentbtn);



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
