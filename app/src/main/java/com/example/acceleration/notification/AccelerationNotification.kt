package com.example.acceleration.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.example.acceleration.screen.FlashScreen

class AccelerationNotification(private val context: Context) {

    private val CHANNEL_ID = "acceleration_channel_id"

    init {
        // 通知チャンネルの作成（Android 8.0以降）
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Acceleration Notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 純粋な加速度が-9.0以下になった際に通知を表示
    fun showNotification(acceleration: Float) {
        val intent = Intent(context, FlashScreen::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(FlashScreen::class.java)
        stackBuilder.addNextIntent(intent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

        // 通知の内容
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("ごめんね！")
            .setContentText("タップしてあやまらせる")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // 通知を表示
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}
