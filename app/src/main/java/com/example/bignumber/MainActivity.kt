package com.example.bignumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var leftNum :Int = 0;
    private var rightNum :Int = 0;
    private var score :Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         // above init our app ui


        pickRandomNumbers();
    }

    fun leftButtonOnClick(view: View)
    {
        if (leftNum > rightNum)
            score++;
        else
            score--;

        updateScoreText();
        pickRandomNumbers();
    }

    fun rightButtonOnClick(view: View)
    {
        if (leftNum < rightNum)
            score++;
        else
            score--;

        updateScoreText();
        pickRandomNumbers();
    }

    fun pickRandomNumbers()
    {
        var leftButton = findViewById<Button>(R.id.left_number_button);
        var rightButton = findViewById<Button>(R.id.right_number_button);

        var rand = Random();

        leftNum = rand.nextInt(10);
        rightNum = rand.nextInt(10);

        leftButton.text = "$leftNum";
        rightButton.text = "$rightNum";
    }

    fun updateScoreText()
    {
        var scoreText = findViewById<TextView>(R.id.score_text);

        scoreText.text = "Score: $score";
    }

}