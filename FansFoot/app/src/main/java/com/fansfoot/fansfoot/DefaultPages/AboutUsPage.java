package com.fansfoot.fansfoot.DefaultPages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fansfoot.fansfoot.R;


/**
 * Created by xamarin on 14/12/16.
 */

public class AboutUsPage extends Fragment  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.about_us_fragment,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.cm_about_us);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cm_back_about);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });
        TextView tv = (TextView) view.findViewById(R.id.textID);

        tv.setText("Oh, Indy. Oh, Indy\n" +
                "The skies are so windy\n" +
                "Is that a flying man with a killer bod?\n" +
                "Wait!\n" +
                "That's no man, it's a Norse God!\n" +
                "\n" +
                "Thunder clapped as Thor\n" +
                "raised his mighty hammer\n" +
                "Indy rapped, 'That's one bad mamma-jamma'\n" +
                "\n" +
                "Thor and Dr. Jones\n" +
                "Thor and Dr. Jones\n" +
                "One plays with lightning\n" +
                "The other plays with bones\n" +
                "\n" +
                "Thor and Dr. Jones\n" +
                "Thor and Dr. Jones\n" +
                "One runs from Loki\n" +
                "The other runs from stones\n" +
                "\n" +
                "Thor summoned lightning and made the Earth quake \n" +
                "But Indy feared nothing, except perhaps a snake\n" +
                "\n" +
                "Thor and Dr. Jones\n" +
                "Thor and Dr. Jones\n" +
                "\n" +
                "Indy's whip snapped, Thor's hammer missed\n" +
                "It was Avenger versus archeologist \n" +
                "Indy held his ground and straightened his fedora \n" +
                "Thor said, 'Nice look in nineteen forty-four-ah' \n" +
                "\n" +
                "Thor and Dr. Jones\n" +
                "Thor and Dr. Jones\n" +
                "One wears a cape\n" +
                "The other wears Earth tones \n" +
                "\n" +
                "Thor and Dr. Jones\n" +
                "Thor and Dr. Jones");
        return view;
    }

}
