package com.lzy.studysource.jetpack.room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lzy.studysource.R;
import com.lzy.studysource.databinding.ActivityBookDaoBinding;
import com.lzy.studysource.jetpack.room.entity.Book;

import java.util.List;

public class BookDaoActivity extends AppCompatActivity {
    private static final String TAG = "BookDaoActivity";
    private ActivityBookDaoBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_dao);
        mBinding.setBookdao(this);
    }

    public void insert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Book book = new Book();
                book.id = "001";
                book.bookName = "三国演义";
                long l = AppDatabase.getInstance().bookDao().insertBook(book);
                Log.d(TAG, "insert: " + l);
            }
        }).start();
    }

    public void delete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Book book = new Book();
                book.id = "001";
                long l = AppDatabase.getInstance().bookDao().deleteBook(book);
                Log.d(TAG, "delete: " + l);
            }
        }).start();
    }

    public void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Book book = new Book();
                book.id = "001";
                book.bookName = "三国演义新名字";
                int i = AppDatabase.getInstance().bookDao().updateBook(book);
                Log.d(TAG, "update: " + i);
            }
        }).start();
    }

    public void query() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Book> books = AppDatabase.getInstance().bookDao().queryAllBook();
                if (books != null) {
                    int size = books.size();
                    Log.d(TAG, "queryAllBook: size=" + size);
                    for (int i = 0; i < size; i++) {
                        Book book1 = books.get(i);
                        Log.d(TAG, "query: " + book1);
                    }
                }

                Book book1 = AppDatabase.getInstance().bookDao().queryBookById("001");
                Log.d(TAG, "queryBookById: " + book1);
            }
        }).start();
    }

    public void jump() {
        startActivity(new Intent(this, UserBookJoinDaoActivity.class));
    }

    public void jump1() {
        startActivity(new Intent(this, UserAndBookDaoActivity.class));
    }

}
