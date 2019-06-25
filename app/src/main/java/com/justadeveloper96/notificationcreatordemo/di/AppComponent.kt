package com.justadeveloper96.notificationcreatordemo.di

import com.justadeveloper96.notificationcreatordemo.fcm.MyFirebaseMessagingService
import com.justadeveloper96.notificationcreatordemo.notification.CreatorsModule
import com.justadeveloper96.notificationcreatordemo.notification.FactoryModule
import dagger.Component


@Component(modules = [AppModule::class,CreatorsModule::class,FactoryModule::class])
interface AppComponent{

    fun inject(service: MyFirebaseMessagingService)
}