package com.rahulrvr.cksample.retrofit.interfaces;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public interface LoginService {


    @POST("/service/v2/auth/ip-address")
    void getIP(Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/service/v2/auth/path-choice")
    @Headers("Content-Type : application/x-www-form-urlencoded")
    void  setPathChoice(@Header("Cookie") String jSessionId, @Field("path_choice_id") String pathId, @Field("path_choice_type") String pathType,Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/service/v2/auth/credentials")
    @Headers("Content-Type : application/x-www-form-urlencoded")
    void credentialsCheck(@Header("Cookie") String jSessionId, @Field("username") String userName, @Field("password") String password, Callback<JsonObject> callback);
}
