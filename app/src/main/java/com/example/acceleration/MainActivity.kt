package com.example.acceleration

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.acceleration.screen.HomeScreen
import com.example.acceleration.screen.TitleScreen
import com.example.acceleration.workmanager.AccelerometerWorker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = Intent(this, TitleScreen::class.java)
        startActivity(intent)
        finish()

        // WorkManagerでワーカーを起動
        val workRequest = OneTimeWorkRequest.Builder(AccelerometerWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)

    }
}