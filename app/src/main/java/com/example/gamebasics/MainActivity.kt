package com.example.gamebasics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuView = MenuView(this) {
            setContentView(GameView(this))
        }
        setContentView(menuView)
    }
}