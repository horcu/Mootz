package com.apps.horcu.mootz;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainView extends AppCompatActivity {

    private EditText userWordEditText;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    userWordEditText.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    userWordEditText.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    userWordEditText.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//
//    };

    private GameboardGrid letterGridUser;
    private GameboardGrid letterGrid;
    private TreeMap<Integer, String> alphaMap;
    private ImageView mSetWord;
    private String currentWord;
    private LinearLayout wEntryLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        if(getActionBar()!=null)
        getActionBar().hide();

        wEntryLinear = (LinearLayout) findViewById(R.id.word_entry_linear);


        userWordEditText = (EditText) findViewById(R.id.message);
        mSetWord = (ImageView)findViewById(R.id.set_word);
        mSetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check to make sure that the word is spelled correctly
                //also make sure that there are only 5 characters in the word
                //also that there arent any repeating letters
                if(!CheckWord())
                    return;

                //animate the movement of the tiles to their set places
                RearrangeFirstRow();

                //get the remaining letters in a list
                TreeMap<Integer, String> remLetters = GetRemainingLetters();

                //set the remaining letters in alpha order
                SetRemainingRows(remLetters);

                //show the letters
                letterGrid.setVisibility(View.VISIBLE);

                //hide the edit text. need to add an icon in the top bar or somewhere else
                // that can bring it back to change the word if user changed mind
                wEntryLinear.setVisibility(View.GONE);

                letterGridUser.setVisibility(View.VISIBLE);
            }
        });


        userWordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(s==null)
                    return;

                if (s.toString().equals("")) {
                    //clean up then return
                    for(int i = 0; i < currentWord.length(); i++){
                        UnHighlightTile(String.valueOf(currentWord.charAt(i)));
                    }
                    letterGridUser.setVisibility(View.INVISIBLE);
                    letterGrid.setVisibility(View.INVISIBLE);
                    return;
                }


                    letterGrid.setVisibility(View.VISIBLE);
                currentWord = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                //maybe change this to repaint the entire set of letters each time and not just the last one
                //may be more responsive that way
                if(s.length() != 0)
                {
                    String lastCharStr = s.toString().substring(s.length()-1, s.length());
                    if(lastCharStr.toLowerCase().equals("x") || lastCharStr.toLowerCase().equals("y")) {
                        //they refer to the same position
                        lastCharStr = "X/Y";
                    }
                        HighlightTile(lastCharStr);

                }
            }
        });


        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        letterGridUser = (GameboardGrid)findViewById(R.id.game_grid_user);
        letterGrid = (GameboardGrid)findViewById(R.id.game_grid_opp);

       ArrayList<String> alpha = new ArrayList<String>()
       {{ add("A");add("B"); add("C"); add("D");add("E");add("F");add("G");add("H"); add("I");add("J");add("K");add("L");
           add("M"); add("N");add("O"); add("P"); add("Q"); add("R"); add("S");add("T"); add("U");  add("V"); add("W"); add("X/Y");  add("Z");
       }};

        alphaMap = new TreeMap<>();

        for (int i=0; i < 25; i++){
            alphaMap.put(i,alpha.get(i));
        }

        SetInitBoard(letterGrid, alpha);
    }

    private TreeMap<Integer, String> GetRemainingLetters() {
        TreeMap<Integer, String> alphaMapClone = new TreeMap<>(alphaMap);

        for(int i=0; i < 25; i ++){
            String currString = alphaMap.get(i).toString().toLowerCase();
            if(currentWord.contains(currString)){
                alphaMapClone.remove(i);
            }
        }

        return alphaMapClone;
    }


    private boolean CheckWord() {
        return true;
    }

    private void RearrangeFirstRow() {

        for(int i=0; i < 5; i++){
            GameboardCard letterCard = (GameboardCard) letterGridUser.getChildAt(i);
            ImageView img = (ImageView) letterCard.findViewById(R.id.tile_letter);
            PaintLetterTile(String.valueOf(currentWord.charAt(i)), img, "#4caf50");
        }
    }

    private void SetRemainingRows(TreeMap<Integer, String> remLetters) {

        for(int i=0; i < 5; i++){
            GameboardCard letterCard = (GameboardCard) letterGrid.getChildAt(i);
            ImageView img = (ImageView) letterCard.findViewById(R.id.tile_letter);
            PaintLetterTile("", img, "#ffffff");
        }

        for(int i=0; i < 20; i++){
            int spot = i+5;

                GameboardCard letterCard = (GameboardCard) letterGrid.getChildAt(spot);
                ImageView img = (ImageView) letterCard.findViewById(R.id.tile_letter);

                Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) remLetters.entrySet().toArray()[i];

                String letter = entry.getValue();
                PaintLetterTile(letter, img, "#edebec");
        }

    }


    private void HighlightTile(String letterEntered) {

            GameboardCard letterTile =  GetTileByLetter(letterEntered.toLowerCase());

            if(letterTile != null) {
                PaintLetterTile(letterEntered, (ImageView) letterTile.findViewById(R.id.tile_letter), "#ff5722");
            }

    }

    private void UnHighlightTile(String letterEntered) {

        GameboardCard letterTile =  GetTileByLetter(letterEntered.toLowerCase());

        if(letterTile != null) {
            PaintLetterTile(letterEntered, (ImageView) letterTile.findViewById(R.id.tile_letter), "#ebebeb");
        }

    }

    private GameboardCard GetTileByLetter(String letterEntered) {
        for(int i=0; i < 25; i++){
            if(alphaMap.get(i).toLowerCase().equals(letterEntered)){
                return (GameboardCard) letterGrid.getChildAt(i);
            }
        }
        return null;
    }

    private void SetInitBoard(ViewGroup navigation, ArrayList<String> alpha) {
        //set up the grid for the user's word
        for (int i = 0; i < 5; i++) {
            final GameboardCard relativeLayout = (GameboardCard) getLayoutInflater().inflate(R.layout.letter_tile, (ViewGroup)navigation.getParent(), false);
            letterGridUser.addView(relativeLayout,i);
        }

        //set up the grid for the opponents remaining letters
        for (int i = 0; i < 25; i++) {

            final GameboardCard relativeLayout = (GameboardCard) getLayoutInflater().inflate(R.layout.letter_tile, (ViewGroup)navigation.getParent(), false);
            String letter = alpha.get(i);
            ImageView img = (ImageView) relativeLayout.findViewById(R.id.tile_letter);
            TextView pos  = (TextView) relativeLayout.findViewById(R.id.tile_position);

            int position = i+1;
            pos.setText(String.valueOf(position));
            pos.setTextSize(15);
            pos.setTextColor(Color.LTGRAY);

            PaintLetterTile(letter, img, "#ebebeb");

            letterGrid.addView(relativeLayout,i);
        }
    }

    private void PaintLetterTile(String letter, ImageView img, String bgColor) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(6) /* thickness in px */
                .textColor(Color.parseColor("#000000"))
                .useFont(Typeface.SANS_SERIF)
                .toUpperCase()
                .fontSize(48) /* size in px */
                //.width(ViewGroup.LayoutParams.MATCH_PARENT)  // width in px
                // .height(ViewGroup.LayoutParams.MATCH_PARENT) // height in px
                .bold()
                .endConfig()
                .buildRoundRect(letter, Color.parseColor(bgColor), 9);

        img.setImageDrawable(drawable);
    }

}
