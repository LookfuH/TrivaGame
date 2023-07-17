package com.example.trivagame

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity()
{
    var correct: Int = 0
    var total: Int = 0
    var A1 = Pair(true, "pick me")
    var A2 = Pair(false, "not me")
    var A3 = Pair(false, "not me")
    var A4 = Pair(false, "not me")


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

    }
    fun loadGame(v: View){
        setContentView(R.layout.gamesession)

    }
    fun Gameplay(v: View){


    }

    fun loadResult(v: View) = setContentView(R.layout.resultsmenu)


}