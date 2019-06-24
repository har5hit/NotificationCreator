package com.justadeveloper96.notificationcreator

import android.content.Context

interface ICreator{

    fun canHandle(type:String):Boolean

    operator fun invoke(context: Context, data: NotificationData)
}