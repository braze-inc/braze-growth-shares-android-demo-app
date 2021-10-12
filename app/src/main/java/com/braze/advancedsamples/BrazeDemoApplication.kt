package com.braze.advancedsamples

import android.app.Application
import com.appboy.AppboyLifecycleCallbackListener
import com.facebook.drawee.backends.pipeline.Fresco

class BrazeDemoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        registerActivityLifecycleCallbacks(AppboyLifecycleCallbackListener())
        val manager = BrazeManager.getInstance(this)
        manager.configureCustomInAppMessageViewFactory()
        manager.registerForContentCardUpdates()
    }
}