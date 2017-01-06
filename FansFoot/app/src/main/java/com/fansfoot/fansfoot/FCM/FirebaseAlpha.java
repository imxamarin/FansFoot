package com.fansfoot.fansfoot.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

/**
 * Created by xamarin on 26/12/16.
 */

public class FirebaseAlpha extends FirebaseInstanceIdService {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("fu", "Refreshed token: " + refreshToken);

    }
//http://fansfoot.com/mobile/web/?type=checkPushNotification&device_token=c6UROFXlA-4:APA91bGznCT0T-zGB_3HB7rLDzURmgTQSi2IyuVFlKC4jrP99XA7Ve9i9MOIcvvpmivtYUL9Uulq0ZJx0IGOePjD0jv_fSqVprHF_rARNhIy0KDVJrNTRGHqfXRuKD1GRQU4jO4UNxHC&device_type=android
//http://fansfoot.com/mobile/web/?type=checkPushNotification?device_token=c6UROFXlA-4:APA91bGznCT0T-zGB_3HB7rLDzURmgTQSi2IyuVFlKC4jrP99XA7Ve9i9MOIcvvpmivtYUL9Uulq0ZJx0IGOePjD0jv_fSqVprHF_rARNhIy0KDVJrNTRGHqfXRuKD1GRQU4jO4UNxHC&device_type=android
//http://fansfoot.com/mobile/web/?type=checkPushNotification?device_token=foHxC2L3u-c:APA91bHWxlT1zxmiBfrCWdwNx-5PK7R6ZKLckHQpiXAZsc3dx3Otm_Q0MDFkMlTWsnA3joj23PNDrtA26SmlDUSlbWPeDjYaoqbaSi60jQ7dpC1KRGnAFOmeGKDd2rJt2kM9q1I_g4Gl&device_type=android
}
