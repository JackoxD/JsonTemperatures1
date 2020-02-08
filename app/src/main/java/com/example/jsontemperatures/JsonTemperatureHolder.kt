package com.example.jsontemperatures

import retrofit2.Call
import retrofit2.http.GET

interface JsonTemperatureHolder {
    @GET("temperatura.json")
    fun getTemperatures(): Call<List<DataTemperatures>>
}