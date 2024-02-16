package com.example.bignumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
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

    fun radioButtonOnClick(view: View)
    {
        if (view.id == R.id.rb_one)
        {
            var tv = findViewById<TextView>(R.id.HelloText);
            tv.text = "Radio Button One";
        }

        if (view.id == R.id.rb_two)
        {
            var tv = findViewById<TextView>(R.id.HelloText);
            tv.text = "Radio Button Two";
        }
    }

    fun leftButtonOnClick(view: View)
    {
        pickRandomNumbers();
    }

    fun rightButtonOnClick(view: View)
    {
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

}