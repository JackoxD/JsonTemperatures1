package com.example.jsontemperatures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class MainActivity : AppCompatActivity() {


    lateinit var lastMeasureTime: String
    lateinit var lastTempInside: String
    lateinit var lastTempOutside: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mActualTime = findViewById<TextView?>(R.id.txtActualTime)
        val mActualInside = findViewById<TextView?>(R.id.txtActualInside)
        val mActualOutside = findViewById<TextView?>(R.id.txtActualOutside)

        //Inicjalizacja tekstów TextView
        mActualTime?.setText(R.string.DataWait)
        mActualInside?.text = ""
        mActualOutside?.text = ""

        if(savedInstanceState!=null){
            val date = savedInstanceState?.getCharSequence("date")
            mActualTime?.text = String.format(
                getString(
                    R.string.ActualTime
                ), date
            )

            val inside = savedInstanceState?.getCharSequence("inside")
            mActualInside?.text = String.format(
                getString(
                    R.string.ActualInside
                ), inside
            )

            val outside = savedInstanceState?.getCharSequence("outside")
            mActualOutside?.text = String.format(
                getString(
                    R.string.ActualOutside
                ), outside
            )
        }



        //---Ustawienie Buttonow na nieklikalne
        btnPlots.alpha = 0.5f
        btnTemperatures.alpha = 0.5f
        btnPlots.isClickable = false
        btnTemperatures.isClickable = false


        //---Funkcja uaktywniająca buttony
        fun buttonOnClick() {
            btnPlots.setOnClickListener {
                val intent = Intent(this, Plots::class.java)
                startActivity(intent)
            }

            btnTemperatures.setOnClickListener {
                val intent = Intent(this, Temperatures::class.java)
                this.startActivity(intent)
            }
            btnPlots.alpha = 1f
            btnTemperatures.alpha = 1f
        }

        //----LABELE Z AKTUALNYM CZASEM



        //-----JSON
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://conflagrant-sanddollar-4572.dataplicity.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val jsonTemperatureHolder = retrofit.create(
                JsonTemperatureHolder::class.java
            )
            val call: Call<List<DataTemperatures>> = jsonTemperatureHolder.getTemperatures()

            call.enqueue(

                object : Callback<List<DataTemperatures>> {
                    override fun onResponse(
                        call: Call<List<DataTemperatures>>,
                        response: Response<List<DataTemperatures>>
                    ) {
                        //Brak odpowiedzi
                        if (!response.isSuccessful) {
                            // Toast.makeText(this@MainActivity, "code: " + response.code(), Toast.LENGTH_LONG).show()
                            mActualTime?.setText(R.string.NoData)
                            mActualInside?.text = ""
                            mActualOutside?.text = ""
                            return
                        }
                        //Odpowiedz OK!
                        DataTemperatures.temperatures = response.body()!!
                        ///----LABELE Z AKTUALNA TEMPERATURA
                        lastMeasureTime = DataTemperatures.temperatures.last().measureTime
                        lastTempInside =  DataTemperatures.temperatures.last().temperatureInsideCelcious
                        lastTempOutside = DataTemperatures.temperatures.last().temperatureOutsideCelcious
                        mActualTime?.text = String.format(
                            getString(
                                R.string.ActualTime
                            ), lastMeasureTime
                        )
                        mActualInside?.text = String.format(
                            getString(
                                R.string.ActualInside
                            ), lastTempInside
                        )
                        mActualOutside?.text = String.format(
                            getString(
                                R.string.ActualOutside
                            ), lastTempOutside
                        )
                        //Uaktywnienie Buttonów
                        buttonOnClick()
                    }
                    //Błąd połączenia
                    override fun onFailure(call: Call<List<DataTemperatures>>, t: Throwable) {
                        mActualTime?.setText(R.string.NoData)
                        mActualInside?.text = ""
                        mActualOutside?.text = ""
                        // Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                }
            )

        }catch(e: Exception){ //Na wszelki wypadek (głównie błędny adres URL)
            mActualTime?.setText(R.string.NoData)
            mActualInside?.text = ""
            mActualOutside?.text = ""
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        if(::lastMeasureTime.isInitialized) {
            super.onSaveInstanceState(outState)
            val date = lastMeasureTime
            outState.putCharSequence("date", date)
            val inside = lastTempInside
            outState.putCharSequence("inside", inside)
            val outside = lastTempOutside
            outState.putCharSequence("outside", outside)
        }
    }



}
