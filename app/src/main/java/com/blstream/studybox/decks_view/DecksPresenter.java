package com.blstream.studybox.decks_view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.blstream.studybox.activities.EmptyDeckActivity;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.components.ExamStartDialog;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

public class DecksPresenter extends MvpBasePresenter<DecksView> implements DataProvider.OnDecksReceivedListener<List<Decks>> {

    private LoginManager loginManager;
    private DataProvider dataProvider;
    private EmptyResponseMessage responseMessage;


    public DecksPresenter(Context context) {
        loginManager = new LoginManager(context);
        dataProvider = new DataHelper(context);
        responseMessage = new EmptyResponseInfo(context);
    }

    public void loadDecks(boolean pullToRefresh) {  // TODO: if timestamp available, add usage of pullToRefresh
        if (loginManager.isUserLoggedIn()) {
            dataProvider.fetchPrivateDecks(this, responseMessage.onEmptyDecks());
        } else {
            dataProvider.fetchPublicDecks(this, responseMessage.onEmptyDecks());
        }
    }

    @Override
    public void OnDecksReceived(List<Decks> decks) {
        if (isViewAttached()) {
            getView().setData(decks);
            getView().showLoading(false);
            getView().showContent();
        }
    }

    @Override
    public void OnEmptyResponse(String message) {
        if (isViewAttached()) {
            getView().setEmptyListInfo(message);
            getView().showLoading(false);
            getView().showContent();
        }
    }

    public void onDeckClicked(int position, View view) {
        String deckId;
        String deckName;
        int cardsAmount;

        if (loginManager.isUserLoggedIn()) {
            deckId = dataProvider.getPrivateDecks().get(position).getDeckId();
            deckName = dataProvider.getPrivateDecks().get(position).getName();
            cardsAmount = dataProvider.getPrivateDecks().get(position).getFlashcardsCount();
        } else {
            deckId = dataProvider.getPublicDecks().get(position).getDeckId();
            deckName = dataProvider.getPublicDecks().get(position).getName();
            cardsAmount = dataProvider.getPublicDecks().get(position).getFlashcardsCount();
        }

        if (cardsAmount == 0) {
            EmptyDeckActivity.start(view.getContext());
        } else {
            Context context = view.getContext();
            ExamStartDialog examStartDialog = ExamStartDialog.newInstance(deckId, deckName, cardsAmount);
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            examStartDialog.show(fragmentManager, "ExamStartDialog");
        }
    }

    public void getDecksByName(String deckName) {
        dataProvider.fetchDecksByName(this, deckName, responseMessage.onEmptyQuery());
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }
}