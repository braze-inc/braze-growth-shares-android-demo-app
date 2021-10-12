package com.braze.advancedsamples

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ui.AppBarConfiguration
import com.braze.Braze
import com.braze.advancedsamples.contentcards.activity.ContentCardListActivity
import com.braze.advancedsamples.contentcards.activity.MessageListActivity
import com.braze.advancedsamples.databinding.MainLayoutBinding
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        val existingUsername = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        with(existingUsername?.getString("user", "")){
            binding.usernameText.setText(this)
            if ("" != this){
                this?.let{
                    BrazeManager.getInstance().userLogin(it)
                }
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Braze Notifications"
            val descriptionText = "Notifications from Braze!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        setContentView(binding.root)
        binding.messageCenterButton.setOnClickListener {
            startActivity(Intent(this, MessageListActivity::class.java))
        }

        binding.setUserButton.setOnClickListener {
            val username = binding.usernameText.text.toString()
            BrazeManager.getInstance().userLogin(username)
            val sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString("user", username)
                apply()
            }
        }

        binding.contentListButton.setOnClickListener {
            startActivity(Intent(this, ContentCardListActivity::class.java))
        }

        binding.htmlContentButton.setOnClickListener {
            Log.d(TAG, "HTML button pressed, sending event")
            BrazeManager.getInstance().logCustomEvent("HTML Pressed")
        }

        binding.fullContentButton.setOnClickListener {
            Log.d(TAG, "Content button pressed, sending event")
            BrazeManager.getInstance().logCustomEvent("Full Page Pressed")
        }

        binding.notificationButton.setOnClickListener {
            Log.d(TAG, "Notification button pressed, sending event")
            BrazeManager.getInstance().logCustomEvent("LAB Session Progress Pressed")
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val CHANNEL_ID = "PrimaryNotifications"
    }
}