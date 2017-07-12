package com.example.dfrank.quickmath;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
    ArrayList<Integer> answers = new ArrayList<>();
    TextView displayAnswer, pointView, sumView, timer;
    int locationOfAnswer, score=0, numberOfQuestion=0;
    Button playAgain, button0, button1, button2, button3, exit;
    LinearLayout linear;
    long times = 0;
    CountDownTimer countDownTimer;
    View view;
    MediaPlayer mp;

    // Using sharedPreferences for selection of time
    public void selectTime(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String select = preferences.getString("list", "2");
        if (select.equals("1")){
            times=10100;
        }else if (select.equals("2")){
            times=20100;
        }else if (select.equals("3")){
            times=30100;
        }else if (select.equals("4")){
            times=40100;
        }else if (select.equals("5")){
            times=50100;
        }else if (select.equals("6")){
            times=60100;
        }
    }
    //method that generates questions
    public void generateQuestion(){
        Random random = new Random();
        int num1 = random.nextInt(21);
        int num2 = random.nextInt(21);
        sumView.setText(Integer.toString(num1)+ " + " + Integer.toString(num2));
        locationOfAnswer = random.nextInt(4);
        answers.clear();
        int incorrectAnswer;
        for (int i=0;i<4;i++){
            if (i==locationOfAnswer){
                answers.add(num1+num2);
            }else {
                incorrectAnswer = random.nextInt(41);
                while (incorrectAnswer==num1+num2){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    //method that allows user to choose answers
    public void chooseAnswer(View v) {
       if (v.getTag().toString().equals(Integer.toString(locationOfAnswer))){
           displayAnswer.setText("Correct!");
           displayAnswer.setTextColor(getResources().getColor(R.color.green));
           score++;
       }else {
           displayAnswer.setText("Incorrect!");
           displayAnswer.setTextColor(getResources().getColor(R.color.red));
       }
       numberOfQuestion++;
        pointView.setText(score+"/"+numberOfQuestion);
        generateQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = (LinearLayout)findViewById(R.id.linear);
        playAgain = (Button)findViewById(R.id.button);
        button0 = (Button)findViewById(R.id.but1);
        button1 = (Button)findViewById(R.id.but2);
        button2 = (Button)findViewById(R.id.but3);
        button3 = (Button)findViewById(R.id.but4);
        exit = (Button)findViewById(R.id.quit);
        displayAnswer = (TextView)findViewById(R.id.answer);
        sumView =(TextView)findViewById(R.id.sumTextView);
        pointView = (TextView)findViewById(R.id.pointView);
        timer = (TextView)findViewById(R.id.timer);
        generateQuestion();
        selectTime();
        countDown();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(v);
                
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                numberOfQuestion = 0;
                displayAnswer.setText("0/0");
                pointView.setText("0/0");
                generateQuestion();
                linear.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.INVISIBLE);
                sumView.setVisibility(View.VISIBLE);
                exit.setVisibility(View.INVISIBLE);
                timer.setTextColor(getResources().getColor(R.color.black));
                selectTime();
                againTime();

            }
        });



    }
    //countdown method for the play again method.
    public void againTime (){
       countDownTimer = new CountDownTimer(times, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int color =((int)(millisUntilFinished/1000));
                if (color<10){
                    timer.setTextColor(getResources().getColor(R.color.colorAccent));
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mp.start();
                }
                timer.setText(color+"s");
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                displayAnswer.setText(score+"/"+numberOfQuestion);
                linear.setVisibility(View.INVISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                sumView.setVisibility(View.INVISIBLE);
                exit.setVisibility(View.VISIBLE);


            }
        }.start();
    }
    // the onCreate countdown method
    void countDown(){
         countDownTimer = new CountDownTimer(times, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int color =((int)(millisUntilFinished/1000));
                if (color<10){
                    timer.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                timer.setText(color+"s");
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                displayAnswer.setText(score+"/"+numberOfQuestion);
                linear.setVisibility(View.INVISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                sumView.setVisibility(View.INVISIBLE);
                exit.setVisibility(View.VISIBLE);


            }
        }.start();
    }
    public void dialog(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit");
        builder.setMessage("Do you want to exit this app");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.settings){
            Intent intent = new Intent(this, Pref.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        dialog(view);
    }
}

