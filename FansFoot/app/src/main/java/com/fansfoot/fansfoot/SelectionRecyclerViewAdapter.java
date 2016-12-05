package com.fansfoot.fansfoot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xamarin on 05/12/16.
 */

public class SelectionRecyclerViewAdapter extends RecyclerView.Adapter<SelectionRecyclerViewAdapter.ViewHolder> {


    String[] SubjectValues;
    int[] ImageValues;

    Context context;
    View mainView;
    ViewHolder viewHolders;
    TextView textView;
    ImageView imageview;


    public SelectionRecyclerViewAdapter(String[] subjectValues, int[] imageValues, Context context) {
        SubjectValues = subjectValues;
        ImageValues = imageValues;
        this.context = context;
    }

    @Override
    public SelectionRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mainView = LayoutInflater.from(context).inflate(R.layout.sections_ls_elements,parent,false);
        viewHolders = new ViewHolder(mainView);
        return viewHolders;

    }

    @Override
    public void onBindViewHolder(SelectionRecyclerViewAdapter.ViewHolder holder, int position) {

       holder.textView.setText(SubjectValues[position]);
      holder.imageView.setImageResource(ImageValues[position]);
    }

    @Override
    public int getItemCount() {
        return SubjectValues.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       public TextView textView;
        public ImageView imageView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.TitleView);
            imageView = (ImageView) itemView.findViewById(R.id.ImgView);


        }
    }
}
