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

class ODE2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ode2)
        val button = findViewById<Button>(R.id.button).setOnClickListener { solve() }
        val Rbutton = findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
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
    private fun notFilled():Boolean{
        return (findViewById<EditText>(R.id.function).getText().toString().length==0 || findViewById<EditText>(R.id.x0).getText().toString().length==0 || findViewById<EditText>(R.id.y0).getText().toString().length==0  || findViewById<EditText>(R.id.xn).getText().toString().length==0 || findViewById<EditText>(R.id.Y0).getText().toString().length==0)
    }
    fun solve(){

        if(notFilled()){
            Toast.makeText(this, "Please fill appropriate information", Toast.LENGTH_SHORT).show()
            return
        }
        val function = findViewById<EditText>(R.id.function).getText().toString().replace("y'","z")
        lateinit var expression_g : Expression

        try{
            expression_g = ExpressionBuilder(function).variable("x").variable("y").variable("z").build()
        } catch (e: Exception) {

            Toast.makeText(this, "Please enter appropriate expression", Toast.LENGTH_SHORT).show()
            return
        }
        val x0 = findViewById<EditText>(R.id.x0).getText().toString().toDouble()
        val xn = findViewById<EditText>(R.id.xn).getText().toString().toDouble()
        var y0 = findViewById<EditText>(R.id.y0).getText().toString().toDouble()
        var n = findViewById<EditText>(R.id.interval).getText().toString().toInt()
        var Y = findViewById<EditText>(R.id.Y0).getText().toString().toDouble()
        val h = (xn-x0)/n
        var Y0 = findViewById<EditText>(R.id.Y0).getText().toString().toDouble()
        val expression_f = ExpressionBuilder("z").variable("z").variable("x").variable("y").build()

        for (i in 0..n){
            val k1 = (expression_f.setVariable("x",x0 + (i*h)).setVariable("y",y0).setVariable("z",Y0).evaluate())*h
            val l1 = (expression_g.setVariable("x",x0 + (i*h)).setVariable("y",y0).setVariable("z",Y0).evaluate())*h

            val k2 = (expression_f.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0 + k1/2).setVariable("z",Y0 + l1/2).evaluate())*h
            val l2 = (expression_g.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0 + k1/2).setVariable("z",Y0 + l1/2).evaluate())*h

            val k3 = (expression_f.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0 + k2/2).setVariable("z",Y0 + l2/2).evaluate())*h
            val l3 = (expression_g.setVariable("x",x0 + (i*h) + h/2).setVariable("y",y0 + k2/2).setVariable("z",Y0 + l2/2).evaluate())*h

            val k4 = (expression_f.setVariable("x",x0 + (i*h) + h).setVariable("y",y0 + k3).setVariable("z",Y0 + l3).evaluate())*h
            val l4 = (expression_g.setVariable("x",x0 + (i*h) + h).setVariable("y",y0 + k3).setVariable("z",Y0 + l3).evaluate())*h

            y0 += (k1+2*k2+2*k3+k4)/6.0
            Y0 += (l1+2*l2+2*l3+l4)/6.0

        }
        val result ="%.${3}f".format(y0)
        val intent = Intent(this,ODE2_solution::class.java).apply{
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
        findViewById<EditText>(R.id.Y0).getText().clear()
        findViewById<EditText>(R.id.interval).setText(("100"))
    }
}