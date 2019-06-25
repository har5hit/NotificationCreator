package com.justadeveloper96.notificationcreator

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class NotificationData(val type: String) {
    lateinit var bundle: Bundle
    var title: String? = null
    var message: String? = null
    lateinit var channelId: String
    lateinit var channelName: String
    lateinit var channelPriority: String
    var iconImageUrl: String? = null
    var landingId: String? = null
    var landingAction: String? = null
    var style: String? = null
    var extras: MutableMap<String, String>? = null
    var actions: List<Action>? = null

    fun make() {
        bundle = Bundle()
        extras?.forEach { (key, value) ->
            bundle.putString(key, value)
        }
        actions?.forEach { action ->
            action.make()
        }
    }

    override fun toString(): String {
        return "NotificationData(type='$type', bundle=$bundle, title=$title, message=$message, channelId='$channelId', channelName='$channelName', channelPriority='$channelPriority', iconImageUrl=$iconImageUrl, landingId=$landingId, landingAction=$landingAction, style=$style, extras=$extras, actions=$actions)"
    }


    companion object {

        fun parser(data: Map<String, String>): NotificationData {
            Log.e("Notification Map", data.toString())
            return NotificationData(data["type"] ?: "DEFAULT").apply {
                title = data["title"]
                message = data["message"]
                channelId = data["channelId"] ?: "Default"
                channelName = data["channelName"] ?: "Default"
                channelPriority = data["channelPriority"] ?: "high"
                iconImageUrl = data["icon"]
                landingId = data["landingId"]
                landingAction = data["landingAction"]
                style = data["style"]
                data["extras"]?.let {
                    val tokenType = object : TypeToken<Map<String, String>>() {}.type
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

data class Action(val label: String, val extras: Map<String, String>?, val action: String?, val drawable: String) {
    lateinit var bundle: Bundle

    fun make() {
        bundle = Bundle()
        extras?.forEach { (key, value) ->
            bundle.putString(key, value)
        }
    }
}
