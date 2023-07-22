package com.example.mathematicstoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class double_integral_solution : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_integral_solution)
        val function =getIntent().getStringExtra("Equation")
        val result = getIntent().getStringExtra("ans")
        val xa =getIntent().getStringExtra("xa")
        val xb =getIntent().getStringExtra("xb")
        val ya =getIntent().getStringExtra("ya")
        val yb = getIntent().getStringExtra("yb")
        findViewById<TextView>(R.id.equation).text="("+function+")"+"dxdy = "
        findViewById<TextView>(R.id.ans).text= result
        findViewById<TextView>(R.id.xb).text = xb
        findViewById<TextView>(R.id.xa).text = xa
        findViewById<TextView>(R.id.ya).text =ya
        findViewById<TextView>(R.id.yb).text =yb
    }
}