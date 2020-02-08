package com.example.jsontemperatures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Temperatures : AppCompatActivity() {

        lateinit var adapter: TemperaturesListAdapter


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperatures)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.TitleOfTemperatures)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)




        //Tylko załadowanie danych do RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.temperaturesRecyclerView)

        loadDataToRecyclerView(recyclerView, DataTemperatures.temperatures )

    }
    fun loadDataToRecyclerView(rW: RecyclerView, temperatures: List<DataTemperatures>){
        rW.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = TemperaturesListAdapter(temperatures)
        rW.adapter = adapter
    }
}


