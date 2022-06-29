package com.lzy.studysource.telephony

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class TelephonyManagerActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephony_manager)
        val tv = findViewById<TextView>(R.id.tv)

        val manager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        manager.apply {
            val imei = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imei
                } else {
                    ""
                }
            } catch (e: Exception) {
            }
            val meid = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    meid
                } else {
                    ""
                }
            } catch (e: Exception) {
            }
            val softwareVersion = try {
                deviceSoftwareVersion
            } catch (e: Exception) {
            }
            // 电话状态
            val callStateForSubscription = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                callStateForSubscription
            } else {
                ""
            }
            val allCellInfo = allCellInfo
            val line1Number = line1Number

            val networkOperator = networkOperator
            val networkOperatorName = networkOperatorName
            val networkCountryIso = networkCountryIso
            val dataNetworkType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dataNetworkType
            } else {
                ""
            }
            val phoneType = phoneType

            val simCountryIso = simCountryIso
            val simOperator = simOperator
            val simOperatorName = simOperatorName
            val simSerialNumber = try {
                simSerialNumber
            } catch (e: Exception) {
            }
            val simState = simState

            val subscriberId = try {
                subscriberId
            } catch (e: Exception) {
            }

            val hasIccCard = hasIccCard()

            tv.text =
                "imei：$imei \n meid：$meid \n softwareVersion：$softwareVersion \n " +
                        "callStateForSubscription：$callStateForSubscription \n " +
                        "allCellInfo：$allCellInfo \n line1Number：$line1Number \n " +
                        "networkOperator：$networkOperator \n networkOperatorName：$networkOperatorName \n" +
                        "networkCountryIso：$networkCountryIso \n dataNetworkType：$dataNetworkType \n phoneType：$phoneType \n \n " +
                        "simCountryIso：$simCountryIso \n simOperator：$simOperator \n simOperatorName：$simOperatorName \n" +
                        "simSerialNumber：$simSerialNumber \n simState：$simState \n subscriberId：$subscriberId \n" +
                        "\n hasIccCard：$hasIccCard \n"
        }
    }

}