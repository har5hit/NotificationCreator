package com.justadeveloper96.notificationcreatordemo.notification

import com.justadeveloper96.notificationcreator.ICreator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
interface CreatorsModule{

    @Binds
    @IntoSet
    fun one(module:AppDelegate):ICreator

}