package com.fansfoot.fansfoot.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.model.StringLoader;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xamarin on 19/12/16.
 */

public class AlphaSingleton {

    private static AlphaSingleton _Instance;
    private RequestQueue _RequestQueue;
    private StringLoader _stringLoader;
    private static Context _Ctx;
    FansfootServer fansfootServers;
    List<Post> posts;
    List<String> URLFromServer;
    private AlphaSingleton(Context context){
        _Ctx = context;
        _RequestQueue = getRequestQueue();

    }
    public RequestQueue getRequestQueue() {
        if (_RequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            _RequestQueue = Volley.newRequestQueue(_Ctx.getApplicationContext());
        }
        return _RequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    public static synchronized AlphaSingleton getInstance(Context context) {
        if (_Instance == null) {
            _Instance = new AlphaSingleton(context);
        }
        return _Instance;
    }
    public StringLoader getImageLoader() {
        return _stringLoader;
    }

}
