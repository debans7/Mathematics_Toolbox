package com.example.mathematicstoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Integration_solution : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integration_solution)
        val answer =getIntent().getStringExtra("Answer")
        val exp = getIntent().getStringExtra("Equation")
        val a = getIntent().getStringExtra("a")
        val b = getIntent().getStringExtra("b")

        findViewById<TextView>(R.id.equation).text="(" +exp+ ")dx = "
        findViewById<TextView>(R.id.ans).text= answer
        findViewById<TextView>(R.id.a).text = a
        findViewById<TextView>(R.id.b).text = b
    }
}