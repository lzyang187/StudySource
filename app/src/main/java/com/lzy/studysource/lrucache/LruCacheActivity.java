package com.lzy.studysource.lrucache;

import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.studysource.R;

import java.util.Map;

public class LruCacheActivity extends AppCompatActivity {
    private static final String TAG = "LruCacheActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        LruCache<Integer, String> lruCache = new LruCache<>(5);
        for (int i = 0; i < 10; i++) {
            lruCache.put(i, String.valueOf(i));
        }
        Map<Integer, String> snapshot = lruCache.snapshot();
        for (Integer next : snapshot.keySet()) {
            Log.d(TAG, "onCreate: " + lruCache.get(next));
        }
    }
}
