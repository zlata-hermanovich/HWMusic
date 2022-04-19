package com.example.mymusic

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.net.Uri
import android.os.IBinder

class PlayMusicService : Service() {

   private lateinit var mediaPlayer: MediaPlayer

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

            val value = intent?.getStringExtra("trackID")
            val myUri= Uri.parse(value)
            mediaPlayer = create(this,myUri)
            mediaPlayer.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}