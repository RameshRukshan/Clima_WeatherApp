package com.picncharge.wetherapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ForecastAdaptor(private val weatherList : ArrayList<forecasting>) :
    RecyclerView.Adapter<ForecastAdaptor.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_forecast_data, parent, false )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = weatherList[position]
        holder.titleImage.setImageResource(currentItem.iconId)
        holder.tvTime.text = currentItem.time
        holder.tvTemperature.text = currentItem.temp

        Log.d("Forcast Adaptor Test", "Binding to positions $position")
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleImage : ImageView = itemView.findViewById(R.id.img)
        val tvTime : TextView = itemView.findViewById(R.id.txt_date_time)
        val tvTemperature : TextView = itemView.findViewById(R.id.txt_temperature)
    }
}