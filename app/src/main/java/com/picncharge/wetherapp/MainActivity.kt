package com.picncharge.wetherapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var btn_continue : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//---------------------------------------- Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//------------------------------------
        setContentView(R.layout.activity_main)

        btn_continue = findViewById(R.id.btn_continue)
        var txt_city = findViewById<TextView>(R.id.textView2)

        btn_continue.setOnClickListener(){
            val file:String = "tempCity.txt"
/*

---------------------WHEN INIT Setup This will be call
            val data:String = "Colombo"
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            }catch (e: Exception){
                e.printStackTrace()
            }
*/
            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(file)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            var city = (stringBuilder.toString()).toString()

            var go_to_home = Intent(this, Home::class.java)
            go_to_home.putExtra("City", city)
            startActivity(go_to_home)
        }

    }
}