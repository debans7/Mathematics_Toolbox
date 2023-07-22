package com.example.mathematicstoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ODE2_solution : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ode2_solution)
        val result = getIntent().getStringExtra("answer")
        findViewById<TextView>(R.id.ans).text = result
        findViewById<TextView>(R.id.xn).text = "("+getIntent().getStringExtra("xn")+")"

    }
}