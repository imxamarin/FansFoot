package in.udya.alphafallproject;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button HomeButton,SectionButton,VideoButton,ProfileButton,SettingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePage home = new HomePage();
        fragmentTransaction.add(R.id.frag,home);
        fragmentTransaction.commit();
        CallThisToPerformButtonAction();
    }

    private void HomeFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePage home = new HomePage();
        fragmentTransaction.replace(R.id.frag,home);
        fragmentTransaction.commit();
    }

    private void SelectionFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectionPage selectionPage = new SelectionPage();
        fragmentTransaction.replace(R.id.frag,selectionPage);
        fragmentTransaction.commit();
    }

    private void ProfileFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfilePage profilePage  = new ProfilePage();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }

    private void VideoFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoPage videoPage = new VideoPage();
        fragmentTransaction.replace(R.id.frag,videoPage);
        fragmentTransaction.commit();
    }

    private void SettingsFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsPage settingsPage  = new SettingsPage();
        fragmentTransaction.replace(R.id.frag,settingsPage);
        fragmentTransaction.commit();
    }

    private void CallThisToPerformButtonAction() {
        HomeButton = (Button) findViewById(R.id.HomeButton);
        SectionButton = (Button) findViewById(R.id.SectionButton);
        VideoButton = (Button) findViewById(R.id.VideoButton);
        ProfileButton = (Button) findViewById(R.id.ProfileButton);
        SettingButton = (Button) findViewById(R.id.SettingButton);

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            HomeFragment();
            }
        });

        SectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SelectionFragment();
            }
        });

        VideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment();
            }
        });

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment();
            }
        });

        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment();
            }
        });


    }
}
