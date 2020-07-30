package com.lzy.studysource.jetpack.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * 用户和书的关系实体
 * created by 李朝阳 on 2020/5/10 15:33
 */
@Entity(tableName = "user_book_join",
        primaryKeys = {"bookId", "usid"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "usid"),
                @ForeignKey(entity = Book.class,
                        parentColumns = "id",
                        childColumns = "bookId")
        })
public class UserBookJoin {
    @NonNull
    public String bookId;
    @NonNull
    public String usid;

    public UserBookJoin(@NonNull String bookId, @NonNull String usid) {
        this.bookId = bookId;
        this.usid = usid;
    }
}
