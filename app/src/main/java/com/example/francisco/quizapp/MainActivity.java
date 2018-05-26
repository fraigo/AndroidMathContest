package com.example.francisco.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button mButton1True, mButton1False, mButton2True, mButton2False;
    private TextView mText1, mText2, status1, status2;
    int curPosition;
    Question current;
    ArrayList<Question> questions;
    boolean answered;
    int points1, points2, baseNumber;
    ProgressBar mBar1, mBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseNumber = getIntent().getIntExtra("COUNTER", 2);
        System.out.println("Created "+baseNumber);

        questions=new ArrayList<Question>();
        answered = false;

        curPosition = -1;
        points1 = 0;
        points2 = 0;
        if (savedInstanceState!=null) {
            curPosition = savedInstanceState.getInt("CURRENT_POSITION", curPosition);
            points1 = savedInstanceState.getInt("PLAYER1_POINTS", points1);
            points2 = savedInstanceState.getInt("PLAYER2_POINTS", points2);
        }

        fillAnswers(savedInstanceState);

        findViews();

        setListeners();

        nextQuestion();


    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Paused");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroyed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Restarted");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SavedInstanceState");
        outState.putInt("CURRENT_POSITION",curPosition-1);
        outState.putInt("PLAYER1_POINTS",points1);
        outState.putInt("PLAYER2_POINTS",points2);
        outState.putString("LAST_QUESTION",current.getText());
        outState.putBoolean("LAST_ANSWER",current.getAnswer());
    }

    public void fillAnswers(Bundle savedInstanceState){
        for(int i=2; i<11; i++){
            int number1=baseNumber;
            int number2=(int)(Math.random()*7)+3;
            boolean correct=Math.random()*10<5;
            int result = number1 * number2;
            if (!correct){
                result = number1 + number2;
            }
            String question = String.format("%d x %d = %d",number1,number2,result);
            System.out.println("checking position "+(curPosition+1)+" "+(savedInstanceState!=null));
            if (curPosition-1 == i && savedInstanceState!=null){

                question = savedInstanceState.getString("LAST_QUESTION",question);
                correct = savedInstanceState.getBoolean("LAST_ANSWER");
            }
            questions.add(new Question(question,correct));
        }
    }

    public void findViews(){
        mButton1True = findViewById(R.id.button1a);
        mButton1False = findViewById(R.id.button1b);
        mButton2True = findViewById(R.id.button2a);
        mButton2False = findViewById(R.id.button2b);
        mText1 = findViewById(R.id.text_a);
        mText2 = findViewById(R.id.text_b);
        status1 = findViewById(R.id.status_a);
        status2 = findViewById(R.id.status_b);
        mBar1 = findViewById(R.id.progressBar_a);
        mBar2 = findViewById(R.id.progressBar_b);

    }

    public void setListeners(){
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
    }

    public void nextQuestion(){
        curPosition++;
        if (curPosition == questions.size()){
            System.out.println("Sending Result with data: "+String.format("%d/%d",points1,points2));
            Intent data= new Intent();
            data.putExtra("POINTS1",points1);
            data.putExtra("POINTS2",points2);

            if (points1 > points2){
                setResult(1, data);
            }else if(points1 > points2){
                setResult(2, data);
            }else{
                setResult(0, data);
            }
            finish();
            return;
        }
        mBar1.setProgress(curPosition+1);
        mBar2.setProgress(curPosition+1);
        current=questions.get(curPosition);
        mText1.setText(current.getText());
        mText2.setText(current.getText());
        status1.setBackgroundColor(Color.GRAY);
        status2.setBackgroundColor(Color.GRAY);
        activateButtons(true);
        this.setTitle(String.format("MathContest - Question %d",curPosition+1));

        updateScreen();
        answered = false;
    }

    private void activateButtons(boolean active){
        mButton1True.setEnabled(active);
        mButton1False.setEnabled(active);
        mButton2True.setEnabled(active);
        mButton2False.setEnabled(active);
    }

    private void updateScreen(){
        status1.setText(String.format("%d points",points1));
        status2.setText(String.format("%d points",points2));
    }


    private void answer(int player, boolean answer){
        // Toast.makeText(this, String.format("Player %d response %s",player,answer), Toast.LENGTH_SHORT).show();
        System.out.println(String.format("Player %d response %s",player,answer));
        if (!answered){
            if (player == 1){
                if (current.getAnswer()==answer){
                    points1++;
                    status1.setBackgroundColor(Color.GREEN);
                }else{
                    status1.setBackgroundColor(Color.RED);
                    points1--;
                }
            }
            if (player == 2){
                if (current.getAnswer()==answer){
                    points2++;
                    status2.setBackgroundColor(Color.GREEN);
                }else{
                    status2.setBackgroundColor(Color.RED);
                    points2--;
                }
            }
        }else{
            if (player == 1){
                status1.setBackgroundColor(Color.LTGRAY);
            }
            if (player == 2){
                status2.setBackgroundColor(Color.LTGRAY);
            }
        }
        updateScreen();
        activateButtons(false);
        answered = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nextQuestion();
                    }
                });
            }
        }, 2000);
    }
}
