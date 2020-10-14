package com.justadeveloper96.notificationcreatordemo.notification.samples

import com.justadeveloper96.notificationcreator.BigPictureStyle
import com.justadeveloper96.notificationcreator.InboxStyle
import com.justadeveloper96.notificationcreator.NotificationData
import com.justadeveloper96.notificationcreator.Style

object SamplePayloads {

    fun simple(): NotificationData {
        return NotificationData(title = "Hey", message = "How are you!").apply {
            make()
        }
    }

    fun inboxStyle(): NotificationData {
        return NotificationData(
            title = "Hey", message = "How are you!",
            style = Style.INBOX,
            inboxStyle = InboxStyle(
                lines = listOf("Lunch", "Dinner"),
                summaryText = "Want to eat?"
            )
        ).apply {
            make()
        }
    }

    fun bigPictureStyle(): NotificationData {
        return NotificationData(
            title = "Hey", message = "How are you!",
            style = Style.BIG_PICTURE,
            iconImageUrl = "https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png",
            bigPictureStyle = BigPictureStyle(
                "https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png"
            ),
            extras = mutableMapOf(
                "int" to 5,
                "double" to 5.2
            )
        ).apply {
            make()
        }
    }
}