package com.justadeveloper96.notificationcreatordemo.notification.samples

import com.justadeveloper96.notificationcreator.NotificationData

object SamplePayloads {

    fun simple(): NotificationData {
        return NotificationData(title = "Hey", message = "How are you!").apply {
            make()
        }
    }
}