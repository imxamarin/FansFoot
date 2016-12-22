package com.fansfoot.fansfoot.DefaultActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 21/12/16.
 */

public class PostPage extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    EditText FbComment;
    LinearLayout linearLayout;
    Spinner spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        textView = (TextView) findViewById(R.id.PostTilteID);
        FbComment = (EditText) findViewById(R.id.FbComment);
        imageView = (ImageView) findViewById(R.id.PostMainImage);
        linearLayout = (LinearLayout) findViewById(R.id.FbShareLayout);
        spinner = (Spinner) findViewById(R.id.Spiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.FB_Array,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        String TitleImg = intent.getStringExtra("ImageTitle");
        String TitlePic = intent.getStringExtra("ImageURL");
        if(textView != null){
            textView.setText(TitleImg);
            Glide
                    .with(getApplicationContext())
                    .load(TitlePic)
                    .fitCenter()
                    .placeholder(R.drawable.post_img)
                    .crossFade()
                    .into(imageView);
        }

//        FbComment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    linearLayout.setVisibility(View.VISIBLE);
//                }else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    linearLayout.setVisibility(View.GONE);
//                }else if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    linearLayout.setVisibility(View.GONE);
//                }else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
//                    linearLayout.setVisibility(View.GONE);
//                }
//
//
//                return true;
//            }
//        });

        FbComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==true){
                    Snackbar.make(view,"Hola",Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(view,"Lola",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}
