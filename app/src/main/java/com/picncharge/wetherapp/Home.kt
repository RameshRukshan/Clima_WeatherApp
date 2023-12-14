package com.picncharge.wetherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import java.io.FileOutputStream

class Home : AppCompatActivity() {

    lateinit var w_s_icon : ImageView

    lateinit var btn_show_more : TextView

    lateinit var txt_city : TextView
    lateinit var txt_humidity : TextView
    lateinit var txt_wind_speed : TextView
    lateinit var txt_temperature : TextView
    lateinit var txt_weather_condition : TextView

    lateinit var inp_city : EditText
    lateinit var btn_search : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//---------------------------------------- Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//------------------------------------

        setContentView(R.layout.activity_home)
        var city = intent.getStringExtra("City")

        txt_city = findViewById(R.id.txt_city)
        txt_temperature = findViewById(R.id.txt_celcious)
        txt_humidity = findViewById(R.id.txt_humidity)
        txt_wind_speed = findViewById(R.id.txt_wind_speed)
        txt_weather_condition = findViewById(R.id.txt_weather_condition)

        w_s_icon = findViewById(R.id.icon)


        loadCityData(city.toString())

        btn_search = findViewById(R.id.btn_search)
        btn_show_more = findViewById(R.id.btn_show_more)

        btn_search.setOnClickListener(){
            inp_city = findViewById(R.id.input_city)

            city = txt_city.text.toString()

            loadCityData(city.toString())

            //updateCurrentCity(inp_city.toString())
        }

        btn_show_more.setOnClickListener(){

        }

        //https://api.openweathermap.org/data/2.5/forecast?q=Colombo&appid=8986a26ec8e3a1442e536cae551826b2

    }

    fun loadCityData(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=8986a26ec8e3a1442e536cae551826b2"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { data ->
            Log.e("Response", data.toString())

            try {
                // Extract temperature from main object (in Kelvin)
                val temperatureKelvin = data.getJSONObject("main").getDouble("temp")

                // Convert Kelvin to Celsius
                val temperatureCelsius = temperatureKelvin - 273.15
                // Display the temperature in Celsius in the TextView
                txt_temperature.text = String.format("%.2f °C", temperatureCelsius)

                // Extract Weather Description
                val weatherArray = data.getJSONArray("weather")
                if (weatherArray.length() > 0) {
                    val main = weatherArray.getJSONObject(0).getString("main")
                    val description = weatherArray.getJSONObject(0).getString("description")
                    val condition = "$main - $description"
                    txt_weather_condition.text = condition
                }

                // Extract Humidity
                val humidity = data.getJSONObject("main").getInt("humidity")
                txt_humidity.text = "$humidity%"

                // Extract Wind Speed
                val windSpeed = data.getJSONObject("wind").getDouble("speed")
                txt_wind_speed.text = String.format("%.2f m/s", windSpeed)

                // Load and display weather icon (similar to your existing code)
                val imageURL = "https://openweathermap.org/img/w/" +
                        weatherArray.getJSONObject(0).getString("icon") + ".png"
                Picasso.get().load(imageURL).into(w_s_icon)

                txt_city.text = city

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }) { error ->
            Log.e("Response", error.toString())
        }
        Volley.newRequestQueue(this).add(request)
    }

    fun updateCurrentCity(city : String){
        val file:String = "tempCity.txt"
        val data:String = city
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

}