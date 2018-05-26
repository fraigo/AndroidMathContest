package com.example.francisco.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    public static final int RESULT_WINNER = 1;
    Button currentButton;
    int totalPoints1, totalPoints2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalPoints1 =0;
        totalPoints2 =0;

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_splash);

        configButton(R.id.button_2,2);
        configButton(R.id.button_3,3);
        configButton(R.id.button_4,4);
        configButton(R.id.button_5,5);
        configButton(R.id.button_6,6);
        configButton(R.id.button_7,7);
        configButton(R.id.button_8,8);
        configButton(R.id.button_9,9);
        configButton(R.id.button_11,11);

    }

    private void configButton(int id, final int number) {
        final Button mButtonStart = findViewById(id);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("COUNTER", number);
                currentButton = mButtonStart;
                startActivityForResult(intent, number);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Result "+ requestCode+ ":" + resultCode);
        int points1=data.getIntExtra("POINTS1",0);
        int points2=data.getIntExtra("POINTS2",0);
        currentButton.setText(requestCode+"\n\n"+points1+"-"+points2);
    }
}
