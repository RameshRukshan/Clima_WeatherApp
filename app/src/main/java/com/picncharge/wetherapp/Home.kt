package com.picncharge.wetherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var city = intent.getStringExtra("City")
        var cityDisplay = findViewById<TextView>(R.id.city)
        cityDisplay.setText(city)
    }
}