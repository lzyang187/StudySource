package com.lzy.studysource.jetpack.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * created by 李朝阳 on 2020/5/10 16:07
 */
@Entity(tableName = "book")
public class Book {
    @NonNull
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "book_name")
    public String bookName;

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + id + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
