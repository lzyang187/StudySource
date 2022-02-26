package com.lzy.studysource.sqlite

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.lzy.studysource.R

class SQLiteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)
        val sqliteOpenHelper = MySQLiteOpenHelper(this, "BookStore.db", null, 2)
        findViewById<Button>(R.id.create_btn).setOnClickListener {
            // 获取的时候，没有则走创建(onCreate)
            sqliteOpenHelper.writableDatabase
        }
        val writableDatabase = sqliteOpenHelper.writableDatabase
        // 增
        findViewById<Button>(R.id.insert_btn).setOnClickListener {
            val contentValues = ContentValues().apply {
                put("author", "罗贯中")
                put("price", 30.5)
                put("pages", 5000)
                put("name", "三国演义")
            }
            writableDatabase.insert("Book", null, contentValues)
        }
        // 改
        findViewById<Button>(R.id.update_btn).setOnClickListener {
            val contentValues = ContentValues().apply {
                put("price", 20.5)
            }
            // 表示更新所有name等于?的行，而?是一个占位符，可以通过第四个参数提供的一个字符串数组为第三个参数中的每个占位符指定相应的内容
            writableDatabase.update("Book", contentValues, "name = ?", arrayOf("三国演义"))
        }
        // 查
        findViewById<Button>(R.id.query_btn).setOnClickListener {
            val cursor = writableDatabase.query(
                "Book", arrayOf("author", "price"), "name = ?", arrayOf("三国演义"), null, null, null
            )
            // 将数据的指针移动到第一行的位置
            if (cursor.moveToFirst()) {
                do {
                    val author = cursor.getStringOrNull(cursor.getColumnIndex("author"))
                    val price = cursor.getDoubleOrNull(cursor.getColumnIndex("price"))
                    val pages = cursor.getIntOrNull(cursor.getColumnIndex("pages"))
                    val name = cursor.getStringOrNull(cursor.getColumnIndex("name"))
                    Log.i(TAG, "query: $author  $price  $pages  $name")
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        // 删
        findViewById<Button>(R.id.delete_btn).setOnClickListener {
            writableDatabase.delete("Book", "pages > ?", arrayOf("500"))
        }
        // 事务
        findViewById<Button>(R.id.transaction_btn).setOnClickListener {
            // 开始事务
            writableDatabase.beginTransaction()
            try {
                writableDatabase.delete("Book", null, null)
                if (false) {
                    throw RuntimeException("手动抛出异常")
                }
                val contentValues = ContentValues().apply {
                    put("author", "曹雪芹")
                    put("price", 60.5)
                    put("pages", 6000)
                    put("name", "红楼梦")
                }
                writableDatabase.insert("Book", null, contentValues)
                // 事务执行成功
                writableDatabase.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // 结束事务
                writableDatabase.endTransaction()
            }
        }
    }

    companion object {
        private const val TAG = "SQLiteActivity"
    }
}