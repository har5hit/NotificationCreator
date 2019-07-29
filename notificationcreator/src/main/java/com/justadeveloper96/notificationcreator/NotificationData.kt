package com.justadeveloper96.notificationcreator

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


open class NotificationData(open val type: String) {
    lateinit var bundle: Bundle
    var title: String? = null
    var message: String? = null
    lateinit var channelId: String
    lateinit var channelName: String
    lateinit var channelPriority: String
    var iconImageUrl: String? = null
    var landingId: String? = null
    var action: String? = null
    var style: String? = null
    var extras: MutableMap<String, Any>? = null
    var actions: List<Action>? = null

    fun make() {
        bundle = extras?.toBundle() ?: Bundle()
        actions?.forEach { action ->
            action.make()
        }
    }

    override fun toString(): String {
        return "NotificationData(type='$type', bundle=$bundle, title=$title, message=$message, channelId='$channelId', channelName='$channelName', channelPriority='$channelPriority', iconImageUrl=$iconImageUrl, landingId=$landingId, action=$action, style=$style, extras=$extras, actions=$actions)"
    }


    companion object {

        fun parser(data: Map<String, String>): NotificationData {
            Log.e("Notification Map", data.toString())
            return NotificationData(data["type"] ?: "DEFAULT").apply {
                title = data["title"]
                message = data["message"]
                channelId = data["channel_id"] ?: "Default"
                channelName = data["channel_name"] ?: "Default"
                channelPriority = data["channel_priority"] ?: "high"
                iconImageUrl = data["icon"]
                landingId = data["landing_id"]
                action = data["action"]
                style = data["style"]
                data["extras"]?.let {
                    val tokenType = object : TypeToken<Map<String, Any>>() {}.type
                    extras = Gson().fromJson(it, tokenType)
                }
                data["actions"]?.let {
                    val tokenType = object : TypeToken<List<Action>>() {}.type
                    actions = Gson().fromJson(it, tokenType)
                }
                make()
            }
        }
    }
}

data class Action(val label: String, val extras: Map<String, Any>?, val action: String?, val drawable: String?) {
    lateinit var bundle: Bundle

    fun make() {
        bundle = extras?.toBundle() ?: Bundle()
    }
}

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