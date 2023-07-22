package com.example.mathematicstoolbox

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val contents = filllist()
        findViewById<RecyclerView>(R.id.list).apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = Adapter(contents).apply{
                SetOnClickListener(object : OnItemClickListener{
                    override fun onItemClick(position: Int, Item: content) {
                        if(Item.layout!=0){
                            startActivity(Intent(this@MainActivity,Item.layout::class.java))
                        }
                        else{
                            Toast.makeText(this@MainActivity, Item.name, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        }
    }


    private fun filllist(): MutableList<content> {
        return mutableListOf(
            content("Definite Integral",R.drawable.int1,Definite_Integral()),
            content("Double Integral",R.drawable.fint2,double_Integral()),
            content("First Order ODE",R.drawable.tode1,ode1()),
            content("Second Order ODE",R.drawable.tode2,ODE2()),
            content("First Derivative",R.drawable.diff1__1_,FirstDerivative()),
            content("Second Derivative",R.drawable.diff2__1_,SecondDerivative())
            )
    }


}