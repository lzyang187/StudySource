package com.lzy.studysource.contentprovider

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class ReadContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_content)

        readContacts()
    }

    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 姓名
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                // 手机号
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.e(TAG, "readContacts: $displayName  $number")
            }
            cursor.close()
        }

    }

    companion object {
        private const val TAG = "ReadContentActivity"
    }
}