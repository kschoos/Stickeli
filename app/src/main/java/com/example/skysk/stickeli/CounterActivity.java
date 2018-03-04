package com.example.skysk.stickeli;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
    }

    public void onClickCounter(View view){
        TextView textView = (TextView) view;
        int currentCount = Integer.parseInt((String) textView.getText());
        textView.setText(Integer.toString(currentCount + 1));
    }
}
