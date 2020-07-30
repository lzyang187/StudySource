package com.lzy.studysource.jetpack.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lzy.studysource.jetpack.room.entity.Book;
import com.lzy.studysource.jetpack.room.entity.User;
import com.lzy.studysource.jetpack.room.entity.UserBookJoin;

import java.util.List;

/**
 * created by 李朝阳 on 2020/5/10 15:42
 */
@Dao
public interface UserBookJoinDao {
    @Insert
    long insert(UserBookJoin userBookJoin);

    @Query("SELECT * FROM user " +
            "INNER JOIN user_book_join " +
            "ON user.id=user_book_join.usid " +
            "WHERE user_book_join.bookId=:bookId")
    List<User> getUsersByBook(final String bookId);

    @Query("SELECT * FROM book " +
            "INNER JOIN user_book_join " +
            "ON book.id=user_book_join.bookId " +
            "WHERE user_book_join.usid=:usid")
    List<Book> getBooksByUser(final String usid);
}
