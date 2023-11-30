package com.picncharge.wetherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class Home : AppCompatActivity() {

    lateinit var img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//---------------------------------------- Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//------------------------------------

        setContentView(R.layout.activity_home)
        var city = intent.getStringExtra("City")

        var cityDisplay = findViewById<TextView>(R.id.city)
        img = findViewById(R.id.img)

        cityDisplay.setText(city)

        //https://api.openweathermap.org/data/2.5/forecast?q=Colombo&appid=8986a26ec8e3a1442e536cae551826b2

    }

    private fun fetchCatImage() {
        Log.e("API Responce", "Getting Responces")

        var city = intent.getStringExtra("City")

        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=8986a26ec8e3a1442e536cae551826b2"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val imageURL = response.getJSONObject(0).getString("url")
                    Picasso.get().load(imageURL).into(img)
                } catch (e: Exception) {
                    Log.e("Exception", e.toString())
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error", error.toString())
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })

        Volley.newRequestQueue(this).add(request)
    }

}