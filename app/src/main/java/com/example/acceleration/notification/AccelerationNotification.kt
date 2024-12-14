import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.acceleration.R

class AccelerationNotification(private val context: Context) {

    private val CHANNEL_ID = "acceleration_channel_id"

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)

            // 既存のチャンネルを削除して再作成
            if (existingChannel != null) {
                notificationManager.deleteNotificationChannel(CHANNEL_ID)
            }

            val name = "Acceleration Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val soundUri: Uri = Uri.parse("android.resource://${context.packageName}/raw/custom_notification")
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.setSound(soundUri, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 通知音をSharedPreferencesから取得
    private fun getNotificationSoundFromPreferences(): Int {
        val sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("notificationSound", R.raw.notification_sound2) // デフォルト通知音
    }

    fun showNotification(acceleration: Float) {
        val notificationSoundId = getNotificationSoundFromPreferences()
        val soundUri = Uri.parse("android.resource://${context.packageName}/raw/$notificationSoundId")
        val dynamicChannelId = "acceleration_channel_id_$notificationSoundId" // 動的なチャンネルID

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel(dynamicChannelId)

            // 新しいチャンネルを作成
            if (existingChannel == null) {
                val name = "Acceleration Notifications"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(dynamicChannelId, name, importance)
                channel.setSound(soundUri, null)  // ここで通知音を設定
                notificationManager.createNotificationChannel(channel)
            }
        }

        val notification = NotificationCompat.Builder(context, dynamicChannelId)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("ごめんね！")
            .setContentText("タップしてあやまらせる")
            .setSound(soundUri)  // 通知音を設定
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}
