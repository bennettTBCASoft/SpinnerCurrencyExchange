package com.example.recycleviewpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 這裡是RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            adapter = RecyclerViewAdapter(context, listOf("第一個", "第二個", "第三個", "第四個", "第五個"))
            layoutManager = LinearLayoutManager(context)
        }

        // 實作Spinner
        // 1. 把我想選擇的值丟進去Spinner & 可以把我選擇的值顯示出來
        var currencyArray = arrayListOf("TWD", "USA", "CNY", "HKD", "JPY")
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyArray)
        val spinner: Spinner = findViewById(R.id.spinner1)
        val spinner2: Spinner = findViewById(R.id.spinner2)
        spinner.adapter = adapter
        spinner2.adapter = adapter

        // 存放選擇好的spinner的值
        var selectCurrencyLeft: String = ""
        var selectCurrencyRight: String = ""

        // 實例 textView
        val textView: TextView = findViewById(R.id.textView2)
        // 實例 editText
        val editText: EditText = findViewById(R.id.editText1)

        // 2. 取選出來的值去做判斷->運算
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            // 當spinner 做出選擇時，會呼叫此函式
            override fun onItemSelected(p0: AdapterView<*>?, view: View, pos: Int, id: Long) {
                println(currencyArray[pos])
                selectCurrencyLeft = currencyArray[pos]

                // 存放運算好的匯率結果的變數
                var result: Double
                // 呼叫calculator函式去做運算，並把回傳的Double值丟到result
                result = calculatorCurrency(selectCurrencyLeft, selectCurrencyRight, Integer.parseInt(editText.text.toString()))
                textView.setText(String.format("%s", result))

                Toast.makeText(this@MainActivity, "你選的是" + currencyArray[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // 同上spinner
        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View, pos: Int, id: Long) {
                println(currencyArray[pos])
                selectCurrencyRight = currencyArray[pos]

                var result: Double
                result = calculatorCurrency(selectCurrencyLeft, selectCurrencyRight, Integer.parseInt(editText.text.toString()))
                textView.setText(String.format("%s", result))

                Toast.makeText(this@MainActivity, "你選的是" + currencyArray[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    // 做匯率計算的函式
    fun calculatorCurrency(currencyStrL: String, currencyStrR: String, editNumber: Int): Double {
        // 創一個結果變數，存放匯率轉換後的值
        var result: Double = 0.0

        // 這邊是判斷式
        when (currencyStrL){
            "TWD" -> {
                when (currencyStrR) {
                    "USA" -> result = editNumber * 0.035
                    "CNY" -> result = editNumber * 0.22
                    "HKD" -> result = editNumber * 0.28
                    "JPY" -> result = editNumber * 4.10
                    else -> println("error")
                }
            }
            "USA" -> {
                when (currencyStrR) {
                    "TWD" -> result = editNumber * 28.37
                    "CNY" -> result = editNumber * 6.32
                    "HKD" -> result = editNumber * 7.82
                    "JPY" -> result = editNumber * 116.26
                    else -> println("error")
                }
            }
            "CNY" -> {
                when (currencyStrR) {
                    "USA" -> result = editNumber * 0.16
                    "TWD" -> result = editNumber * 4.49
                    "HKD" -> result = editNumber * 1.24
                    "JPY" -> result = editNumber * 18.38
                    else -> println("error")
                }
            }
            "HKD" -> {
                when (currencyStrR) {
                    "USA" -> result = editNumber * 0.13
                    "CNY" -> result = editNumber * 0.81
                    "TWD" -> result = editNumber * 3.63
                    "JPY" -> result = editNumber * 14.86
                    else -> println("error")
                }
            }
            "JPY" -> {
                when (currencyStrR) {
                    "USA" -> result = editNumber * 0.0086
                    "CNY" -> result = editNumber * 0.054
                    "HKD" -> result = editNumber * 0.067
                    "TWD" -> result = editNumber * 0.24
                    else -> println("error")
                }
            }

            else -> print("error")
        }
        return result
    }
}

class RecyclerViewAdapter (private val context: Context, private val data: List<String>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textId: TextView = itemView.findViewById(R.id.textId)
        val btnClick: Button = itemView.findViewById(R.id.btnClick)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            textId.text = data[position]
            btnClick.setOnClickListener{
                println("${position + 1}")
            }
        }
    }

    override fun getItemCount(): Int = data.size

}