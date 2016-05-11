package com.blstream.studybox.api;

import com.blstream.studybox.model.AuthCredentials;
import com.blstream.studybox.model.database.Card;
import com.blstream.studybox.model.database.Decks;
import com.blstream.studybox.model.database.Tip;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;

public class RestClientManager {

    public static void getPrivateDecks(boolean key, RequestInterceptor interceptor, RequestCallback<List<Decks>> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.getPrivateDecks(key, callback);
    }

    public static void getRandomDeck(boolean randomKey, boolean flashcardCountKey, RequestCallback<List<Decks>> callback){
        RestInterface restInterface = new RestClient().getService();
        restInterface.getRandomDeck(randomKey, flashcardCountKey, callback);
    }

    public static void getPublicDecks(boolean key, RequestCallback<List<Decks>> callback){
        RestInterface restInterface = new RestClient().getService();
        restInterface.getDecks(key, callback);
    }

    public static void getFlashcards(final String key, final String randomAmount, RequestCallback<List<Card>> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.getFlashcards(key, randomAmount, callback);
    }

    public static void authenticate(RequestInterceptor interceptor, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient(interceptor).getService();
        restInterface.authenticate(callback);
    }

    public static void signUp(AuthCredentials credentials, Callback<AuthCredentials> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.signUp(credentials, callback);
    }

    public static void getTips(String deckId, String cardId, RequestCallback<List<Tip>> callback) {
        RestInterface restInterface = new RestClient().getService();
        restInterface.getTips(deckId, cardId, callback);
    }
}
