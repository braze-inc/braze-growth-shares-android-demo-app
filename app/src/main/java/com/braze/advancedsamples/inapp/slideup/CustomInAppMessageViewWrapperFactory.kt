package com.braze.advancedsamples.inapp.slideup

import android.view.View
import android.view.animation.Animation
import com.braze.advancedsamples.inapp.slideup.CustomSlideUpInAppMessageViewWrapper
import com.braze.configuration.BrazeConfigurationProvider
import com.braze.models.inappmessage.IInAppMessage
import com.braze.models.inappmessage.InAppMessageSlideup
import com.braze.ui.inappmessage.IInAppMessageViewWrapper
import com.braze.ui.inappmessage.IInAppMessageViewWrapperFactory
import com.braze.ui.inappmessage.factories.DefaultInAppMessageViewWrapperFactory
import com.braze.ui.inappmessage.listeners.IInAppMessageViewLifecycleListener

class CustomInAppMessageViewWrapperFactory : DefaultInAppMessageViewWrapperFactory() {

    override fun createInAppMessageViewWrapper(
        inAppMessageView: View?,
        inAppMessage: IInAppMessage?,
        inAppMessageViewLifecycleListener: IInAppMessageViewLifecycleListener?,
        configurationProvider: BrazeConfigurationProvider?,
        openingAnimation: Animation?,
        closingAnimation: Animation?,
        clickableInAppMessageView: View?
    ): IInAppMessageViewWrapper {
        return if (inAppMessage is InAppMessageSlideup) {
            CustomSlideUpInAppMessageViewWrapper(
                inAppMessageView,
                inAppMessage,
                inAppMessageViewLifecycleListener,
                configurationProvider,
                openingAnimation,
                closingAnimation,
                clickableInAppMessageView
            )
        } else {
            super.createInAppMessageViewWrapper(
                inAppMessageView,
                inAppMessage,
                inAppMessageViewLifecycleListener,
                configurationProvider,
                openingAnimation,
                closingAnimation,
                clickableInAppMessageView
            )
        }
    }
}