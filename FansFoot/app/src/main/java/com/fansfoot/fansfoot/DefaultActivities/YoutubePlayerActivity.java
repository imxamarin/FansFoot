package com.fansfoot.fansfoot.DefaultActivities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fansfoot.fansfoot.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String TitleImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_player);
        Intent intent = getIntent();
        TitleImg = intent.getStringExtra("VideoTitle");
         youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youTubeView.initialize("AIzaSyDKVkI5QRYt486jYFoKUW4npL0wt6dDGAo",this);



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//        if (!b) {
        Log.d("start","provider"+provider);
        Log.d("start","youTubePlayer"+youTubePlayer);
        Log.d("startTilte",TitleImg);
            youTubePlayer.loadVideo(TitleImg);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            youTubePlayer.play();
//        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                Toast.makeText(this, provider.toString(), Toast.LENGTH_LONG).show();
        Log.d("abc",errorMessage);
        Log.d("dfg",provider.toString());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize("AIzaSyDKVkI5QRYt486jYFoKUW4npL0wt6dDGAo", this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


}
