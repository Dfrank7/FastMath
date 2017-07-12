package com.example.dfrank.quickmath;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dfrank on 6/6/17.
 */

public class Advance extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();
    ArrayList<Integer> answers = new ArrayList<>();
    TextView displayAnswer, pointView, mulView, timer;
    int locationOfAnswer, score=0, numberOfQuestion=0;
    Button playAgain, button0, button1, button2, button3, exit;
    LinearLayout linear;
    long times = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        linear = (LinearLayout)findViewById(R.id.linear);
        playAgain = (Button)findViewById(R.id.button);
        button0 = (Button)findViewById(R.id.but1);
        button1 = (Button)findViewById(R.id.but2);
        button2 = (Button)findViewById(R.id.but3);
        button3 = (Button)findViewById(R.id.but4);
        exit = (Button)findViewById(R.id.quit);
        displayAnswer = (TextView)findViewById(R.id.answer);
        mulView =(TextView)findViewById(R.id.sumTextView);
        pointView = (TextView)findViewById(R.id.pointView);
        timer = (TextView)findViewById(R.id.timer);
        generateQuestion();
        mainActivity.selectTime();
        mainActivity.countDown();

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
                mulView.setVisibility(View.VISIBLE);
                exit.setVisibility(View.INVISIBLE);
                timer.setTextColor(getResources().getColor(R.color.black));
                mainActivity.selectTime();
                mainActivity.againTime();
            }
        });


    }
    public void generateQuestion(){
        Random random = new Random();
        int num1 = random.nextInt(21);
        int num2 = random.nextInt(21);
        mulView.setText(String.valueOf(num1)+" x "+ String.valueOf(num2));
        locationOfAnswer = random.nextInt(4);
        answers.clear();
        for (int i =0; i<4; i++){
            if (i==locationOfAnswer){
                answers.add(num1*num2);
            }else {
                int inCorrectAnswer = random.nextInt(441);
                while (inCorrectAnswer==(num1*num2)){
                    inCorrectAnswer=random.nextInt(441);
                }
                answers.add(inCorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }


}
