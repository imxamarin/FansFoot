package com.fansfoot.fansfoot.DefaultPages;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.EditProfile;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.Profiler;
import com.fansfoot.fansfoot.ImageAPI.PickerBuilder;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.fansfoot.fansfoot.models.ImageData;
import com.fansfoot.fansfoot.retrofit.RestClient;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.fansfoot.fansfoot.R.id.imageView;

/**
 * Created by xamarin on 05/12/16.
 */

public class ProfilePage extends Fragment {

    Context context;
    ImageView profilePictureView;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    EditText ProfileNameEdtext;
    EditText ProfileCityEdtext;
    EditText ProfileCountryEdtext;
    EditText ProfileBirthDayEdtext;
    SharedPreferences sharedPreferencesBeta;
    SharedPreferences.Editor editorBeta;
    TextView points;
    TextView likes;
    TextView posts;
    RequestQueue mRequestQueue;
    Profiler profiler;
    ProgressDialog progress;
    String name="",country="",city="",birthday = "";
     Calendar myCalendar;
     Calendar _myCalendar;
    Uri img;
    String base64;
    EditProfile edmx;
    String imgAvi;
    ProgressBar progressBarImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment,null,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        context = getActivity();
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Updating the Profile");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        sharedPreferencesBeta =getActivity().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Profiletoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CheckBox editbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Edit);
        final CheckBox logoutbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Logout);
         ProfileNameEdtext = (EditText) view.findViewById(R.id.ProfileNameEditView);
         ProfileCityEdtext = (EditText) view.findViewById(R.id.ProfileCityEditView);
        ProfileCountryEdtext = (EditText) view.findViewById(R.id.ProfileCountryEditView);
        ProfileBirthDayEdtext = (EditText) view.findViewById(R.id.ProfileBirthdayEditView);
        profilePictureView = (ImageView) view.findViewById(imageView);
        progressBarImage = (ProgressBar) view.findViewById(R.id.progressBarImage);
        points = (TextView) view.findViewById(R.id.Pro_points);
        likes = (TextView) view.findViewById(R.id.Pro_comments);
        posts = (TextView) view.findViewById(R.id.Pro_Post);
        myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.YEAR, -15);
        _myCalendar = Calendar.getInstance();


        profilePictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new PickerBuilder(getActivity(), PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(Uri imageUri) {
                                profilePictureView.setImageURI(imageUri);
                                img = imageUri;
                            }
                        })
                        .start();
            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        ProfileBirthDayEdtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("wa",""+(myCalendar.get(Calendar.YEAR)-15));

                DatePickerDialog dates =   new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dates.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());


                dates.show();
            }

        });

        if(FacebookStatus.CheckFbLogin()){
            String urlAvalivable = sharedPreferencesBeta.getString("iFbpicture","");
            if(!urlAvalivable.isEmpty()){
            Picasso.with(context)
                    .load(urlAvalivable)
                    .into(profilePictureView);
            }else {
                profilePictureView.setImageResource(R.drawable.profile_pic);
            }

            name = sharedPreferencesBeta.getString("iName","");
            city = sharedPreferencesBeta.getString("iCity","");
            country= sharedPreferencesBeta.getString("iCountry","");
            birthday = sharedPreferencesBeta.getString("iBirthday","");
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(city);
            ProfileCountryEdtext.setText(country);
            ProfileBirthDayEdtext.setText(birthday);



        }else {
            String urlAvalivable = sharedPreferencesBeta.getString("FbImage","");
            if(!urlAvalivable.isEmpty()) {
                Picasso.with(context)
                        .load(urlAvalivable)
                        .into(profilePictureView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBarImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBarImage.setVisibility(View.GONE);

                            }
                        });
            }

            String name = sharedPreferencesBeta.getString("FbFFName","");
            String locationCity = sharedPreferencesBeta.getString("eCity","");
            String locationCountry = sharedPreferencesBeta.getString("eCountry","");
            String birthday = sharedPreferencesBeta.getString("eBirthday","");
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(locationCity);
            ProfileCountryEdtext.setText(locationCountry);
            ProfileBirthDayEdtext.setText(birthday);
        }

        CallThisMethodToGetUpdatesFromServer();



        logoutbtn.setEnabled(true);
        logoutbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                logoutbtn.setEnabled(false);
                if (FacebookStatus.CheckFbLogin()){
                    FacebookStatus.disconnectFromFacebook();
                    editorBeta = sharedPreferencesBeta.edit();
                    editorBeta.clear();
                    editorBeta.commit();
                    Toast.makeText(context, "USER LOGOUT", Toast.LENGTH_SHORT).show();
                    DoThisOperation();
                }else{
                    editorBeta = sharedPreferencesBeta.edit();
                    editorBeta.clear();
                    editorBeta.commit();
                    Toast.makeText(context, "USER LOGOUT", Toast.LENGTH_SHORT).show();
                    DoThisOperation();
                }
            }
        });

        editbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                progress.show();


                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), img);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String imagesAvi = getStringImage(bmp);

                ImageData imageData=new ImageData();
                imageData.setType(ConstServer.Edit_profile);
                imageData.setUSERID(sharedPreferencesBeta.getString("FbFFID", "5294"));
                imageData.setFull_name(ProfileNameEdtext.getText().toString());
                imageData.setProfilepicture(imagesAvi);
                imageData.setExtension("jpg");
                imageData.setCountry(ProfileCountryEdtext.getText().toString());

             /*   RestClient.getApiService().postIMage(imageData, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        progress.dismiss();
                        Log.d("responce", jsonObject.toString());
                        Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progress.dismiss();
                        Log.d("responceError", error.toString());
                        Toast.makeText(context, "Image Upload Fail", Toast.LENGTH_SHORT).show();
                    }
                });*/

                RestClient.getApiService().postIMage(ConstServer.Edit_profile,
                        sharedPreferencesBeta.getString("FbFFID", "5294"),
                        ProfileNameEdtext.getText().toString(), imagesAvi, "jpg",
                        ProfileCountryEdtext.getText().toString(), new Callback<JsonObject>() {
                            @Override
                            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                                progress.dismiss();
                                Log.d("responce", jsonObject.toString());
                                Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                progress.dismiss();
                                Log.d("responceError", error.toString());

                                Toast.makeText(context, "Image Upload Fail", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
//                String ModUrl = ConstServer._MainbaseUrls;
//
//
//
//                StringRequest stringRequests = new StringRequest(Request.Method.POST, ModUrl,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String s) {
//                                //Disimissing the progress dialog
//                                progress.dismiss();
//                                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                                Log.d("ans",s);
//                                //Showing toast message of the response
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//                                //Dismissing the progress dialog
//                                progress.dismiss();
//                                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
//                                //Showing toast
//
//                            }
//                        }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Bitmap bmp = null;
//                        try {
//                            bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), img);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        String imagesAvi = getStringImage(bmp);
//
//                        Map<String,String> paramsBody = new Hashtable<String, String>();
//
//                        //Adding parameters
//                        paramsBody.put(ConstServer._posts_type,ConstServer.Edit_profile);
//                        paramsBody.put(ConstServer.my_profiles_USERID,sharedPreferencesBeta.getString("FbFFID","5294"));
//                        paramsBody.put(ConstServer.fullnames,ProfileNameEdtext.getText().toString());
//                        paramsBody.put(ConstServer.profilesPic,imagesAvi);
//                        paramsBody.put(ConstServer.extensions,"jpg");
//                        paramsBody.put(ConstServer.countries,ProfileCountryEdtext.getText().toString());
//
//                        Log.d("Params",paramsBody.toString());
//                        //returning parameters
//                        return paramsBody;
//                    }
//                };
//                mRequestQueue.add(stringRequests);
//            }
            }    });


//                StringRequest stringRequest = new StringRequest(Request.Method.POST, ModUrl, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson _Gson = new Gson();
//                        edmx = _Gson.fromJson(response.toString(),EditProfile.class);
//                        progress.dismiss();
//                        if(edmx.getStatus()==1){
//
//                            editorBeta = sharedPreferencesBeta.edit();
//                            editorBeta.putString("FbFFName",ProfileNameEdtext.getText().toString());
//                            editorBeta.putString("eCountry",ProfileCountryEdtext.getText().toString());
//                            editorBeta.commit();
//
//                            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
//                        }else {
//
//                            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progress.dismiss();
//                        error.printStackTrace();
//                        if (error.networkResponse == null) {
//                            if (error.getClass().equals(TimeoutError.class)) {
//                                // Show timeout error message
//                                Toast.makeText(getContext(),
//                                        "Oops. Timeout error!",
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//
//                    }
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError
//                    {
//                        Map<String, String> paramsBody = new HashMap<>();
//                        paramsBody.put(ConstServer._posts_type,ConstServer.Edit_profile);
//                        paramsBody.put(ConstServer.my_profiles_USERID,sharedPreferencesBeta.getString("FbFFID","5294"));
//                        paramsBody.put(ConstServer.fullnames,ProfileNameEdtext.getText().toString());
//                        paramsBody.put(ConstServer.profilesPic,imgAvi);
//                        paramsBody.put(ConstServer.extensions,"jpg");
//                        paramsBody.put(ConstServer.countries,ProfileCountryEdtext.getText().toString());
//                        Log.d("Ins",paramsBody.toString());
//                        return paramsBody;
//                    }
//
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/x-www-form-urlencoded");
//                        return params;
//                    }
//                };
//
//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        60000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                stringRequest.setRetryPolicy(
//                        new DefaultRetryPolicy(0
//                                , -1,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AdView mAdView = (AdView) view.findViewById(R.id.adViewProfile);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return  view;
    }



    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        }
      //  editText.setBackgroundColor(Color.TRANSPARENT);
    }


    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.clearFocus();
        editText.requestFocus();
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(true);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //  editText.setBackgroundColor(Color.TRANSPARENT);
    }


    public void DoThisOperation(){
        FragmentTransaction fragmentTransaction;
        FragmentManager manager = MainActivity.getBaseFragmentManager();
        manager.popBackStackImmediate();
        fragmentTransaction = manager.beginTransaction();
        LoginPage profilePage = new LoginPage();
        manager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }
    public void CallThisMethodToGetUpdatesFromServer(){


        String ModUrl = ConstServer._MainbaseUrl+
                ConstServer._type+
                ConstServer.my_profile+
                ConstServer._ConCat+
                ConstServer.my_profile_USERID+sharedPreferencesBeta.getString("FbFFID","5294");

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson _Gson = new Gson();
                profiler = _Gson.fromJson(response.toString(),Profiler.class);
                if(profiler.getStatus()==1){

                    points.setText(profiler.getLike().toString());
                    likes.setText(profiler.getComments().toString());
                    posts.setText(profiler.getPost().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(_JsonObjectRequest);


    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ProfileBirthDayEdtext.setText(sdf.format(myCalendar.getTime()));
    }
    public void CallThisMethodToGetUpdatesFromServerBeta(){


        String ModUrl = ConstServer._MainbaseUrl+
                ConstServer._type+
                ConstServer.my_profile+
                ConstServer._ConCat+
                ConstServer.my_profile_USERID+sharedPreferencesBeta.getString("FbFFID","5294");

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(context !=null) {
                    Gson _Gson = new Gson();
                    profiler = _Gson.fromJson(response.toString(), Profiler.class);
                    if (profiler.getStatus() == 1) {
                        points.setText(profiler.getLike().toString());
                        likes.setText(profiler.getComments().toString());
                        posts.setText(profiler.getPost().toString());
                        ProfileNameEdtext.setText(profiler.getName().toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(_JsonObjectRequest);


    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context=null;
        if (mRequestQueue != null) {
            mRequestQueue.stop();
        }
    }
}