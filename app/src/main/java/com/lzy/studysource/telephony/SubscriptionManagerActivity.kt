package com.lzy.studysource.telephony

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class SubscriptionManagerActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription_manager)
        val tv = findViewById<TextView>(R.id.tv)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val subscriptionManager =
                getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            subscriptionManager.apply {
                val activeSubscriptionInfoCount = activeSubscriptionInfoCount
                val activeSubscriptionInfoList = activeSubscriptionInfoList

                val accessibleSubscriptionInfoList = accessibleSubscriptionInfoList



                val str =
                    "activeSubscriptionInfoCount=$activeSubscriptionInfoCount \n activeSubscriptionInfoList$activeSubscriptionInfoList \n \n" +
                            "accessibleSubscriptionInfoList=$accessibleSubscriptionInfoList \n"
                tv.text = str
            }
        }
    }
}