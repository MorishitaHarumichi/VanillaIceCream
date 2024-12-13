package com.example.acceleration.screen

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.VideoView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

        if (selectedVideo != -1) {
            // 動画IDがある場合、動画を再生
            setContentView(R.layout.activity_screen_flash)
            videoView = findViewById(R.id.videoView)
            playVideo(selectedVideo)

            // 戻るボタンの処理を追加
            val backButton: ImageButton = findViewById(R.id.btnBack)
            backButton.setOnClickListener {
                val intent = Intent(this, HomeScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
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

    // 動画の再生処理
    private fun playVideo(videoResId: Int) {
        val videoUri = Uri.parse("android.resource://$packageName/$videoResId")
        videoView?.setVideoURI(videoUri)
        videoView?.setOnPreparedListener { it.start() }
        videoView?.setOnCompletionListener {
            finish() // 動画終了後に画面を閉じる
        }
    }

    @Composable
    fun FlashScreenContent(onBack: () -> Unit, selectedImage: Int) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 戻るボタン
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(48.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
            }


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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
