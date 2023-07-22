package com.example.mathematicstoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.Expression

class double_Integral : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_integral)
        findViewById<Button>(R.id.button).apply{
            setOnClickListener{
                solve()
            }
        }
        findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            reset()
        }
        findViewById<EditText>(R.id.interval).setOnFocusChangeListener{_,hasFocus ->
            if(hasFocus){
                Toast.makeText(this,
                        "Increasing intervals improves accuracy, but exceeding 1000 is not recommended due to device limitations", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun notFilled() : Boolean {
        return (findViewById<EditText>(R.id.function).text.toString().isEmpty() || findViewById<EditText>(R.id.upperLimitX).text.toString().isEmpty()  ||findViewById<EditText>(R.id.lowerLimitX).text.toString().isEmpty() || findViewById<EditText>(R.id.UpperLimitY).text.toString().isEmpty()
                || findViewById<EditText>(R.id.lowerLimitY).text.toString().isEmpty() )
    }

    fun solve(){
        if (notFilled()){
            Toast.makeText(this, "Please fill appropriate details", Toast.LENGTH_SHORT).show()
            return
        }
        val function = findViewById<EditText>(R.id.function).text.toString()


        val matrix : MutableList<MutableList<Double>> = mutableListOf()

        //function

        lateinit var expression :Expression
        try{
            expression = ExpressionBuilder(function).variable("x").variable("y").build()
        } catch (e: Exception) {

            Toast.makeText(this, "Please enter appropriate expression", Toast.LENGTH_SHORT).show()
            return
        }
        val upX = findViewById<EditText>(R.id.upperLimitX).text.toString().toDouble()
        val loX = findViewById<EditText>(R.id.lowerLimitX).text.toString().toDouble()
        val upY = findViewById<EditText>(R.id.UpperLimitY).text.toString().toDouble()
        val loY = findViewById<EditText>(R.id.lowerLimitY).text.toString().toDouble()
        var n = findViewById<EditText>(R.id.interval).text.toString().toInt()
        n += (n % 2)
        val h = (upX-loX)/n
        val k = (upY-loY)/n

        val matSize : Int = n+1
        var total =0.0
        for(i in 0 until matSize){
            val y = loY+(i*k)
            val tempList :MutableList<Double> = mutableListOf()
            for(j in 0 until matSize){
                val x = loX+(j*h)
                val result = expression.setVariable("x",x).setVariable("y",y).evaluate()
                tempList.add(result)
                total += result
            }
            matrix.add(tempList)
        }

        val corner : Double = matrix[0][0] + matrix[0][n-1] + matrix [n-1][0] + matrix[n-1][n-1]

        var middle =0.0

        for(i in 1 until n-1){
            for(j in 1 until n-1){
                middle +=  matrix[i][j]
            }
        }

        val rest = total - (corner+middle)
        val answer ="%.${3}f".format(h*k/4.0*(corner + (2*rest) + (4*middle)))


        val intent = Intent(this, double_integral_solution::class.java).apply {
            putExtra("ans", answer)
            putExtra("Equation", findViewById<EditText>(R.id.function).text.toString())
            putExtra("xb",upX.toString())
            putExtra("xa",loX.toString())
            putExtra("ya",loY.toString())
            putExtra("yb",upY.toString())
        }
        startActivity(intent)
    }

    fun reset(){
        findViewById<EditText>(R.id.function).text.clear()
        findViewById<EditText>(R.id.upperLimitX).text.clear()
        findViewById<EditText>(R.id.lowerLimitX).text.clear()
        findViewById<EditText>(R.id.UpperLimitY).text.clear()
        findViewById<EditText>(R.id.lowerLimitY).text.clear()
        findViewById<EditText>(R.id.interval).setText("100")
    }
}