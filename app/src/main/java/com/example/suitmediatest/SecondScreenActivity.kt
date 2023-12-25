package com.example.suitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SecondScreenActivity : AppCompatActivity() {
    lateinit var tvUsername : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        val username = intent.getStringExtra("USERNAME")

        tvUsername = findViewById(R.id.tvUsername)
        tvUsername.text = username
    }

    fun btnChooseUser(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun intentBack(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}