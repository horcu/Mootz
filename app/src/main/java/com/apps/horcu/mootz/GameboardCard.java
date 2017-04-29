package com.apps.horcu.mootz;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;


/**
 * Created by Horatio on 5/21/2016.
 */
public class GameboardCard extends CardView {

    public GameboardCard(Context context) {
        super(context);
    }

    public GameboardCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameboardCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int GetCardIndex() {

        int tagIndex = -1;
        return GetProperties().getCardIndex();
    }

    private CardProperties GetProperties() {
        return (CardProperties) getTag(consts.CARD_PROPERTIES);
    }

    public String GetCardLetter() {

        String letter = "";
        if (getTag(consts.ANSWER_LETTER) != null)
            letter = (String) getTag(consts.ANSWER_LETTER);
        return letter;
    }

    public CardProperties GetCardProperties() {
        return (CardProperties) getTag(consts.CARD_PROPERTIES);
    }
}
