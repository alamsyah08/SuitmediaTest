package com.example.suitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var tvUsername : TextView
    private lateinit var tvSelectUser : TextView

    private lateinit var sessionManager: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        sessionManager = SharePreference(this)

        val selectUser = intent.getStringExtra("SELECTUSER")
        val retrievedUsername: String = sessionManager.getUsername().toString()

        tvUsername = findViewById(R.id.tvUsername)
        tvUsername.text = retrievedUsername

        tvSelectUser = findViewById(R.id.tvSelectedUser)
        if(selectUser != null){
            tvSelectUser.text = selectUser
        }else{
            tvSelectUser.text = "Selected User Name"
        }


    }

    fun btnChooseUser(view: View){
        val intent = Intent(this, ThirdScreenActivity::class.java)
        startActivity(intent)
    }

    fun intentBack(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}