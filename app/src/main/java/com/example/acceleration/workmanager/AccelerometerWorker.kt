package com.example.acceleration.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log
import com.example.acceleration.notification.AccelerationNotification

class AccelerometerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val notificationManager: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationChannelId = "acceleration_notification_channel"

    override fun doWork(): Result {
        // センサー管理者の初期化
        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        // センサーリスナーの登録
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // 通知チャネルの設定（Android 8.0以降）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Acceleration Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return Result.success()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            // 加速度データをログに出力
            val z = event.values[2]

            if (z < -9.0) {
                Log.d("Accelerometer", "z: $z, Sending notification!")

                // 通知の送信
                sendNotification(z)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 精度が変更された場合の処理(今回はなし)
    }

    override fun onStopped() {
        // センサーリスナーの登録解除
        sensorManager.unregisterListener(this)
    }

    private fun sendNotification(acceleration: Float) {
        val accelerationNotification = AccelerationNotification(applicationContext)
        accelerationNotification.showNotification(acceleration) // 通知表示
    }
}
