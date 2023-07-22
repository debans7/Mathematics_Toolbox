package com.example.mathematicstoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class FirstDerivative : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_derivative)
        val function = findViewById<EditText>(R.id.function)
        val x = findViewById<EditText>(R.id.x)
        val h = findViewById<EditText>(R.id.h)
        val reset = findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            reset(function,x,h)
        }
        val button = findViewById<Button>(R.id.button).setOnClickListener {
            if(!notFilled(function,x)){
                solve(function,x,h) }
            else{
                Toast.makeText(this@FirstDerivative, "Please fill appropriate information", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun solve(function: EditText, X: EditText, H: EditText) {
        lateinit var exp : Expression
        try {
            exp = ExpressionBuilder(function.getText().toString()).variable("x").build()
        }
        catch (e : Exception){
            Toast.makeText(this, "Please provide appropriate function", Toast.LENGTH_SHORT).show()
            return
        }
        val x = X.getText().toString().toDouble()
        val h = H.getText().toString().toDouble()

        val xp : Double = exp.setVariable("x",x-h).evaluate()
        val xn : Double = exp.setVariable("x",x+h).evaluate()
        val ans ="%.${3}f".format((xn-xp)/(2*h))

        val intent = Intent(this,FIrstDerivativeSolution::class.java).apply{
            putExtra("answer",ans.toString())
            putExtra("fun",function.getText().toString())
            putExtra("X",x.toString())
        }
        startActivity(intent)

    }

    fun notFilled(function: EditText, x: EditText): Boolean{
        return (function.getText().toString().isEmpty() || x.getText().toString().isEmpty())
    }
    private fun reset(function: EditText, x: EditText, h: EditText) {
        function.getText().clear()
        x.getText().clear()
        h.setText("0.001")
    }
}
