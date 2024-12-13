package com.example.acceleration.screen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.VideoView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class FlashScreen : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null  // MediaPlayerを管理する変数
    private var videoView: VideoView? = null      // VideoViewを管理する変数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedImage = getSelectedImageFromPreferences()
        val selectedSound = getSelectedSoundFromPreferences()
        val selectedVideo = getSelectedVideoFromPreferences()
        val vibrationDuration = getVibrationDurationFromPreferences()

        if (selectedVideo != -1) {
            // 動画IDがある場合、動画を再生
            setContentView(R.layout.activity_screen_flash)
            videoView = findViewById(R.id.videoView)
            playVideo(selectedVideo)

        } else {
            // 動画がない場合、画像と音声を表示
            setContent {
                AccelerationTheme {
                    FlashScreenContent(
                        onBack = {
                            val intent = Intent(this, HomeScreen::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        },
                        selectedImage = selectedImage
                    )
                }
            }
            playSoundDelayed(selectedSound)
            playVibration(vibrationDuration)

            android.util.Log.d("FlashScreen", "Vibration Duration: $vibrationDuration")

        }
    }



    // 音声の再生処理
    private fun playSoundDelayed(soundId: Int) {
        mediaPlayer?.release() // 以前のMediaPlayerを解放
        mediaPlayer = MediaPlayer.create(this, soundId)
        mediaPlayer?.setOnPreparedListener {
            it.start()  // 音声が準備できたら再生を開始
        }
    }

    // 動画の再生処理（ループ再生）
    private fun playVideo(videoResId: Int) {
        val videoUri = Uri.parse("android.resource://$packageName/$videoResId")
        videoView?.setVideoURI(videoUri)

        videoView?.setOnPreparedListener { it.start() }

        // ループ再生の設定
        videoView?.setOnCompletionListener {
            videoView?.start() // 再生が終わったら最初から再生
        }
    }


    @Composable
    fun FlashScreenContent(onBack: () -> Unit, selectedImage: Int) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = selectedImage),
                contentDescription = "selectedImage",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.character1)
    }

    private fun getSelectedSoundFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedSound", R.raw.a_n)
    }

    private fun getSelectedVideoFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedVideo", -1) // デフォルト値は-1
    }

    private fun getVibrationDurationFromPreferences(): Long {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getLong("vibrationDuration", 0L) // デフォルトは0ms
    }

    private fun playVibration(duration: Long) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

