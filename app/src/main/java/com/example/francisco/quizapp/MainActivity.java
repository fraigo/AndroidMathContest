package com.example.francisco.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mButton1True, mButton1False, mButton2True, mButton2False;
    private TextView mText1, mText2;
    int curPosition;
    Question current;
    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions=new ArrayList<Question>();
        curPosition = -1;

        questions.add(new Question("2 x 3 = 5",false));
        questions.add(new Question("3 x 4 = 12",true));
        questions.add(new Question("4 x 5 = 25",false));

        mButton1True = findViewById(R.id.button1a);
        mButton1False = findViewById(R.id.button1b);
        mButton2True = findViewById(R.id.button2a);
        mButton2False = findViewById(R.id.button2b);
        mText1 = findViewById(R.id.text_a);
        mText2 = findViewById(R.id.text_b);


        mButton1True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(1,true);
            }
        });
        mButton1False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(1,false);
            }
        });
        mButton2True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(2,true);
            }
        });
        mButton2False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(2,false);
            }
        });

        nextQuestion();


    }

    public void nextQuestion(){
        curPosition++;
        if (curPosition ==questions.size()){
            curPosition = 0;
        }
        current=questions.get(curPosition);
        mText1.setText(current.getText());
        mText2.setText(current.getText());

    }


    private void answer(int player, boolean answer){
        // Toast.makeText(this, String.format("Player %d response %s",player,answer), Toast.LENGTH_SHORT).show();
        System.out.println(String.format("Player %d response %s",player,answer));
        
    }
}
