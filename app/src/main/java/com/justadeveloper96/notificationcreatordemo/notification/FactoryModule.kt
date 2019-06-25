package com.justadeveloper96.notificationcreatordemo.notification

import com.justadeveloper96.notificationcreator.ICreator
import com.justadeveloper96.notificationcreator.NotificationCreationFactory
import dagger.Module
import dagger.Provides


@Module
class FactoryModule{

    @Provides
    fun provideCreatorManager(list:Set<@JvmSuppressWildcards ICreator>): NotificationCreationFactory {
        return NotificationCreationFactory(list.toList())
    }
}