package com.example.gamebasics

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint

class Animations {
    fun animatePlayerPaint(paint: Paint) {
        val animator = ValueAnimator.ofFloat(0.8f, 1.2f).apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
                val scale = it.animatedValue as Float
                paint.color = Color.rgb(
                    (255 * scale).toInt(),
                    (100 * scale).toInt(),
                    (100 * scale).toInt()
                )
            }
        }
        animator.start()
    }
}