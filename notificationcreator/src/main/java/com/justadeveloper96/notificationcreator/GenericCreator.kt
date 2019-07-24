package com.justadeveloper96.notificationcreator

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.util.*


abstract class GenericCreator<T : NotificationData> : ICreator<T> {

    private fun initService(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun invoke(context: Context, data: T) {
        val service = initService(context)
        val data = preModifyData(data)
        val builder = createBuilder(context, data, service)
        beforeNotification(context, data)
        buildNotification(data, builder, context)
        modifyBuilder(data, builder)
        sendNotification(getSendNotificationId(data), service = service, builder = builder)
    }

    open fun beforeNotification(context: Context, data: T) {
    }

    open fun preModifyData(data: T): T {
        return data
    }

    open fun modifyBuilder(data: T, builder: NotificationCompat.Builder) {

    }

    val TAG = "GenericCreator"

    open fun getContentIntent(context: Context, data: T): Intent? {
        return if (data.action != null) {
            val intent = Intent(data.action)
            extractExtras(data)?.getString("data")?.let { intent.setData(Uri.parse(it)) }
            intent
        } else if (data.landingId!=null && activityMap(data.landingId!!)!=null){
            val intent = Intent(context, activityMap(data.landingId!!))
            extractExtras(data)?.let { intent.putExtras(it) }
            intent
        }else
        {
            try {
                throw Exception("Landing action/id not available")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            null
        }
    }


    fun extractExtras(data: T): Bundle? {
        return data.bundle
    }


    private fun getContentPendingIntent(context: Context, data: T): PendingIntent? {
        val intent = getContentIntent(context, data)
        return if (intent == null) null else PendingIntent.getActivity(
            context, Random().nextInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getActionsPendingIntent(
        context: Context,
        data: T,
        bundle: Bundle?,
        action: String?
    ): PendingIntent? {
        val intent = getContentIntent(context, data)
        return if (intent == null) null
        else {
            intent.action=action
            bundle?.let { intent.putExtras(it) }
            PendingIntent.getActivity(
                context, Random().nextInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }


    private fun createBuilder(context: Context, data: T, service: NotificationManager): NotificationCompat.Builder {
        val imp = data.channelPriority
        val builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                val channel: NotificationChannel
                val name = data.channelName
                val id = data.channelId
                channel = NotificationChannel(id, name, getChannelImportance(imp))
                // Register the channel with the system
                service.createNotificationChannel(channel)
                NotificationCompat.Builder(context, channel.id)
            } else {
                NotificationCompat.Builder(context)
            }

        builder.priority = getNotificationImportance(data.channelPriority)
        return builder
    }

    open fun sendNotification(id: Int, service: NotificationManager, builder: NotificationCompat.Builder) {
        service.notify(id, builder.build())
    }


    open fun getSendNotificationId(data: T) = Random().nextInt()

    private fun buildNotification(data: T, notificationBuilder: NotificationCompat.Builder, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getColor(data)?.let {
                notificationBuilder.color = ContextCompat.getColor(context, it)
            }
        }
        notificationBuilder.setLights(-0x10000, 1000, 300)
        getSound(data, context)?.let { notificationBuilder.setSound(it) }
        getContentPendingIntent(context, data)?.let { notificationBuilder.setContentIntent(it) }
        getContentTitle(data)?.let { notificationBuilder.setContentTitle(it) }
        getContentText(data)?.let { notificationBuilder.setContentText(it) }
        getAutoCancel(data)?.let { notificationBuilder.setAutoCancel(it) }
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
        getSmallIcon(data)?.let { notificationBuilder.setSmallIcon(it) }
        getStyle(data)?.let { notificationBuilder.setStyle(it) }
        getLargeIcon(context, data)?.let { notificationBuilder.setLargeIcon(it) }
        getGroupKey(data)?.let { notificationBuilder.setGroup(it) }
        getActions(context, data)?.forEach { notificationBuilder.addAction(it) }
        getDeleteIntent(context, data)?.let { notificationBuilder.setDeleteIntent(it) }
    }

    abstract fun getSound(
        data: T,
        context: Context
    ): Uri?

    @ColorRes
    abstract fun getColor(data: T): Int?

    private fun getContentText(data: T): String? {
        return data.message?.takeIf { it.isNotBlank() }
    }

    private fun getContentTitle(data: T): String? {
        return data.title?.takeIf { it.isNotBlank() }
    }

    fun getAutoCancel(data: T): Boolean? {
        return true
    }

    open fun getActions(context: Context, data: T): List<NotificationCompat.Action>? {
        val list = mutableListOf<NotificationCompat.Action>()
        extractActions(data)?.forEach {
            list.add(NotificationCompat.Action.Builder(actionDrawableMap(it.drawable), it.label, getActionsPendingIntent(context, data, it.bundle,it.action)).build())
        }
        return list
    }

    @DrawableRes
    abstract fun actionDrawableMap(type: String): Int

    fun extractActions(data: T): List<Action>? {
        return data.actions
    }

    fun getGroupKey(data: T): String? {
        return null
    }

    abstract fun getLargeIcon(context: Context, data: T): Bitmap?


    fun getStyle(data: T): NotificationCompat.Style? {
        when (data.style) {
            "message" -> {
                return NotificationCompat.BigTextStyle().bigText(getContentText(data))
            }
            "bigPicture"->{
                data.extras?.get("bigPicture")?.let {
                    provideBitmap(it)?.let {
                        return NotificationCompat.BigPictureStyle().bigPicture(it)
                    }
                    return null
                }
                return null
            }
            else -> return null
        }
    }


    abstract fun provideBitmap(imgUrl:String):Bitmap?

    companion object {

        fun getChannelImportance(importance: String): Int {
            return when (importance) {
                "max" -> {
                    NotificationManagerCompat.IMPORTANCE_MAX
                }
                "high" -> {
                    NotificationManagerCompat.IMPORTANCE_HIGH
                }
                "low" -> {
                    NotificationManagerCompat.IMPORTANCE_LOW
                }
                "min" -> {
                    NotificationManagerCompat.IMPORTANCE_MIN
                }
                else -> {
                    NotificationManagerCompat.IMPORTANCE_DEFAULT
                }
            }
        }

        fun getNotificationImportance(importance: String): Int {
            return when (importance) {
                "max" -> {
                    NotificationCompat.PRIORITY_MAX
                }
                "high" -> {
                    NotificationCompat.PRIORITY_HIGH
                }
                "low" -> {
                    NotificationCompat.PRIORITY_LOW
                }
                "min" -> {
                    NotificationCompat.PRIORITY_MIN
                }
                else -> {
                    NotificationCompat.PRIORITY_DEFAULT
                }
            }
        }

    }

    open fun getDeleteIntent(context: Context, data: T): PendingIntent? = null

    @DrawableRes
    abstract fun getSmallIcon(data: T): Int?


    abstract fun activityMap(key: String): Class<*>?
}