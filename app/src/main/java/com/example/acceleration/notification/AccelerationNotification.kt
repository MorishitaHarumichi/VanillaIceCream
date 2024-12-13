package com.example.acceleration.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.example.acceleration.screen.PreviewScreen

class AccelerationNotification(private val context: Context) {

    private val CHANNEL_ID = "acceleration_channel_id"

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Acceleration Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val soundUri: Uri = Uri.parse("android.resource://${context.packageName}/raw/custom_notification") // 新しいMP3ファイルを参照
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.setSound(soundUri, null)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(acceleration: Float) {
        val intent = Intent(context, PreviewScreen::class.java)
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(PreviewScreen::class.java)
        stackBuilder.addNextIntent(intent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("ごめんね！")
            .setContentText("タップしてあやまらせる")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}
