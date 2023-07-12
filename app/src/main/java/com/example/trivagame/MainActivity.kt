package com.example.trivagame

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

    }
    fun loadGame(v: View) = setContentView(R.layout.gamesession)

    fun loadResult(v: View) = setContentView(R.layout.resultsmenu)


}