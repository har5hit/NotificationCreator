package com.justadeveloper96.notificationcreator

import android.content.Context


class NotificationCreationFactory(private val ICreators: List<ICreator>){

    @Throws(NoSuchElementException::class)
    operator fun invoke(data: NotificationData, context: Context) {
        val type = data.type
        try {
            return getProperCreator(type)(context,data)
        }catch (e:Exception){
            if (e is NoSuchElementException)
                throw CreatorNotFoundException(type)
        }
    }

    private fun getProperCreator(type:String):ICreator{
        return ICreators.first { it.canHandle(type) }
    }
}