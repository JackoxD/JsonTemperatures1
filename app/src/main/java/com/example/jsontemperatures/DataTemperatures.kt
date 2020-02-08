package com.example.jsontemperatures

//Klasa z danymi
data class DataTemperatures(val measureTime: String, val temperatureOutsideCelcious: String, val temperatureInsideCelcious: String){
    companion object{
        lateinit var temperatures: List<DataTemperatures>
    }
}