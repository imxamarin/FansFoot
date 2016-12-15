package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 07/12/16.
 */

public class NsfwRecycleViewAdapter extends RecyclerView.Adapter<NsfwRecycleViewAdapter.NsfwImageViewHolder> {


    String[] ImageTitle;
    String[] ImageAvaliable;
    String[] ImagePoints;
    String[] ImageComments;
    Context context;
    View mainView;
    NsfwImageViewHolder viewHolder;


    public NsfwRecycleViewAdapter(String[] imageTitle, String[] imageAvaliable, String[] imagePoints, String[] imageComments, Context context) {
        ImageTitle = imageTitle;
        ImageAvaliable = imageAvaliable;
        ImagePoints = imagePoints;
        ImageComments = imageComments;
        this.context = context;
    }

    @Override
    public NsfwImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.nfsw_ls_elements,parent,false);
        viewHolder = new NsfwImageViewHolder(mainView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NsfwImageViewHolder holder, int position) {
        holder.ImageDetail.setText(ImageTitle[position]);
        Glide
                .with(context)
                .load(ImageAvaliable[position])
                .centerCrop()
                .placeholder(R.drawable.post_img)
                .crossFade()
                .into(holder.ViewImage);
        holder.likesTextView.setText(ImagePoints[position]);
        holder.commentTextView.setText(ImageComments[position]);
    }

    @Override
    public int getItemCount() {
        return ImageTitle.length;
    }

    public static class NsfwImageViewHolder extends RecyclerView.ViewHolder {
        public TextView ImageDetail;
        public ImageView ViewImage;
        public TextView likesTextView;
        public TextView commentTextView;

        public ImageButton likeBtn;
        public ImageButton dislikeBtn;
        public ImageButton commentBtn;
        public NsfwImageViewHolder(View itemView) {
            super(itemView);
            ImageDetail = (TextView) itemView.findViewById(R.id.nfswTilteID);
            ViewImage = (ImageView) itemView.findViewById(R.id.nfswMainImage);
            likesTextView  = (TextView) itemView.findViewById(R.id.nfswImagePointsValue);
            commentTextView  = (TextView) itemView.findViewById(R.id.nfswImageCommentPoints);
            likeBtn = (ImageButton) itemView.findViewById(R.id.nfswlikebutton);
            dislikeBtn = (ImageButton) itemView.findViewById(R.id.nfswdislikebutton);
            commentBtn = (ImageButton) itemView.findViewById(R.id.nfswcommentbtn);



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
