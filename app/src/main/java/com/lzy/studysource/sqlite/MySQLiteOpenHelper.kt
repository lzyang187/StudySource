package com.lzy.studysource.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

/**
 * Created by zhaoyang.li5 on 2022/2/26 14:12
 */
class MySQLiteOpenHelper(
    private val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val createBook =
        "create table Book (" + " id integer primary key autoincrement," + "author text," + "price real," + "pages integer," + "name text," + "category_code integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createBook)
        // 新安装的时候直接在onCreate创建
        db?.execSQL(createCategory)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    private val createCategory =
        "create table Category (" + "id integer primary key autoincrement," + "category_name text," + "category_code integer)"

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.e(TAG, "onUpgrade: $oldVersion  $newVersion")
        // 保证App在跨版本升级的时候， 每一次的数据库修改都能被全部执行
        if (oldVersion <= 1) {
            // 升级上来的
            db?.execSQL(createCategory)
        }
        // 如果用户是直接从第1版升级到第3版，那么两条判断语句都会执行
        if (oldVersion <= 2) {
            db?.execSQL("alter table Book add column category_id integer")
        }
    }

    companion object {
        private const val TAG = "MySQLiteOpenHelper"
    }

}