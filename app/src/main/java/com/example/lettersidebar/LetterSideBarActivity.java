package com.example.lettersidebar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LetterSideBarActivity extends AppCompatActivity implements LetterSideBar.LetterTouchListener {

    TextView textView;
    LetterSideBar letterSideBar;
    private char letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_side_bar);

        textView = findViewById(R.id.letter_tv);
        letterSideBar = findViewById(R.id.letter_bar);

        letterSideBar.setLetterTouchListener(this);
    }


    @Override
    public void touch(char letter, boolean ifTouch) {
        if (ifTouch) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(letter));
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}