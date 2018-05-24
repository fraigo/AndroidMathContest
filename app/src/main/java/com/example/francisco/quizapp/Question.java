package com.example.francisco.quizapp;

/**
 * Created by francisco on 2018-05-23.
 */

class Question {

    private String text;
    private boolean answer;

    public Question(String text, boolean answer){
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public boolean getAnswer() {
        return answer;
    }

}
