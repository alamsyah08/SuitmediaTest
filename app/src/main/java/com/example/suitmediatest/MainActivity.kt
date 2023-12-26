package com.example.suitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etNama : EditText
    private lateinit var etPalindrome : EditText

    private lateinit var sessionManager: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SharePreference(this)

        etNama = findViewById(R.id.etName)
        etPalindrome = findViewById(R.id.etPalindrome)
    }

    fun btnCheck(view: View){
        val originalString = etPalindrome.text.toString()
        val reversedString = originalString.reversed()
        if(originalString.isNotEmpty()){
            if(originalString == reversedString) {
                Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "not palindrome", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun intentNext(view: View){
        val nama = etNama.text.toString()
        if(nama.isNotEmpty()){
            val intent = Intent(this, SecondScreenActivity::class.java)
            sessionManager.saveUsername(nama)
            startActivity(intent)
        }

    }
}