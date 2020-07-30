package com.lzy.studysource.jetpack.room;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lzy.studysource.R;
import com.lzy.studysource.databinding.ActivityUserBookJoinDaoBinding;
import com.lzy.studysource.jetpack.room.entity.Book;
import com.lzy.studysource.jetpack.room.entity.User;
import com.lzy.studysource.jetpack.room.entity.UserBookJoin;

import java.util.List;

public class UserBookJoinDaoActivity extends AppCompatActivity {
    private static final String TAG = "UserBookJoinDaoActivity";
    private ActivityUserBookJoinDaoBinding mBinding;
    private User user1, user2;
    private Book book1, book2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_book_join_dao);
        mBinding.setUserbookdao(this);
        user1 = new User();
        user1.id = "user1Id";
        user2 = new User();
        user2.id = "user2Id";
        book1 = new Book();
        book1.id = "book1Id";
        book1.bookName = "book1Name";
        book2 = new Book();
        book2.id = "book2Id";
        book2.bookName = "book2Name";
    }

    public void insert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long bookl1 = AppDatabase.getInstance().bookDao().insertBook(book1);
                Log.d(TAG, "insertbook1: " + bookl1);
                long bookl2 = AppDatabase.getInstance().bookDao().insertBook(book2);
                Log.d(TAG, "insertbook2: " + bookl2);
                long userl1 = AppDatabase.getInstance().userDao().insertUser(user1);
                Log.d(TAG, "insertuser1: " + userl1);
                long userl2 = AppDatabase.getInstance().userDao().insertUser(user2);
                Log.d(TAG, "insertuser2: " + userl2);

                UserBookJoin userBookJoin = new UserBookJoin(book1.id, user1.id);
                long insert = AppDatabase.getInstance().userBookJoinDao().insert(userBookJoin);
                Log.d(TAG, "userBookJoin: " + insert);
            }
        }).start();
    }

    public void delete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Book book = new Book();
//                book.bookId = "001";
//                long l = AppDatabase.getInstance().bookDao().deleteBook(book);
//                Log.d(TAG, "delete: " + l);
            }
        }).start();
    }

    public void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Book book = new Book();
//                book.bookId = "001";
//                book.bookName = "三国演义新名字";
//                int i = AppDatabase.getInstance().bookDao().updateBook(book);
//                Log.d(TAG, "update: " + i);
            }
        }).start();
    }

    public void query() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Book> booksByUser = AppDatabase.getInstance().userBookJoinDao().getBooksByUser(user1.id);
                if (booksByUser != null) {
                    int size = booksByUser.size();
                    Log.d(TAG, "getBooksByUser: size=" + size);
                    for (int i = 0; i < size; i++) {
                        Book book1 = booksByUser.get(i);
                        Log.d(TAG, "getBooksByUser: " + book1);
                    }
                }

                List<User> usersByBook = AppDatabase.getInstance().userBookJoinDao().getUsersByBook(book1.id);
                if (usersByBook != null) {
                    int size = usersByBook.size();
                    Log.d(TAG, "usersByBook: size=" + size);
                    for (int i = 0; i < size; i++) {
                        User user = usersByBook.get(i);
                        Log.d(TAG, "usersByBook: " + user);
                    }
                }

            }
        }).start();
    }
}
