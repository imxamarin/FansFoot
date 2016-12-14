package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fansfoot.fansfoot.DefaultPages.AboutUsPage;
import com.fansfoot.fansfoot.DefaultPages.MemesPage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 05/12/16.
 */

public class SettingsRecycleViewAdapter extends RecyclerView.Adapter<SettingsRecycleViewAdapter.SettingViewHolder> {


    String[] SubjectValues;
    int[] ImageValues;

   static Context context;
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
    public void onBindViewHolder(SettingViewHolder holder, final int position) {
        holder.textView.setText(SubjectValues[position]);
        holder.imageBtn.setButtonDrawable(ImageValues[position]);



    }

    @Override
    public int getItemCount() {
       return SubjectValues.length;
    }


    public static class SettingViewHolder extends RecyclerView.ViewHolder  {
        public TextView textView;
        public CheckBox imageBtn;
        public SettingViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.SettingMenuBar);
           imageBtn = (CheckBox) itemView.findViewById(R.id.SettingsImgBtn);
            imageBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   int x = getPosition();
                    if(x==4){
                        if(b==true){
                            compoundButton.setButtonDrawable(R.drawable.on_toggle);
                        }else {
                            compoundButton.setButtonDrawable(R.drawable.off_toggle);
                        }


                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                    manager.popBackStackImmediate();
                    fragmentTransaction = manager.beginTransaction();
                    switch (x) {
                        case 0:
                            Snackbar.make(view,"Profile",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 1:
                            AboutUsPage memes = new AboutUsPage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,memes);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 2:
                            Snackbar.make(view,"Rating",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Snackbar.make(view,"Feedback",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 5:
                            Snackbar.make(view,"Share App",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 6:
                            Snackbar.make(view,"Report",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 7:
                            Snackbar.make(view,"Like on Facebook",Snackbar.LENGTH_SHORT).show();
                            break;
                        case 8:
                            Snackbar.make(view,"Like on Twitter",Snackbar.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }

                }
            });



        }
    }
}
