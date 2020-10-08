package com.justadeveloper96.notificationcreatordemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.justadeveloper96.notificationcreator.NotificationCreatorPool
import com.justadeveloper96.notificationcreatordemo.di.Injector
import com.justadeveloper96.notificationcreatordemo.notification.samples.SamplePayloads
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pool: NotificationCreatorPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        Injector.graph.inject(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        FirebaseMessaging.getInstance().subscribeToTopic("test")

        generateLocalSetup()
    }

    private fun generateLocalSetup() {
        btn_simple.setOnClickListener {
            pool.invoke(SamplePayloads.simple(), this)
        }
    }

}
