package com.fansfoot.fansfoot.DefaultActivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 21/12/16.
 */

public class CommentPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page);
    }
}
