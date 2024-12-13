package com.example.acceleration.screen

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class HomeScreen : AppCompatActivity() {

    private var savedImageId by mutableStateOf(R.drawable.character1) // State管理

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
        // SharedPreferencesから画像IDを即座に監視して反映
        savedImageId = getSelectedImageFromPreferences()

        val context = LocalContext.current

        DisposableEffect(Unit) {
            val prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE)
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == "selectedImage") {
                    savedImageId = getSelectedImageFromPreferences()
                }
            }
            prefs.registerOnSharedPreferenceChangeListener(listener)

            onDispose {
                prefs.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // 背景画像を設定
            Image(
                painter = painterResource(id = savedImageId),
                contentDescription = "Selected Background",
                modifier = Modifier.fillMaxSize()
            )

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

    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.character1)
    }
}

