package com.example.mathematicstoolbox

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.Expression

class Definite_Integral : AppCompatActivity() {
    private var h : Double =0.0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definite_integral)
        findViewById<Button>(R.id.button).setOnClickListener{
            solve()
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


    private fun solve(){
        if(findViewById<EditText>(R.id.upperLimit2).text.toString().isEmpty() || findViewById<EditText>(R.id.lowerLimit).text.toString().isEmpty()  || findViewById<EditText>(R.id.function).text.toString().isEmpty()   ){

            Toast.makeText(this, "Please provide appropriate Information", Toast.LENGTH_SHORT).show()

        }
        else {

            var n = findViewById<EditText>(R.id.interval).text.toString().toInt()



            n = n + (n % 2)

            lateinit var expression : Expression

            try {
                expression = ExpressionBuilder(
                    findViewById<EditText>(R.id.function).text.toString()
                ).variable("x").build()
            }
            catch (e: Exception) {

                Toast.makeText(this, "Please enter appropriate expression", Toast.LENGTH_SHORT).show()
                return
            }

            val b = findViewById<EditText>(R.id.upperLimit2).text.toString().toFloat()
            val a = findViewById<EditText>(R.id.lowerLimit).text.toString().toFloat()
            h = (b - a).toDouble() / n.toDouble()
            var I = 0.0
            for (i in 0..n) {

                val xi: Double = (a + i * h)
                val result = expression.setVariable("x", xi).evaluate()

                I += if (i == 0 || i == n) {
                    result
                } else {
                    if (i % 2 == 0) {
                        2 * result
                    } else {
                        4 * result
                    }
                }

            }

            var res ="%.${3}f".format(I * h / 3)



            startActivity(Intent(this@Definite_Integral, Integration_solution::class.java).apply {
                putExtra("Answer", res)
                putExtra("Equation", findViewById<EditText>(R.id.function).text.toString())
                putExtra("b",b.toString())
                putExtra("a",a.toString())
            })
        }

    }

    private fun reset(){
        findViewById<EditText>(R.id.upperLimit2).text.clear()
        findViewById<EditText>(R.id.lowerLimit).text.clear()
        findViewById<EditText>(R.id.interval).setText("100")
        findViewById<EditText>(R.id.function).text.clear()
        h=0.0
    }


}
