package com.justadeveloper96.notificationcreatordemo.notification

import com.justadeveloper96.notificationcreator.ICreator
import com.justadeveloper96.notificationcreator.NotificationCreatorPool
import dagger.Module
import dagger.Provides


@Module
class FactoryModule{

    @Provides
    fun provideCreatorManager(list: Set<@JvmSuppressWildcards ICreator>): NotificationCreatorPool {
        return NotificationCreatorPool(list.toList())
    }
}