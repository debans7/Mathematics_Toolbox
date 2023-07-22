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

class ode1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ode1)
        val button = findViewById<Button>(R.id.button).setOnClickListener { solve() }
        val Rbutton = findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            reset()
        }

    findViewById<EditText>(R.id.interval).setOnFocusChangeListener{
        _, hasFocus ->
        if (hasFocus) {
            Toast.makeText(
                this,
                "Increasing intervals improves accuracy, but exceeding 1000 is not recommended due to device limitations",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
    fun notFilled():Boolean{
        return (findViewById<EditText>(R.id.function).getText().toString().length==0 || findViewById<EditText>(R.id.x0).getText().toString().length==0 || findViewById<EditText>(R.id.y0).getText().toString().length==0  || findViewById<EditText>(R.id.xn).getText().toString().length==0)
    }

    fun solve(){
        if(notFilled()){
            Toast.makeText(this, "Please fill appropriate information", Toast.LENGTH_SHORT).show()
            return
        }
        val function = findViewById<EditText>(R.id.function).getText().toString()

        lateinit var expression : Expression

        try{
            expression = ExpressionBuilder(function).variable("x").variable("y").build()
        } catch (e: Exception) {

            Toast.makeText(this, "Please enter appropriate expression", Toast.LENGTH_SHORT).show()
            return
        }

        val x0 = findViewById<EditText>(R.id.x0).getText().toString().toDouble()
        val xn = findViewById<EditText>(R.id.xn).getText().toString().toDouble()
        var y0 = findViewById<EditText>(R.id.y0).getText().toString().toDouble()
        var n = findViewById<EditText>(R.id.interval).getText().toString().toInt()


        val h = (xn-x0)/n
        var yp = y0
        for(i in 0..(n-1)){
            val k1 = (expression.setVariable("x",x0 + (i*h)).setVariable("y",y0).evaluate())*h
            val k2 = (expression.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0+ k1/2).evaluate())*h
            val k3 = (expression.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0 + k2/2).evaluate())*h
            val k4 = (expression.setVariable("x",x0 + (i*h) + h).setVariable("y",y0 + k3).evaluate())*h

            val k = (k1+2*k2+2*k3+k4)/6.0
            val ynext = y0+k
            y0 = ynext
        }
        val result ="%.${3}f".format(y0)

        val intent = Intent(this,ode1_solution::class.java).apply{
            putExtra("answer",result)
            putExtra("xn",xn.toString())
        }
        startActivity(intent)
    }

    fun reset(){
        findViewById<EditText>(R.id.function).getText().clear()
        findViewById<EditText>(R.id.x0).getText().clear()
        findViewById<EditText>(R.id.y0).getText().clear()
        findViewById<EditText>(R.id.xn).getText().clear()
        findViewById<EditText>(R.id.interval).setText("100")
    }
}
