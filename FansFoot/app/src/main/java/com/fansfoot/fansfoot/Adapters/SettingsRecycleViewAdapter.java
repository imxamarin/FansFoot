package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 05/12/16.
 */

public class SettingsRecycleViewAdapter extends RecyclerView.Adapter<SettingsRecycleViewAdapter.SettingViewHolder> {


    String[] SubjectValues;
    int[] ImageValues;

    Context context;
    View mainView;
    SettingViewHolder viewHolders;


    public SettingsRecycleViewAdapter(String[] subjectValues,int[] imageValues, Context context) {
        SubjectValues = subjectValues;
        ImageValues = imageValues;
        this.context = context;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.settings_ls_elements,parent,false);
        viewHolders = new SettingViewHolder(mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        holder.textView.setText(SubjectValues[position]);
       holder.imageBtn.setImageResource(ImageValues[position]);
    }

    @Override
    public int getItemCount() {
       return SubjectValues.length;
    }


    public static class SettingViewHolder extends RecyclerView.ViewHolder  {
        public TextView textView;
        public ImageButton imageBtn;
        public SettingViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.SettingMenuBar);
           imageBtn = (ImageButton) itemView.findViewById(R.id.SettingsImgBtn);
        }
    }
}
