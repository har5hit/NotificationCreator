package com.justadeveloper96.notificationcreatordemo

import android.app.Application
import com.justadeveloper96.notificationcreatordemo.di.AppModule
import com.justadeveloper96.notificationcreatordemo.di.DaggerAppComponent
import com.justadeveloper96.notificationcreatordemo.di.Injector

class MyApp:Application(){


    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        Injector.graph=DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}