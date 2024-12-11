package com.example.acceleration.screen

import android.content.Intent
import android.os.Bundle
import android.content.SharedPreferences
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class FlashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccelerationTheme {
                val selectedImage = getSelectedImageFromPreferences()  // SharedPreferencesから画像IDを取得
                FlashScreenContent(onBack = {
                    val intent = Intent(this, HomeScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }, selectedImage = selectedImage)
            }
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

            // 渡された画像IDを使って表示
            Image(
                painter = painterResource(id = selectedImage),
                contentDescription = "Selected Character Image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // SharedPreferencesから画像IDを取得する関数
    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.character1)  // デフォルト値を指定
    }
}
