package com.naulepilef.tip_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class MainActivity : AppCompatActivity() {
    private lateinit var textPercentage: TextView
    private lateinit var textTipValue: TextView
    private lateinit var textTotalValue: TextView

    private lateinit var inputValue: TextInputEditText

    private lateinit var seekBarPercentage: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findAllViews()
        setListeners()
        updateTextPercentage()
        calculateTip()
    }

    private fun calculateTip(){
        val valueText = inputValue.text.toString()
        if (valueText == "" || valueText == ".") {
            updateTipAndTotal(0.00, 0.00)
            return
        }

        val df = DecimalFormat("#.##")
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'
        df.decimalFormatSymbols = dfs

        val value: Double = valueText.toDouble()
        val percentage: Double = seekBarPercentage.progress.toDouble()

        val tip: Double = df.format(value * (percentage / 100)).toDouble()
        val total: Double = df.format(value + tip).toDouble()

        updateTipAndTotal(tip, total)
    }

    private fun findAllViews(){
        textPercentage = findViewById(R.id.text_percentage)
        textTipValue = findViewById(R.id.text_tip_value)
        textTotalValue = findViewById(R.id.text_total_value)

        inputValue = findViewById(R.id.input_value)

        seekBarPercentage = findViewById(R.id.seekBar_percentage)
    }

    private fun setListeners(){
        // When the user change the value of the seekBar
        seekBarPercentage.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateTextPercentage()
                calculateTip()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do nothing
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do nothing
            }
        })
        inputValue.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateTip()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })
    }

    private fun updateTextPercentage(){
        textPercentage.text = getString(R.string.main_text_percentage_value, seekBarPercentage.progress)
    }

    private fun updateTipAndTotal(tip: Double, total: Double){
        textTipValue.text = getString(R.string.text_money, tip)
        textTotalValue.text = getString(R.string.text_money, total)
    }
}