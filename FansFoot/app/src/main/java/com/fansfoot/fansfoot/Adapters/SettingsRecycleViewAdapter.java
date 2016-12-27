package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.DefaultPages.AboutUsPage;
import com.fansfoot.fansfoot.DefaultPages.FbLikePage;
import com.fansfoot.fansfoot.DefaultPages.FeedbackPage;
import com.fansfoot.fansfoot.DefaultPages.LoginPage;
import com.fansfoot.fansfoot.DefaultPages.MemesPage;
import com.fansfoot.fansfoot.DefaultPages.ProfilePage;
import com.fansfoot.fansfoot.DefaultPages.RatingPage;
import com.fansfoot.fansfoot.DefaultPages.ReportPage;
import com.fansfoot.fansfoot.DefaultPages.ShareTheApp;
import com.fansfoot.fansfoot.DefaultPages.TwitterLikePage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.models.LikeTransition;

import org.greenrobot.eventbus.EventBus;

import static com.fansfoot.fansfoot.R.id.view;

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
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
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
                            Snackbar snackbar = Snackbar
                                    .make(itemView,"NSFW Enabled",Snackbar.LENGTH_SHORT);

                            snackbar.show();
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
                            LikeTransition likeTransition=new LikeTransition();
                            likeTransition.setPos(x);
                            likeTransition.setFragName("profile");
                            EventBus.getDefault().post(likeTransition);
//                            boolean fb_status = FacebookStatus.CheckFbLogin();
//
//                            if(fb_status == true)
//                            {
//                                ProfilePage profilePage = new ProfilePage();
//                                manager.popBackStackImmediate();
//                                fragmentTransaction.replace(R.id.frag,profilePage);
//                                fragmentTransaction.addToBackStack(null);
//                                fragmentTransaction.commit();
//                                break;
//                            }else {
//                                LoginPage profilePage = new LoginPage();
//                                manager.popBackStackImmediate();
//                                fragmentTransaction.replace(R.id.frag,profilePage);
//                                frbreak;agmentTransaction.addToBackStack(null);
//                                fragmentTransaction.commit();
//                                break;
//                            }
                            break;

                        case 1:
                            AboutUsPage aboutUsPage = new AboutUsPage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,aboutUsPage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 2:
                            RatingPage ratingPage = new RatingPage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,ratingPage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 3:
                            FeedbackPage feedbackPage = new FeedbackPage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,feedbackPage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 5:
                            ShareTheApp shareThePage = new ShareTheApp();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,shareThePage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 6:
                            ReportPage reportPage = new ReportPage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,reportPage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 7:
                            FbLikePage fbLikePage = new FbLikePage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,fbLikePage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        case 8:
                            TwitterLikePage twLikePage = new TwitterLikePage();
                            manager.popBackStackImmediate();
                            fragmentTransaction.replace(R.id.frag,twLikePage);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            break;
                        default:
                            break;
                    }

                }
            });



        }
        public void JumpToFaceBookForLogin(){
            int x = getPosition();
            LikeTransition likeTransition=new LikeTransition();
            likeTransition.setPos(x);
            likeTransition.setFragName("profile");
            EventBus.getDefault().post(likeTransition);


        }

    }
}
