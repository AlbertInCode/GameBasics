package com.example.gamebasics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class MenuView(context: Context, private val startGameCallback: () -> Unit) : View(context) {
    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 80f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        canvas.drawText("Start Game", width / 2f - 150f, height /2f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (event.x > width / 2f - 150f && event.x < width / 2f + 150f &&
                event.y > height / 2f - 50f && event.y < height / 2f + 50f
                ){
                startGameCallback()
            }
        }
        return true
    }
}