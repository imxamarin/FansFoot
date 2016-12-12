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
 * Created by kafir on 10-Dec-16.
 */

public class DeltaHomeRecycleViewAdapter extends RecyclerView.Adapter<DeltaHomeRecycleViewAdapter.DeltaViewHolder> {
    String[] ImageTitle;
    int[] ImageAvaliable;
    String[] ImagePoints;
    String[] ImageComments;
    Context context;
    View mainView;
    DeltaHomeRecycleViewAdapter.DeltaViewHolder viewHolders;
    public DeltaHomeRecycleViewAdapter(String[] imageTitle, int[] imageAvaliable, String[] imagePoints, String[] imageComments, Context context) {
        ImageTitle = imageTitle;
        ImageAvaliable = imageAvaliable;
        ImagePoints = imagePoints;
        ImageComments = imageComments;
        this.context = context;
    }

    @Override
    public DeltaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.delta_ls_elements,parent,false);
        viewHolders = new DeltaHomeRecycleViewAdapter.DeltaViewHolder (mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(DeltaViewHolder holder, int position) {
        holder.ImageDetail.setText(ImageTitle[position]);
        holder.ViewImage.setImageResource(ImageAvaliable[position]);
        holder.likesTextView.setText(ImagePoints[position]);
        holder.commentTextView.setText(ImageComments[position]);
    }

    @Override
    public int getItemCount() {
        return ImageTitle.length;
    }

    public static class DeltaViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;
        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;
        public DeltaViewHolder(View itemView) {
            super(itemView);
            ImageDetail = (TextView) itemView.findViewById(R.id.DeltaTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.DeltaMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.DeltaImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.DeltaImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.Deltalikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.Deltadislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.Deltacommentbtn);



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
