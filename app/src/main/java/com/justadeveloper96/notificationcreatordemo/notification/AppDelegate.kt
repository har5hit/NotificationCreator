package com.justadeveloper96.notificationcreatordemo.notification

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import com.justadeveloper96.notificationcreator.GenericCreator
import com.justadeveloper96.notificationcreator.NotificationData
import com.justadeveloper96.notificationcreatordemo.MainActivity
import com.justadeveloper96.notificationcreatordemo.R
import javax.inject.Inject

open class AppDelegate @Inject constructor(private val context: Context) : GenericCreator() {

    override fun getColor(data: NotificationData): Int? = null

    override fun getSound(data: NotificationData, context: Context): Uri? = null

    override fun provideBitmap(imgUrl: String): Bitmap? {
        return getBitmapFromServer(imgUrl)
    }

    private fun getBitmapFromServer(imgUrl: String) = Glide.with(context).asBitmap().load(imgUrl).submit().get()

    override fun actionMap(key:String): String? = when(key) {
        "action_view" -> Intent.ACTION_VIEW
        else-> null
    }

    override fun actionDrawableMap(type: String) =  R.drawable.ic_launcher_foreground


    override fun getLargeIcon(context: Context, data: NotificationData): Bitmap? = data.iconImageUrl?.let {
        if (URLUtil.isHttpUrl(it) || URLUtil.isHttpsUrl(it)){
            return getBitmapFromServer(it)
        }else{
            BitmapFactory.decodeResource(context.resources,actionDrawableMap(it))
        }
    }


    override fun getSmallIcon(data: NotificationData) = R.drawable.ic_launcher_foreground

    override fun activityMap(key:String): Class<*>? =  when(key) {
        "main" -> MainActivity::class.java
        else-> null
    }

    override fun canHandle(type: String): Boolean = true


}