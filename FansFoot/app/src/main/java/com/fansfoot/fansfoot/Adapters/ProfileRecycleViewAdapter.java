package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 05/12/16.
 */

public class ProfileRecycleViewAdapter extends RecyclerView.Adapter<ProfileRecycleViewAdapter.ProfileViewHolder>  {

    String[] ProfileDetail;
    String[] ProfileValues;
    Context context;
    View mainView;
    ProfileViewHolder viewHolders;

    public ProfileRecycleViewAdapter(String[] profileDetail, String[] profileValues, Context context) {
        ProfileDetail = profileDetail;
        ProfileValues = profileValues;
        this.context = context;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainView = LayoutInflater.from(context).inflate(R.layout.profile_ls_elements,parent,false);
        viewHolders = new ProfileViewHolder(mainView);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        holder.textViewDetails.setText(ProfileDetail[position]);
        holder.EdittextViewValues.setText(ProfileValues[position]);
    }

    @Override
    public int getItemCount() {
        return ProfileDetail.length;
    }

    public static class ProfileViewHolder  extends RecyclerView.ViewHolder {
        public TextView textViewDetails;
        public EditText EdittextViewValues;
        public ProfileViewHolder(View itemView) {
            super(itemView);
            textViewDetails = (TextView) itemView.findViewById(R.id.detailTextView);
            EdittextViewValues = (EditText) itemView.findViewById(R.id.valueEditView);
        }
    }


}
