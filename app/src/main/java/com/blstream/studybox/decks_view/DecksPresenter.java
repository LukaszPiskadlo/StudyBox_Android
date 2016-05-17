package com.blstream.studybox.decks_view;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.blstream.studybox.activities.EmptyDeckActivity;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.components.ExamStartDialog;
import com.blstream.studybox.data_provider.DataHelper;
import com.blstream.studybox.data_provider.DataProvider;
import com.blstream.studybox.model.database.Deck;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;
import java.util.Random;

public class DecksPresenter extends MvpBasePresenter<DecksView> implements DataProvider.OnDecksReceivedListener<List<Deck>> {

    private LoginManager loginManager;
    private DataProvider dataProvider;
    private EmptyResponseMessage responseMessage;
    private DecksAdapter decksAdapter;

    public DecksPresenter(Context context) {
        loginManager = new LoginManager(context);
        dataProvider = new DataHelper(context);
        responseMessage = new EmptyResponseInfo(context);
    }

    public void loadDecks(boolean pullToRefresh) {  // TODO: if timestamp available, add usage of pullToRefresh
        if (loginManager.isUserLoggedIn()) {
            dataProvider.fetchPrivateDecks(this);
        } else {
            dataProvider.fetchPublicDecks(this, responseMessage.onEmptyDecks());
        }
    }

    @Override
    public void OnDecksReceived(List<Deck> decks) {
       if (isViewAttached()) {
            decks = getRandomDecksFromList(decks);
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

    private List<Decks> getRandomDecksFromList(List<Decks> decks) {
        if (decks.size() >= 3) {
            Random random = new Random();
            int end = random.nextInt(decks.size()) + 3;
            end = Math.min(end, decks.size());
            decks = decks.subList(end - 3, end);
        }

        return decks;
    }

    public void setDecksAdapter(DecksAdapter decksAdapter) {
        this.decksAdapter = decksAdapter;
    }

    public void onDeckClicked(int position, View view) {
        String deckId;
        String deckName;
        int cardsAmount;

        deckId = dataProvider.getCurrentDecks().get(position).getDeckId();
        deckName = dataProvider.getCurrentDecks().get(position).getName();
        cardsAmount = dataProvider.getCurrentDecks().get(position).getFlashcardsCount();

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