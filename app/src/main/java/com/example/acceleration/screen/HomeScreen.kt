package com.example.acceleration.screen

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.acceleration.R
import com.example.acceleration.ui.theme.AccelerationTheme

class HomeScreen : AppCompatActivity() {

    private var savedImageId by mutableIntStateOf(R.drawable.kao1) // State管理

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
            // 画像を背景として配置
            Image(
                painter = painterResource(id = savedImageId), // 初期値の顔
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(), // 画像のサイズ
                contentScale = ContentScale.FillBounds // 画像の表示形式
            )

            // ボタンを画像の上に配置
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // 設定アイコンボタン
                Button(
                    onClick = {
                        // "CharacterScreen"へ遷移するためのIntent
                        val intent = Intent(context, CharacterScreen::class.java)
                        context.startActivity(intent)
                    },
                    shape = CircleShape, // 円形にする
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.setting), // 設定アイコン
                        contentDescription = "Settings",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(58.dp), // 画像のサイズ
                        contentScale = ContentScale.Crop // 画像の表示形式
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // プレビューアイコンボタン
                Button(
                    onClick = {
                        // "PreviewScreen"へ遷移するためのIntent
                        val intent = Intent(context, PreviewScreen::class.java)
                        context.startActivity(intent)
                    },
                    shape = CircleShape, // 円形にする
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.face), // 顔のアイコン
                        contentDescription = "Preview",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(58.dp), // 画像のサイズ
                        contentScale = ContentScale.Crop // 画像の表示形式
                    )
                }
            }
        }
    }

    private fun getSelectedImageFromPreferences(): Int {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPref.getInt("selectedImage", R.drawable.kao1)
    }
}
