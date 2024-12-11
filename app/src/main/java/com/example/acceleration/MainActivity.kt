package com.example.acceleration

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.acceleration.notification.AccelerationNotification
import com.example.acceleration.screen.FlashScreen
import com.example.acceleration.ui.theme.AccelerationTheme
import com.example.acceleration.workmanager.AccelerometerWorker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccelerationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        onNavigateToFlashScreen = {
                            val intent = Intent(this, FlashScreen::class.java)
                            startActivity(intent)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // WorkManagerでワーカーを起動
        val workRequest = OneTimeWorkRequest.Builder(AccelerometerWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}

@Composable
fun MainScreen(onNavigateToFlashScreen: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // 画面中央に配置
    ) {
        Button(onClick = onNavigateToFlashScreen) {
            Text("FlashScreenへ遷移")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AccelerationTheme {
        MainScreen(onNavigateToFlashScreen = {})
    }
}
