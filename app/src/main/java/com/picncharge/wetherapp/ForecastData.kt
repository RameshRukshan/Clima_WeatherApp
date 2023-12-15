package com.picncharge.wetherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ForecastData : AppCompatActivity() {

    private lateinit var ForecastAdaptor: ForecastAdaptor
    private val weatherList = ArrayList<forecasting>()

    lateinit var btn_back : Button

    var temp = ""
    var humidity = ""
    var wind = ""
    var press = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//---------------------------------------- Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//------------------------------------

        setContentView(R.layout.activity_forecast_data)
        var city = intent.getStringExtra("City")
        temp = intent.getStringExtra("temp").toString()
        humidity = intent.getStringExtra("humidity").toString()
        wind = intent.getStringExtra("wind").toString()
        press = intent.getStringExtra("press").toString()

        //fetchDataM()


        btn_back = findViewById(R.id.btn_back_to_home)

        btn_back.setOnClickListener(){
            var go_to_home = Intent(this, Home::class.java)
            go_to_home.putExtra("City", city)
            startActivity(go_to_home)
        }

        fetchRecyclerData(city.toString()) { jsonString ->
            if (jsonString != null) {
                Log.e("JSON Response", jsonString)
                parseJsonResponse(jsonString)
            } else {
                Log.e("Error", "Error fetching data")
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.weather_forecast)
        ForecastAdaptor = ForecastAdaptor(weatherList)
        recyclerView.adapter = ForecastAdaptor

    }

    private fun parseJsonResponse(jsonResponse: String) {

        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("list")

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val dtTxt = item.getString("dt_txt")
                val temp = item.getJSONObject("main").getString("temp")
                val icon = item.getJSONArray("weather").getJSONObject(0).getString("icon")
                val main = item.getJSONArray("weather").getJSONObject(0).getString("main")

                val iconId = getIconResourceId(icon)

                Log.d("DataAdaptor","Date $dtTxt")

                weatherList.add(forecasting(dtTxt, temp, iconId))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getIconResourceId(icon: String): Int {
        return resources.getIdentifier("ic_$icon", "drawable", packageName)
    }

    fun fetchRecyclerData(city: String, listener: (String?) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=13743e65600c3dd86e8905a8b8f82bc0"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { data ->
                val jsonString = data.toString()
                listener.invoke(jsonString)
            },
            { error ->
                Log.e("Response", error.toString())
                listener.invoke(null)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    fun fetchDataM(){
        var txt_temp1 : TextView = findViewById(R.id.txt_temperature)
        var txt_humi1 : TextView = findViewById(R.id.txt_humidity)
        var txt_wind1 : TextView = findViewById(R.id.txt_humidity)
        var txt_press1 : TextView = findViewById(R.id.txt_press)
        txt_temp1.setText(temp)
        txt_humi1.setText(humidity)

        var txt_temp2 : TextView = findViewById(R.id.txt_temperature2)
        var txt_humi2 : TextView = findViewById(R.id.txt_humidity2)
        var txt_wind2 : TextView = findViewById(R.id.txt_humidity2)
        var txt_press2 : TextView = findViewById(R.id.txt_press2)
        txt_temp2.setText(temp)
        txt_humi2.setText(humidity)

        var txt_temp3 : TextView = findViewById(R.id.txt_temperature3)
        var txt_humi3 : TextView = findViewById(R.id.txt_humidity3)
        var txt_wind3 : TextView = findViewById(R.id.txt_humidity3)
        var txt_press3 : TextView = findViewById(R.id.txt_press3)
        txt_temp3.setText(temp)
        txt_humi3.setText(humidity)

        var txt_temp4 : TextView = findViewById(R.id.txt_temperature4)
        var txt_humi4 : TextView = findViewById(R.id.txt_humidity4)
        var txt_wind4 : TextView = findViewById(R.id.txt_humidity4)
        var txt_press4 : TextView = findViewById(R.id.txt_press4)
        txt_temp4.setText(temp)
        txt_humi4.setText(humidity)

        var txt_temp5 : TextView = findViewById(R.id.txt_temperature5)
        var txt_humi5 : TextView = findViewById(R.id.txt_humidity5)
        var txt_wind5 : TextView = findViewById(R.id.txt_humidity5)
        var txt_press5 : TextView = findViewById(R.id.txt_press5)
        txt_temp5.setText(temp)
        txt_humi5.setText(humidity)
    }
}