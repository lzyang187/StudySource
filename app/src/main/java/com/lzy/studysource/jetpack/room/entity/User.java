package com.lzy.studysource.jetpack.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * created by 李朝阳 on 2020/5/10 16:58
 */
@Entity(tableName = "user")
public class User {
    @NonNull
    @PrimaryKey
    public String id;

    @Override
    public String toString() {
        return "User{" +
                "usid='" + id + '\'' +
                '}';
    }
}
