package com.justadeveloper96.notificationcreator

import android.content.Context


class NotificationCreatorPool(private val creators: List<ICreator<*>>) {

    @Throws(NoSuchElementException::class)
    operator fun invoke(data: NotificationData, context: Context) {
        val type = data.type
        try {
            return getProperCreator(type)(context, data)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is NoSuchElementException)
                throw CreatorNotFoundException(type)
        }
    }

    private fun getProperCreator(type: String): ICreator<*> {
        return creators.first { it.canHandle(type) }
    }
}