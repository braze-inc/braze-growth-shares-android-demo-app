package com.braze.advancedsamples

import android.app.Activity
import android.view.View
import com.braze.advancedsamples.immersive.CustomImmersiveInAppMessage
import com.braze.advancedsamples.inapp.modal.TeamPickerView
import com.braze.models.inappmessage.IInAppMessage
import com.braze.ui.inappmessage.BrazeInAppMessageManager
import com.braze.ui.inappmessage.IInAppMessageViewFactory

class CustomInAppMessageViewFactory : IInAppMessageViewFactory {
    override fun createInAppMessageView(activity: Activity, inAppMessage: IInAppMessage): View {
        return when {
            inAppMessage.extras?.get("view_type") == "picker" -> {
                getCustomPickerView(activity, inAppMessage)
            }
            inAppMessage.extras?.get("view_type") == "switches" -> {
                getCustomImmersiveView(activity, inAppMessage)
            }
            else -> {
                //Defer to default
                BrazeInAppMessageManager
                    .getInstance()
                    .getDefaultInAppMessageViewFactory(inAppMessage).createInAppMessageView(activity, inAppMessage)
            }
        }
    }

    private fun getCustomImmersiveView(activity: Activity, inAppMessage: IInAppMessage): CustomImmersiveInAppMessage{
        val view = activity.layoutInflater.inflate(R.layout.full_screen_iam, null) as CustomImmersiveInAppMessage
        val options = inAppMessage.message.split(",")
        view.setOptions(options)
        inAppMessage.extras?.get("title").let { view.setTitle(it) }
        inAppMessage.extras?.get("subtitle").let {view.setSubtitle(it) }
        return view
    }

    private fun getCustomPickerView(activity: Activity, inAppMessage: IInAppMessage): TeamPickerView {
        val view = activity.layoutInflater.inflate(R.layout.team_picker_dialog, null) as TeamPickerView
        val teams = inAppMessage.message.split(",")
        view.setTeams(teams)
        return view
    }
}