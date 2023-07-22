package com.example.mathematicstoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FIrstDerivativeSolution : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_derivative_solution)
        val result = getIntent().getStringExtra("answer")
        findViewById<TextView>(R.id.ans).text = result
        findViewById<TextView>(R.id.equation).text = getIntent().getStringExtra("fun")
        findViewById<TextView>(R.id.X).text = getIntent().getStringExtra("X")
    }
}
