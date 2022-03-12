package com.lzy.studysource.contentprovider

import android.content.ContentValues
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getIntOrNull
import com.lzy.studysource.R

class ReadContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_content)
        findViewById<Button>(R.id.query_btn).setOnClickListener {
            readContacts()
        }
        findViewById<Button>(R.id.insert_contact).setOnClickListener {
//            // 修改联系人
//            val contentValues = ContentValues()
//            contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "13205693712")
//            val rows = contentResolver.update(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                contentValues,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
//                arrayOf("Hhhh")
//            )
//            Log.e(TAG, "update return: $rows")
            insertContacts()
        }
    }

    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOf(
                ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ), null, null, null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // id
                val id =
                    cursor.getIntOrNull(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID))
                // 姓名
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                // 手机号
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.e(TAG, "readContacts: $id $displayName  $number")
            }
            cursor.close()
        }

    }

    /**
     * 会闪退
     */
    private fun insertContacts() {
        val contentValues = ContentValues()
        contentValues.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, "新增1")
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "110")
        val uri = contentResolver.insert(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, contentValues
        )
        Log.e(TAG, "insertContacts: $uri")
    }

    companion object {
        private const val TAG = "ReadContentActivity"
    }
}