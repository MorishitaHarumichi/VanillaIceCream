package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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
            contentAlignment = Alignment.TopStart // 矢印を左上に配置
        ) {
            // 戻るボタン
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
            }

            // CoilでGIFを表示
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = this@FlashScreen)
                    .data(R.raw.androidcompactfacesad)  // GIFのリソースID
                    .build()
            )

            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = "GIF",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
