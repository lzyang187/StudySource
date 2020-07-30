package com.lzy.studysource.jetpack.room;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lzy.studysource.R;
import com.lzy.studysource.databinding.ActivityUserAndBookDaoBinding;
import com.lzy.studysource.jetpack.room.entity.UserAndBook;

import java.util.List;

public class UserAndBookDaoActivity extends AppCompatActivity {
    private static final String TAG = "UserAndBookDaoActivity";

    private ActivityUserAndBookDaoBinding mBinding;
    private String usid1 = "usid001", usid2 = "usid002";
    private String bookid1 = "bookid001", bookid2 = "bookid002";
    private String bookName1 = "bookName001", bookName2 = "bookName002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_and_book_dao);
        mBinding.setUserandbookdao(this);
    }

    public void insert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserAndBook userAndBook = new UserAndBook();
                userAndBook.bookId = bookid1;
                userAndBook.bookName = bookName1;
                userAndBook.usid = usid1;
                long l = AppDatabase.getInstance().userAndBookDao().insertUserAndBook(userAndBook);
                Log.d(TAG, "insert userAndBook: " + l);

                UserAndBook userAndBook1 = new UserAndBook();
                userAndBook1.bookId = bookid1;
                userAndBook1.bookName = bookName1;
                userAndBook1.usid = usid2;
                long l2 = AppDatabase.getInstance().userAndBookDao().insertUserAndBook(userAndBook1);
                Log.d(TAG, "insert userAndBook1: " + l2);
            }
        }).start();
    }

    public void delete() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public void query() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<UserAndBook> books = AppDatabase.getInstance().userAndBookDao().queryAllUserAndBook();
                if (books != null) {
                    int size = books.size();
                    Log.d(TAG, "queryAllUserAndBook: size=" + size);
                    for (int i = 0; i < size; i++) {
                        UserAndBook book1 = books.get(i);
                        Log.d(TAG, "query: " + book1);
                    }
                }

                List<UserAndBook> userAndBooks = AppDatabase.getInstance().userAndBookDao().queryUserAndBookByUsid(usid1);
                if (books != null) {
                    int size = userAndBooks.size();
                    Log.d(TAG, "queryUserAndBookByUsid: size=" + size);
                    for (int i = 0; i < size; i++) {
                        UserAndBook book1 = userAndBooks.get(i);
                        Log.d(TAG, "queryUserAndBookByUsid: " + book1);
                    }
                }

                List<UserAndBook> userAndBooks2 = AppDatabase.getInstance().userAndBookDao().queryUserAndBookByUsid(usid2);
                if (books != null) {
                    int size = userAndBooks2.size();
                    Log.d(TAG, "queryUserAndBookByUsid2: size=" + size);
                    for (int i = 0; i < size; i++) {
                        UserAndBook book1 = userAndBooks2.get(i);
                        Log.d(TAG, "queryUserAndBookByUsid2: " + book1);
                    }
                }

                List<UserAndBook> userAndBooks3 = AppDatabase.getInstance().userAndBookDao().queryUserAndBookByBookId(bookid1);
                if (books != null) {
                    int size = userAndBooks3.size();
                    Log.d(TAG, "queryUserAndBookByBookId1: size=" + size);
                    for (int i = 0; i < size; i++) {
                        UserAndBook book1 = userAndBooks3.get(i);
                        Log.d(TAG, "queryUserAndBookByBookId1: " + book1);
                    }
                }

            }
        }).start();
    }
}
