package com.example.gamebasics

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val paint = Paint()
    private var ballX = 300f
    private var ballY = 300f
    private var velocityX = 10f
    private var velocityY = 10f
    private var ballRadius = 50f
    private var isPaused = false

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startGameLoop()
    }

    private fun startGameLoop() {
        Thread {
            while (true) {
                if (!isPaused) {
                    updatePhysics()
                    drawCanvas()
                }
                Thread.sleep(16)
            }
        }.start()
    }

    private fun updatePhysics() {
        ballX += velocityX
        ballY += velocityY

        if (ballX - ballRadius < 0 || ballX + ballRadius > width) velocityX = -velocityX
        if (ballY - ballRadius < 0 || ballY + ballRadius > height) velocityY = - velocityY
    }

    private fun drawCanvas() {
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK) // Clear screen

        paint.color = Color.RED
        canvas.drawCircle(ballX, ballY, ballRadius, paint)

        holder.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isPaused = !isPaused
        }

        return true
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }
}