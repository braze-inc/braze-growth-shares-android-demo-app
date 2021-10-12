package com.braze.advancedsamples

import android.service.notification.NotificationListenerService
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.datatransport.cct.internal.LogResponse.fromJson
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class NotificationService : FirebaseMessagingService() {

    private val badgeIds: List<Int> = listOf(
        R.id.badge_1,
        R.id.badge_2,
        R.id.badge_3,
        R.id.badge_4,
        R.id.badge_5,
        R.id.badge_6,
        R.id.badge_7,
        R.id.badge_8
    )

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val dataMap = Gson().fromJson<Map<String, String>>(remoteMessage.data["extra"] as String, object:
            TypeToken<Map<String, String>>(){}.type)
        if (true == dataMap["display_type"]?.equals("braze_custom")) {
            val nextSessionCompleteDate = dataMap["next_session_complete_date"]
            val totalSessionCount = dataMap["total_session_count"]?.toInt() ?: 0
            val completedSessionCount = dataMap["completed_session_count"]?.toInt() ?:0
            val nextSessionName = dataMap["next_session_name"]

            // Get the layouts to use in the custom notification
            val notificationLayoutExpanded = RemoteViews(packageName, R.layout.large_personalized_message)
            notificationLayoutExpanded.setTextViewText(
                R.id.session_count,
                "$completedSessionCount / $totalSessionCount"
            )
            notificationLayoutExpanded.setTextViewText(R.id.next_session, nextSessionName)
            notificationLayoutExpanded.setTextViewText(R.id.next_session_date, nextSessionCompleteDate)
            if (completedSessionCount > 0){
                for (i in 1..completedSessionCount){
                    if (i < badgeIds.size -1){
                        notificationLayoutExpanded.setInt(badgeIds[i-1], "setBackgroundResource", R.drawable.selected_circle)
                    }
                }
            }

            // Apply the layouts to the notification
            val customNotification = NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_braze_push)
                .setContentTitle("You completed a Braze LAB session!")
                .setContentText("View to check your progress!")
                .setCustomBigContentView(notificationLayoutExpanded)
                .build()
            with(NotificationManagerCompat.from(this)) {
                notify(Random.nextInt(), customNotification)
            }
        }


    }
}