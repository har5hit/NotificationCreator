package com.justadeveloper96.notificationcreator

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


open class NotificationData(
    open val type: String = "DEFAULT",
    open var title: String? = null,
    open var message: String? = null,
    open var channelId: String = "Default",
    open var channelName: String = "Default",
    open var channelPriority: String = "high",
    open var iconImageUrl: String? = null,
    open var landingId: String? = null,
    open var action: String? = null,
    open var style: Style? = null,
    open var extras: MutableMap<String, Any>? = null,
    open var actions: List<Action>? = null,
    open var groupSummary: Boolean? = null,
    open var groupKey: String? = null,
    open var inboxStyle: InboxStyle? = null,
    open var bigPictureStyle: BigPictureStyle? = null
) {
    lateinit var bundle: Bundle

    fun make() {
        bundle = extras?.toBundle() ?: Bundle()
        actions?.forEach { action ->
            action.make()
        }
    }

    companion object {

        fun parser(data: Map<String, String>): NotificationData {
            Log.e("Notification Map", data.toString())
            return NotificationData(data["type"] ?: "DEFAULT",
                title = data["title"],
                message = data["message"],
                channelId = data["channelId"] ?: "Default",
                channelName = data["channelName"] ?: "Default",
                channelPriority = data["channelPriority"] ?: "high",
                iconImageUrl = data["icon"],
                landingId = data["landingId"],
                action = data["action"],
                style = data["style"]?.let {
                    try {
                        Style.valueOf(it)
                    } catch (e: Exception) {
                        null
                    }
                },
                extras = data["extras"]?.let {
                    val tokenType = object : TypeToken<Map<String, Any>>() {}.type
                    return Gson().fromJson(it, tokenType)
                },
                actions = data["actions"]?.let {
                    val tokenType = object : TypeToken<List<Action>>() {}.type
                    return Gson().fromJson(it, tokenType)
                },
                groupSummary = data["groupSummary"]?.toBoolean(),
                groupKey = data["groupKey"],
                inboxStyle = data["inboxStyle"]?.let {
                    Gson().fromJson(it, InboxStyle::class.java)
                },
                bigPictureStyle = data["bigPictureStyle"]?.let {
                    Gson().fromJson(it, BigPictureStyle::class.java)
                }
            ).apply {
                make()
            }
        }
    }
}

enum class Style { MESSAGE, BIG_PICTURE, INBOX }

data class Action(
    @SerializedName("label")
    val label: String,
    @SerializedName("extras")
    val extras: Map<String, Any>?,
    @SerializedName("action")
    val action: String?,
    @SerializedName("drawable")
    val drawable: String?
) {
    lateinit var bundle: Bundle

    fun make() {
        bundle = extras?.toBundle() ?: Bundle()
    }
}

data class InboxStyle(
    @SerializedName("lines") val lines: List<String>? = null,
    @SerializedName("summaryText") val summaryText: String? = null
)

data class BigPictureStyle(@SerializedName("bigPicture") val bigPicture: String? = null)

fun Map<String, Any>.toBundle(): Bundle {
    val bundle = Bundle()
    forEach { (key, value) ->
        when (value) {
            is Boolean -> {
                bundle.putBoolean(key, value)
            }
            is Double -> {
                if ((value - value.toInt() == 0.0)) {
                    bundle.putInt(key, value.toInt())
                } else {
                    bundle.putDouble(key, value)
                }
            }
            is List<*> -> {
                bundle.putStringArrayList(key, value as java.util.ArrayList<String>)
            }
            is String -> {
                bundle.putString(key, value)
            }
        }
    }
    return bundle
}