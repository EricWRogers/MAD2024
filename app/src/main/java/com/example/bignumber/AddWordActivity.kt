package com.example.bignumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    fun submitOnClick(view: View)
    {
        print("hit")
        val word = findViewById<EditText>(R.id.word_edit_text).text.toString()
        val def = findViewById<EditText>(R.id.def_edit_text).text.toString()

        val myIntent = Intent();
        myIntent.putExtra("word", word)
        myIntent.putExtra("def", def)
        setResult(RESULT_OK, myIntent)
        finish()
    }
}