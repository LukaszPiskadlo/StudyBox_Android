package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.DecksList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface RestInterface {
    @GET("/{json}")
    void getAllDecks(@Path("json") String json, Callback<DecksList> cb);

    @GET("/users/me")
    void authenticate(Callback<AuthCredentials> callback);
}
