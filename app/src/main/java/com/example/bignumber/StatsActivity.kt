package com.example.bignumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val score = intent.getStringExtra("score")?:"0"
        val totalCorrect = intent.getStringExtra("totalCorrect")?:"0"
        val totalWrong = intent.getStringExtra("totalWrong")?:"0"

        val scoreText = findViewById<TextView>(R.id.score_text)
        val correctText = findViewById<TextView>(R.id.total_correct_text)
        val wrongText = findViewById<TextView>(R.id.total_wrong_text)

        scoreText.text = "Score: " + score
        correctText.text = "Correct: " + totalCorrect
        wrongText.text = "Wrong: " + totalWrong
    }

    fun backOnClick(view: View)
    {
        finish()
    }
}