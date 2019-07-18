package com.lzy.studysource.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

import java.util.ArrayList;

public class SendSmsActivity extends AppCompatActivity implements View.OnClickListener {
    private MySmsReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        findViewById(R.id.btn).setOnClickListener(this);
        register();
    }

    private class MySmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
                Bundle bundle = intent.getExtras();
                SmsMessage message = null;
                if (bundle != null) {
                    Object[] smsObj = (Object[]) bundle.get("pdus");
                    for (Object o : smsObj) {
                        message = SmsMessage.createFromPdu((byte[]) o);
                        String messageBody = message.getDisplayMessageBody();
                        Log.e("cyli8", messageBody);
                    }
                }
            }
        }
    }

    private void register() {
        mReceiver = new MySmsReceiver();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReceiver, filter);
    }

    private void unRegister() {
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            SendSms();
        }
    }

    private void SendSms() {
        // 获取数据内容
        String number = "13205693712";
        String content = "测试发短信";
        // 使用SmsManager类
        SmsManager smsManager = SmsManager.getDefault();
        // 短信内容可能很长，需要分发
        ArrayList<String> parts = smsManager.divideMessage(content);
        //发送短信，需要权限
        smsManager.sendMultipartTextMessage(number, null, parts, null, null);
//        for (String text : parts) {
//            smsManager.sendTextMessage(number, null, text, null, null);
//        }
    }

    private void sendSmsJump() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "13205693712"));
        intent.putExtra("sms_body", "错错错");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegister();
    }
}
