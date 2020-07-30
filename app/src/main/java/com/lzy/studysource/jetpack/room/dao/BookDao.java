package com.lzy.studysource.jetpack.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.lzy.studysource.jetpack.room.entity.Book;

import java.util.List;

/**
 * created by 李朝阳 on 2020/5/10 16:11
 */
@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBook(Book book);

    @Delete
    int deleteBook(Book book);

    @Update
    int updateBook(Book book);

    @Query("SELECT * FROM book")
    List<Book> queryAllBook();

    @Query("SELECT * FROM BOOK WHERE id = :bookId")
    Book queryBookById(String bookId);

}
