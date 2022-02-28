package com.lzy.studysource.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.lzy.studysource.sqlite.MySQLiteOpenHelper

class MyContentProvider : ContentProvider() {

    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.lzy.studysource.provider"
    private var dbHelper: MySQLiteOpenHelper? = null

    private val uriMatcher by lazy {
        UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(authority, "book", bookDir)
            addURI(authority, "book/#", bookItem)
            addURI(authority, "category", categoryDir)
            addURI(authority, "category/#", categoryItem)
        }
    }

    /**
     * 初始化ContentProvider的时候调用。通常会在这里完成对数据库的创建和升级等操作，
     * 返回true表示ContentProvider初始化成功，返回false则表示失败
     */
    override fun onCreate(): Boolean = context?.let {
        Log.d(TAG, "onCreate: MyContentProvider")
        dbHelper = MySQLiteOpenHelper(it, "BookStore.db", null, 2)
        true
    } ?: false


    /**
     * 从ContentProvider中删除数据。uri参数用于确定删除哪一张表中的数据，
     * selection和selectionArgs参数用于约束删除哪些行，被删除的行数将作为返回值返回
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var deletedRows = 0
        dbHelper?.let {
            val writableDatabase = it.writableDatabase
            deletedRows = when (uriMatcher.match(uri)) {
                bookDir -> writableDatabase.delete(BOOK_TABLE_NAME, selection, selectionArgs)
                bookItem -> {
                    // 会将内容URI权限之后的部分以“/”符号进行分割，并把分割后的结果放入一个字符串列表中，
                    // 那这个列表的第0个位置存放的就是路径，第1个位置存放的就是id
                    val bookId = uri.pathSegments[0]
                    writableDatabase.delete(BOOK_TABLE_NAME, "id = ?", arrayOf(bookId))
                }
                categoryDir -> writableDatabase.delete(
                    CATEGORY_TABLE_NAME, selection, selectionArgs
                )
                categoryItem -> {
                    val categoryId = uri.pathSegments[0]
                    writableDatabase.delete(CATEGORY_TABLE_NAME, "id = ?", arrayOf(categoryId))
                }
                else -> 0
            }
        }
        return deletedRows
    }

    /**
     * 根据传入的内容URI返回相应的MIME类型
     */
    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        bookDir -> "vnd.android.cursor.dir/vnd.com.lzy.studysource.provider.book"
        bookItem -> "vnd.android.cursor.item/vnd.com.lzy.studysource.provider.book"
        categoryDir -> "vnd.android.cursor.dir/vnd.com.lzy.studysource.provider.category"
        categoryItem -> "vnd.android.cursor.item/vnd.com.lzy.studysource.provider.category"
        else -> null
    }


    /**
     * 向ContentProvider中添加一条数据。uri参数用于确定要添加到的表，待添加的数据保存在values参数中。添加完成后，返回一个用于表示这条新记录的URI
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var uriReturn: Uri? = null
        dbHelper?.let {
            val writableDatabase = it.writableDatabase
            uriReturn = when (uriMatcher.match(uri)) {
                bookDir, bookItem -> {
                    val newBookId = writableDatabase.insert(BOOK_TABLE_NAME, null, values)
                    Uri.parse("content://$authority/book/$newBookId")
                }
                categoryDir, categoryItem -> {
                    val newCategoryId = writableDatabase.insert("Category", null, values)
                    Uri.parse("content://$authority/category/$newCategoryId")
                }
                else -> null

            }
        }
        return uriReturn
    }

    /**
     * 从ContentProvider中查询数据。uri参数用于确定查询哪张表，projection参数用于确定查询哪些列，
     * selection和selectionArgs参数用于约束查询哪些行，sortOrder参数用于对结果进行排序，查询的结果存放在Cursor对象中返回
     */
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null
        dbHelper?.let {
            val readableDatabase = it.readableDatabase
            cursor = when (uriMatcher.match(uri)) {
                bookDir -> readableDatabase.query(
                    BOOK_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
                )
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    readableDatabase.query(
                        BOOK_TABLE_NAME,
                        projection,
                        "id = ?",
                        arrayOf(bookId),
                        null,
                        null,
                        sortOrder
                    )
                }
                categoryDir -> readableDatabase.query(
                    CATEGORY_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
                )
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    readableDatabase.query(
                        CATEGORY_TABLE_NAME,
                        projection,
                        "id = ?",
                        arrayOf(categoryId),
                        null,
                        null,
                        sortOrder
                    )
                }
                else -> null
            }
        }
        return cursor
    }

    /**
     * 更新ContentProvider中已有的数据。uri参数用于确定更新哪一张表中的数据，新数据保存在values参数中，selection和selectionArgs参数用于约束更新哪些行，
     * 受影响的行数将作为返回值返回
     */
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?
    ): Int {
        var rows = 0
        dbHelper?.let {
            val writableDatabase = it.writableDatabase
            rows = when (uriMatcher.match(uri)) {
                bookDir -> writableDatabase.update(
                    BOOK_TABLE_NAME, values, selection, selectionArgs
                )
                bookItem -> {
                    val bookId = uri.pathSegments[0]
                    writableDatabase.update(BOOK_TABLE_NAME, values, "id = ?", arrayOf(bookId))
                }
                categoryDir -> writableDatabase.update(
                    CATEGORY_TABLE_NAME, values, selection, selectionArgs
                )
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    writableDatabase.update(
                        CATEGORY_TABLE_NAME, values, "id = ?", arrayOf(categoryId)
                    )
                }
                else -> 0
            }
        }
        return rows
    }

    companion object {
        private const val TAG = "MyContentProvider"
        private const val BOOK_TABLE_NAME = "Book"
        private const val CATEGORY_TABLE_NAME = "Category"
    }
}