package com.example.jsontemperatures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_plots.*
import java.lang.Exception
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class Plots : AppCompatActivity() {


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plots)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "New Activity"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        lineChart.setNoDataText(getString(R.string.DataWait))


        val txtFoundData = findViewById<TextView>(R.id.txtFoundData)
        txtFoundData?.setText(R.string.DataWait)
        try {
            val entries = ArrayList<Entry>()
            val entries2 = ArrayList<Entry>()
            val xValues = ArrayList<String>()
            txtFoundData?.text = ""
                for (i in DataTemperatures.temperatures.indices) {
                entries.add(
                    Entry(
                        i + 1f,
                        DataTemperatures.temperatures[i].temperatureOutsideCelcious.toFloat()
                    )
                )
                entries2.add(
                    Entry(
                        i + 1f,
                        DataTemperatures.temperatures[i].temperatureInsideCelcious.toFloat()
                    )
                )
                    var xValuesSinAno = DataTemperatures.temperatures[i].measureTime.removeRange(0..4)
                    xValues.add(
                        xValuesSinAno.removeRange(11..13)
                )
            }

            lineChart.xAxis.granularity=1f
            lineChart.xAxis.setValueFormatter(LargeValueFormatter())
            lineChart.xAxis.isGranularityEnabled = true
            lineChart.xAxis.setCenterAxisLabels(true)
            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xValues)

            val vl = LineDataSet(entries, getString(R.string.TempOutside))
            val vl2 = LineDataSet(entries2, getString(R.string.TempInside))


            vl.setDrawValues(false)
            vl.setDrawFilled(false)
            vl.lineWidth = 1f
            vl.setDrawCircles(false)
            vl.color = R.color.colorAccent

            vl2.setDrawValues(false)
            vl2.setDrawFilled(false)
            vl2.lineWidth = 1f
            vl2.setDrawCircles(false)



            lineChart.xAxis.labelRotationAngle = 60f
            lineChart.xAxis.mAxisMaximum = 12f
            lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            lineChart.data = LineData(vl, vl2)

            lineChart.axisRight.isEnabled = true
            lineChart.xAxis.axisMaximum = DataTemperatures.temperatures.size + 0.5f



            lineChart.setTouchEnabled(true)
            lineChart.setPinchZoom(true)

            lineChart.description.text = getString(R.string.Time)

            //    lineChart.animateX(1800, Easing.EaseInExpo)


        } catch (e: Exception){
            txtFoundData?.setText(R.string.NoData)
        }
    }
}
