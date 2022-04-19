package com.example.mymusic

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mymusic.databinding.ActivityMainBinding
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar

const val TRACK=33
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedTrackUri: Uri? = null
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val intentService = Intent(this, PlayMusicService::class.java)
        binding.play.isEnabled=false
        binding.pause.isEnabled = false
        binding.stop.isEnabled = false

        binding.addTrack.setOnClickListener {
            galleryAddTrack()
            binding.play.isEnabled=true
        }

        binding.play.setOnClickListener {
            intentService.putExtra("trackID",selectedTrackUri.toString())
            startService(intentService)
            binding.pause.isEnabled = true
            binding.stop.isEnabled = true
        }

        binding.stop.setOnClickListener {
            stopService(intentService)
        }

        binding.pause.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                TRACK -> {
                    selectedTrackUri = data?.data
                }
            }
        }
    }

    private fun galleryAddTrack() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select track"), TRACK)
    }
}