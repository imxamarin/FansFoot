package com.fansfoot.fansfoot.retrofit;

import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.models.ImageData;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by xamarin on 11/01/17.
 */

public interface ApiService {



/*
    @POST("/")
    void createPath(
            @Body Object dummy
            , Callback<CreatePathResponse> callback);*/

   /* @FormUrlEncoded
    @PUT("/listing/{listing_id}/image/{image_id}")
    void editImageWithNumber(@Header(GlobalConstant.TOKEN) String token
            , @Header(GlobalConstant.PLATFORM) String plateform
            , @Header(GlobalConstant.API_VERSION) String appVersion
            , @Field(GlobalConstant.LISTING_MODIFICATION_DATE) String modificationDate
            , @Field(GlobalConstant.POSITION) String POSITION
            , @Path(GlobalConstant.LISTING_ID) String listingId
            , @Path(GlobalConstant.IMAGE_ID) String image_id
            , Callback callback);*/

    /*paramsBody.put(ConstServer._post_type,ConstServer.Edit_profile);
    paramsBody.put(ConstServer.my_profiles_USERID,sharedPreferencesBeta.getString("FbFFID","5294"));
    paramsBody.put(ConstServer.fullnames,ProfileNameEdtext.getText().toString());
    paramsBody.put(ConstServer.profilesPic,base64);
    paramsBody.put(ConstServer.extensions,"jpg");
    paramsBody.put(ConstServer.countries,ProfileCountryEdtext.getText().toString());*/


    /*@POST("/post_type/{post_type}/USERID/{USERID}/full_name/{full_name}/profilepicture/{profilepicture}/extension/{extension}/country/{country}")
    void postIMage(
             @Path("post_type") String post_type
            , @Path("USERID") String USERID
            , @Path("full_name") String full_name
            , @Path("profilepicture") String profilepicture
            , @Path("extension") String extension
            , @Path("country") String country
            , Callback<JsonObject> callback);*/
//
    @FormUrlEncoded
    @POST("/")
    void postIMage(
            @Field("type") String type
            , @Field("USERID") String USERID
            , @Field("full_name") String full_name
            , @Field("profilepicture") String profilepicture
            , @Field("extension") String extension
            , @Field("country") String country
            , Callback<JsonObject> callback);



/*    @POST("/web")
    void postIMage(
            @Body ImageData dummy
            , Callback<JsonObject> callback);*/
}
