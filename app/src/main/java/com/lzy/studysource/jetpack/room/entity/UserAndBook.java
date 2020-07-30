package com.lzy.studysource.jetpack.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * 用户和书实体类，2个属性组成主键
 * created by 李朝阳 on 2020/5/10 17:51
 */
@Entity(tableName = "user_and_book",
        primaryKeys = {"bookId", "usid"})
public class UserAndBook {
    @NonNull
    public String bookId;
    @NonNull
    public String usid;
    public String bookName;

    @Override
    public String toString() {
        return "UserAndBook{" +
                "bookId='" + bookId + '\'' +
                ", usid='" + usid + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
