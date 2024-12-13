package com.example.acceleration.screen


import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 通知権限をリクエスト
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }

        setContent {
            AccelerationTheme {
                HomeScreenContent()
            }
        }
    }

    // 通知権限をリクエスト
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }

    @Composable
    fun HomeScreenContent() {
        // SharedPreferencesから選択されたコンテンツを読み込む
        val savedImageId = remember { getSelectedImageFromPreferences() }

        // LaunchedEffectで選択された設定を即時反映
        LaunchedEffect(savedImageId) {
            // 設定が変わったらUIが自動的に更新される
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // 背景画像を設定
            Image(
                painter = painterResource(id = savedImageId),
                contentDescription = "Selected Background",
                modifier = Modifier.fillMaxSize()
            )

            // ボタンの配置
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        val intent = Intent(this@HomeScreen, FlashScreen::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("FlashScreenに遷移")
                }

                Button(
                    onClick = {
                        val intent = Intent(this@HomeScreen, CharacterScreen::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("CharacterScreenに遷移")
                }
            }
        }
    }

    // SharedPreferencesから画像IDを取得
    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.character1)
    }
}
