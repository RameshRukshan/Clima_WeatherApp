package com.picncharge.wetherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class WeatherForecast : AppCompatActivity() {


    private lateinit var ForecastAdaptor: ForecastAdaptor
    private val weatherList = ArrayList<forecasting>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//---------------------------------------- Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//------------------------------------

        setContentView(R.layout.activity_weather_forecast)

        val recyclerView: RecyclerView = findViewById(R.id.weather_forecast)
        ForecastAdaptor = ForecastAdaptor(weatherList)
        recyclerView.adapter = ForecastAdaptor
    }

    private fun parseJsonResponse(jsonResponse: String) {
        // Parse JSON and populate weatherList
        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("list")

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val dayFilter = item.getString("dt_txt")
                //if(filterForToday(dayFilter)){
                val dtTxt = item.getString("dt_txt")
                val temp = item.getJSONObject("main").getString("temp")
                val icon = item.getJSONArray("weather").getJSONObject(0).getString("icon")
                val main = item.getJSONArray("weather").getJSONObject(0).getString("main")

                // Convert icon to drawable resource ID
                val iconId = getIconResourceId(icon)

                // Add Weather object to the list
                weatherList.add(forecasting(dtTxt, temp, iconId, main))
                //}
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getIconResourceId(icon: String): Int {
        // Convert icon string to drawable resource ID (you may need to have corresponding icons in your resources)
        // For example, if your icons are named "ic_rain", "ic_cloud", etc.
        return resources.getIdentifier("ic_$icon", "drawable", packageName)
    }

    fun fetchRecyclerData(city: String, listener: (String?) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=13743e65600c3dd86e8905a8b8f82bc0"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { data ->
                // Convert the entire JSON response to a string
                val jsonString = data.toString()

                // Invoke the listener with the JSON string
                listener.invoke(jsonString)
            },
            { error ->
                // Handle error
                Log.e("Response", error.toString())

                // Invoke the listener with null to indicate an error
                listener.invoke(null)
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}