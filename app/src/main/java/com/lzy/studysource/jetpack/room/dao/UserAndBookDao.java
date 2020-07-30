package com.lzy.studysource.jetpack.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.lzy.studysource.jetpack.room.entity.UserAndBook;

import java.util.List;

/**
 * created by 李朝阳 on 2020/5/10 17:54
 */
@Dao
public interface UserAndBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserAndBook(UserAndBook userAndBook);

    @Delete
    int deleteUserAndBook(UserAndBook userAndBook);

    @Update
    int updateUserAndBook(UserAndBook book);

    @Query("SELECT * FROM user_and_book")
    List<UserAndBook> queryAllUserAndBook();

    @Query("SELECT * FROM user_and_book WHERE bookId = :bookId")
    List<UserAndBook> queryUserAndBookByBookId(String bookId);

    @Query("SELECT * FROM user_and_book WHERE usid = :usid")
    List<UserAndBook> queryUserAndBookByUsid(String usid);

}
