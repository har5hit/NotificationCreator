package com.justadeveloper96.notificationcreator

import android.content.Context

interface ICreator<T> {

    fun canHandle(type:String):Boolean

    operator fun invoke(context: Context, data: T)
}