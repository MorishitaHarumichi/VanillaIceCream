package com.example.acceleration

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
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
import com.example.acceleration.screen.HomeScreen
import com.example.acceleration.ui.theme.AccelerationTheme
import com.example.acceleration.workmanager.AccelerometerWorker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()

        // WorkManagerでワーカーを起動
        val workRequest = OneTimeWorkRequest.Builder(AccelerometerWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)

    }
}
