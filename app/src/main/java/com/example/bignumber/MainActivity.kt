package com.example.bignumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Random

data class WordDefinition(val word: String, val definition: String);

class MainActivity : AppCompatActivity() {
    private var dataDefList = ArrayList<String>();
    private lateinit var myAdapter : ArrayAdapter<String>;
    private var leftNum :Int = 0;
    private var rightNum :Int = 0;
    private var score :Int = 0;

    private var wordDefinitions = mutableListOf<WordDefinition>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         // above init our app ui


        loadDefinitions();
        pickRandomNumbers();
        setupList();

        val def_list = findViewById<ListView>(R.id.dynamic_def_list);
        def_list.setOnItemClickListener { _, _, index, _ ->
            dataDefList.removeAt(index);
            myAdapter.notifyDataSetChanged();
        };
    }

    fun setupList()
    {
        refreshWordAndDefinitions()

        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataDefList);

        val def_list = findViewById<ListView>(R.id.dynamic_def_list);
        def_list.adapter = myAdapter;
    }

    fun loadDefinitions()
    {
        wordDefinitions.add(
            WordDefinition(
            "simple",
            "easy to understand")
        );

        wordDefinitions.add(WordDefinition(
            "game engine",
            "library that manages the lifetime of the game"));

        wordDefinitions.add(WordDefinition(
            "kotlin",
            "programming language"));

        wordDefinitions.add(WordDefinition(
            "Love2D",
            "game framework for lua"));
    }

    fun refreshWordAndDefinitions()
    {
        findViewById<TextView>(R.id.word_id).text = wordDefinitions[0].word;

        dataDefList.clear();

        for(wd in wordDefinitions){
            dataDefList.add(wd.definition)
        }

        dataDefList.shuffle()
    }

    fun radioButtonOnClick(view: View)
    {
        /*if (view.id == R.id.rb_one)
        {
            var tv = findViewById<TextView>(R.id.HelloText);
            tv.text = "Radio Button One";
        }

        if (view.id == R.id.rb_two)
        {
            var tv = findViewById<TextView>(R.id.HelloText);
            tv.text = "Radio Button Two";
        }*/
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
        /*var leftButton = findViewById<Button>(R.id.left_number_button);
        var rightButton = findViewById<Button>(R.id.right_number_button);

        var rand = Random();

        leftNum = rand.nextInt(10);
        rightNum = rand.nextInt(10);

        leftButton.text = "$leftNum";
        rightButton.text = "$rightNum";*/
    }

}