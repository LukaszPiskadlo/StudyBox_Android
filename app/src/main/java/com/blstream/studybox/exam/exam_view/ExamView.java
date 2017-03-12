package com.blstream.studybox.exam.exam_view;

import com.blstream.studybox.model.database.Card;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ExamView extends MvpView {

    void setCardCounter(int currentCard, int totalCards);

    void showQuestion(Card card);

    void showAnswer(Card card);
    void showResult(int correctAnswers, int totalCards);
    void setDeckTitle(String deckTitle);
}
