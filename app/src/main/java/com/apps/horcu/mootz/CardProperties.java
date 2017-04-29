package com.apps.horcu.mootz;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;

/**
 * Created by Horatio on 5/22/2016.
 */
public class CardProperties{

    //setup
    private int CardColor;
    private boolean IsExposed;
    private int LetterValue;
    private int CardIndex;
    private TextDrawable ImageDrawable;
    private int BackgroundColor;
    private String TileLetter;
    private int LetterPosition;
    private View.OnClickListener listener;

    public CardProperties() {
    }

    public int getCardColor() {
        return CardColor;
    }

    public void setCardColor(int cardColor) {
        CardColor = cardColor;
    }

    public boolean isExposed() {
        return IsExposed;
    }

    public void setExposed(boolean exposed) {
        IsExposed = exposed;
    }

    public int getLetterValue() {
        return LetterValue;
    }

    public void setLetterValue(int letterValue) {
        LetterValue = letterValue;
    }

    public int getCardIndex() {
        return CardIndex;
    }

    public void setCardIndex(int cardIndex) {
        CardIndex = cardIndex;
    }

    public TextDrawable getImageDrawable() {
        return ImageDrawable;
    }

    public void setImageDrawable(TextDrawable imageDrawable) {
        ImageDrawable = imageDrawable;
    }

    public int getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getTileLetter() {
        return TileLetter;
    }

    public void setTileLetter(String tileLetter) {
        TileLetter = tileLetter;
    }

    public int getLetterPosition() {
        return LetterPosition;
    }

    public void setLetterPosition(int letterPosition) {
        LetterPosition = letterPosition;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}
