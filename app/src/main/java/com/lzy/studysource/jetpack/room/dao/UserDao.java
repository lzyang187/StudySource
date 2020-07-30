package com.lzy.studysource.jetpack.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.lzy.studysource.jetpack.room.entity.User;

import java.util.List;

/**
 * created by 李朝阳 on 2020/5/10 17:02
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Delete
    int deleteUser(User user);

    @Update
    int updateUser(User user);

    @Query("SELECT * FROM user")
    List<User> queryAllUser();

    @Query("SELECT * FROM user WHERE id = :usid")
    User queryUserById(String usid);
}
