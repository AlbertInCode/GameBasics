package com.example.gamebasics

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback  {
    private val paint = Paint()
    private var ballX = 300f
    private var ballY = 300f
    private var velocityX = 10f
    private var velocityY = 10f
    private val ballRadius = 50f
    private var isPaused = false
    private var score = 0
    private var obstacles = mutableListOf<Obstacle>()

    init {
        holder.addCallback(this)
        for (i in 0 until 4) {
            obstacles.add(Obstacle(
                (800..1200).random().toFloat(),
                (100..800).random().toFloat(),
                80f,
                120f,
                (5..10).random().toFloat()
            ))
        }
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a [Surface], so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
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
        if (ballY - ballRadius < 0 || ballY + ballRadius > height) velocityY = -velocityY

        // Update obstacle
        obstacles.forEach { it.update() }

        obstacles.forEach {
            if (it.checkCollision(ballX, ballY, ballRadius)) {
                isPaused = true
            }
        }

        score++
    }

    private fun drawCanvas() {
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)

        // Draw ball
        paint.color = Color.RED
        canvas.drawCircle(ballX, ballY, ballRadius, paint)

        // Draw obstacle
        obstacles.forEach { it.draw(canvas) }

        // Draw score
        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas.drawText("Score: $score", 50f, 50f, paint)

        holder.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            isPaused = !isPaused
        }

        return true
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after [.surfaceCreated].
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new [PixelFormat] of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Handle changes if needed
    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

}
