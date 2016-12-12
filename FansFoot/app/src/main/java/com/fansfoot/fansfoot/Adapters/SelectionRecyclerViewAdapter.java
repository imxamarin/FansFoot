package com.fansfoot.fansfoot.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fansfoot.fansfoot.DefaultPages.GifPage;
import com.fansfoot.fansfoot.DefaultPages.MemesPage;
import com.fansfoot.fansfoot.DefaultPages.NsfwPage;
import com.fansfoot.fansfoot.DefaultPages.SelectionPage;
import com.fansfoot.fansfoot.DefaultPages.VideoPage;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

import java.net.URISyntaxException;

/**
 * Created by xamarin on 05/12/16.
 */

public class SelectionRecyclerViewAdapter extends RecyclerView.Adapter<SelectionRecyclerViewAdapter.ViewHolder> {


    String[] SubjectValues;
    int[] ImageValues;
    private AdapterView.OnItemClickListener mListener;
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

    public  class ViewHolder extends RecyclerView.ViewHolder {
       public TextView textView;
        public ImageView imageView;
        SelectionPage sp = new SelectionPage();
        public ViewHolder(final View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.TitleView);
            imageView = (ImageView) itemView.findViewById(R.id.ImgView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int x = getPosition();
                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
//                    if(fragment != null){
//                       fragmentTransaction = MainActivity.getBaseFragmentManager().beginTransaction().remove(fragment).commit();
//                    }else {

                        fragmentTransaction = manager.beginTransaction();
//                    }

                    switch (x){
                        case 0:
                            MemesPage memes = new MemesPage();
                            Toast.makeText(context,  manager.getFragments().toString()+"", Toast.LENGTH_LONG).show();
                            fragmentTransaction.replace(R.id.frag,memes);
                            Toast.makeText(context,  manager.getFragments().toString()+"", Toast.LENGTH_LONG).show();
                            fragmentTransaction.commit();
                            break;
                        case 1:
                            VideoPage videopage = null;
                            try {
                                videopage = new VideoPage();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            fragmentTransaction.replace(R.id.frag,videopage);

                            fragmentTransaction.commit();
                            break;
                        case 2:
                            NsfwPage nsfw = new NsfwPage();
                            fragmentTransaction.replace(R.id.frag,nsfw);
                            fragmentTransaction.commit();
                            break;
                        case 3:
                            GifPage gifpage = new GifPage();
                            fragmentTransaction.replace(R.id.frag,gifpage);
                            fragmentTransaction.commit();
                            break;
                        default:
                            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }

    }
}
