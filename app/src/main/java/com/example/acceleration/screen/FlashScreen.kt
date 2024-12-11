package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class FlashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccelerationTheme {
                FlashScreenContent(onBack = {
                    val intent = Intent(this, HomeScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                })
            }
        }
    }

    @Composable
    fun FlashScreenContent(onBack: () -> Unit) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 戻るボタンを手前に表示
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(48.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
            }

            // CoilでGIFを表示（サイズを少し小さくする）
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = this@FlashScreen)
                    .data(R.raw.androidcompactfacesad)
                    .build()
            )

            // 画像のサイズを指定して表示
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = "GIF",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
