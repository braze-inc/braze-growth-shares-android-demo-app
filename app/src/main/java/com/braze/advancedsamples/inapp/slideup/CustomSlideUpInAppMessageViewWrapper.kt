package com.braze.advancedsamples.inapp.slideup

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import com.braze.configuration.BrazeConfigurationProvider
import com.braze.models.inappmessage.IInAppMessage
import com.braze.ui.inappmessage.DefaultInAppMessageViewWrapper
import com.braze.ui.inappmessage.listeners.IInAppMessageViewLifecycleListener

class CustomSlideUpInAppMessageViewWrapper(inAppMessageView: View?,
                                           inAppMessage: IInAppMessage?,
                                           inAppMessageViewLifecycleListener: IInAppMessageViewLifecycleListener?,
                                           configurationProvider: BrazeConfigurationProvider?,
                                           openingAnimation: Animation?,
                                           closingAnimation: Animation?,
                                           clickableInAppMessageView: View?) : DefaultInAppMessageViewWrapper(inAppMessageView,
    inAppMessage,
    inAppMessageViewLifecycleListener,
    configurationProvider,
    openingAnimation,
    closingAnimation,
    clickableInAppMessageView) {

    override fun getLayoutParams(inAppMessage: IInAppMessage?): ViewGroup.LayoutParams {
        val params = super.getLayoutParams(inAppMessage) as FrameLayout.LayoutParams
        params.bottomMargin = params.bottomMargin + 500
        return params
    }
}