package com.example.gamebasics

import android.content.Context
import android.media.SoundPool

class GameSoundManager(context: Context) {
    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()
    private val scoreSound = soundPool.load(context, R.raw.score, 1)
    private val collisionSound = soundPool.load(context, R.raw.collision, 1)

    fun playScoreSound() {
        soundPool.play(scoreSound, 1f, 1f, 1, 0, 1f)
    }

    fun playCollisionSound() {
        soundPool.play(collisionSound, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}