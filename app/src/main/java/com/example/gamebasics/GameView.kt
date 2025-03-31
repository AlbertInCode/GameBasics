package com.example.gamebasics

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback  {
    private val paint = Paint()
    private val animations = Animations()
    private val soundManager = GameSoundManager(context)
    private var score = 0
    private var isPaused = false

    init {
        holder.addCallback(this)
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
        animations.animatePlayerPaint(paint)
    }

    private fun startGameLoop() {
        Thread {
            while (true) {
                if (!isPaused) {
                    updateGameLogic()
                    drawCanvas()
                }
                Thread.sleep(16)
            }
        }.start()
    }

    private fun updateGameLogic() {
        score++

        if (score % 10 == 0) soundManager.playScoreSound()

        if (score == 500) {
            soundManager.playCollisionSound()
            isPaused = true
        }
    }

    private fun drawCanvas() {
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)

        //paint.color = Color.RED
        canvas.drawCircle(width / 2f, height / 2f, 50f, paint)

        holder.unlockCanvasAndPost(canvas)
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
        soundManager.release()
    }

}
