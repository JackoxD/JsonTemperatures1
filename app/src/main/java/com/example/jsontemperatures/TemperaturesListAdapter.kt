package com.example.jsontemperatures

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Zmiana Mutable List na List
class TemperaturesListAdapter(var temperatures: List<DataTemperatures>): RecyclerView.Adapter<TemperaturesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.temperatures_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return temperatures.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var temperature = temperatures[position]
        holder.bind(temperature)
    }


            class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
                private val mtime = itemView.findViewById<TextView?>(R.id.txtViewTime)
                private val mtempOutside = itemView.findViewById<TextView?>(R.id.txtViewTempOutside)
                private val mtempInside = itemView.findViewById<TextView?>(R.id.txtViewTempInside)
                fun bind(temperature: DataTemperatures){
                    mtime?.text = temperature.measureTime
                    mtempOutside?.text = temperature.temperatureOutsideCelcious
                    mtempInside?.text = temperature.temperatureInsideCelcious
                }
            }
}




