package com.lzy.studysource.jetpack.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lzy.studysource.MyApplication;
import com.lzy.studysource.jetpack.room.dao.BookDao;
import com.lzy.studysource.jetpack.room.dao.UserAndBookDao;
import com.lzy.studysource.jetpack.room.dao.UserBookJoinDao;
import com.lzy.studysource.jetpack.room.dao.UserDao;
import com.lzy.studysource.jetpack.room.entity.Book;
import com.lzy.studysource.jetpack.room.entity.User;
import com.lzy.studysource.jetpack.room.entity.UserAndBook;
import com.lzy.studysource.jetpack.room.entity.UserBookJoin;

/**
 * created by 李朝阳 on 2020/5/10 16:19
 */
@Database(entities = {Book.class, User.class, UserBookJoin.class, UserAndBook.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    public abstract UserDao userDao();

    public abstract UserBookJoinDao userBookJoinDao();

    public abstract UserAndBookDao userAndBookDao();

    private static AppDatabase mIntance;

    public static AppDatabase getInstance() {
        if (mIntance == null) {
            synchronized (AppDatabase.class) {
                if (mIntance == null) {
                    mIntance = Room.databaseBuilder(MyApplication.getInstance(),
                            AppDatabase.class, "books.db").build();
                }
            }
        }
        return mIntance;
    }

}
