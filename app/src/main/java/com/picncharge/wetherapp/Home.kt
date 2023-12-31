package com.picncharge.wetherapp

import android.content.Context
import android.content.Intent
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
    lateinit var txt_sea_level : TextView
    lateinit var txt_ground_level : TextView
    lateinit var txt_pres : TextView

    lateinit var inp_city : EditText
    lateinit var btn_search : Button

    var temp = 0;
    var humidityI = 0;
    var press = 0;
    var wind = 0;

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
        txt_sea_level = findViewById(R.id.txt_sea_level)
        txt_ground_level = findViewById(R.id.txt_ground_level)
        txt_pres = findViewById(R.id.txt_preasure)

        w_s_icon = findViewById(R.id.icon)

        //loadCityData("Colombo")

        loadCityData(city.toString())

        btn_search = findViewById(R.id.btn_search)
        btn_show_more = findViewById(R.id.btn_show_more)

        btn_search.setOnClickListener(){
            inp_city = findViewById(R.id.input_city)

            city = inp_city.text.toString()

            loadCityData(city.toString())

            updateCurrentCity(city.toString())
        }

        btn_show_more.setOnClickListener(){
            var go_to_forecast = Intent(this, ForecastData::class.java)
            go_to_forecast.putExtra("City", city)
            go_to_forecast.putExtra("temp", temp)
            go_to_forecast.putExtra("humidity", humidityI)
            go_to_forecast.putExtra("wind", wind)
            go_to_forecast.putExtra("press", press)
            startActivity(go_to_forecast)
        }

        //https://api.openweathermap.org/data/2.5/forecast?q=Colombo&appid=8986a26ec8e3a1442e536cae551826b2

    }

    fun loadCityData(city: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=8986a26ec8e3a1442e536cae551826b2"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { data ->
            Log.e("Response", data.toString())

            try {
                val temperatureKelvin = data.getJSONObject("main").getDouble("temp")

                val temperatureCelsius = temperatureKelvin - 273.15
                temp = temperatureCelsius.toInt()
                txt_temperature.text = String.format("%.2f °C", temperatureCelsius)

                val weatherArray = data.getJSONArray("weather")
                if (weatherArray.length() > 0) {
                    val main = weatherArray.getJSONObject(0).getString("main")
                    val description = weatherArray.getJSONObject(0).getString("description")
                    val condition = "$main \n $description"
                    txt_weather_condition.text = condition
                }

                val humidity = data.getJSONObject("main").getInt("humidity")
                humidityI = humidity.toInt()
                txt_humidity.text = "$humidity%"

                val seaL = data.getJSONObject("main").getInt("sea_level")
                txt_sea_level.text = "$seaL meters"

                val grdLevel = data.getJSONObject("main").getInt("grnd_level")
                txt_ground_level.text = "$grdLevel meters"

                val preass = data.getJSONObject("main").getInt("pressure")
                press = preass.toInt()
                txt_pres.text = "$preass mb"

                val windSpeed = data.getJSONObject("wind").getDouble("speed")
                wind = windSpeed.toInt()
                txt_wind_speed.text = String.format("%.2f m/s", windSpeed)

                val imageURL = "https://openweathermap.org/img/w/" + weatherArray.getJSONObject(0).getString("icon") + ".png"
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