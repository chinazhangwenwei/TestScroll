package com.baoyz.pullrefreshlayout.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = "string " + i;
        }
        recyclerView.setAdapter(new RecyclerViewActivity.ArrayAdapter(this, array));

    }

    public void onListViewClick(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    public void onRecyclerViewClick(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void onScrollViewClick(View view) {
        startActivity(new Intent(this, ScrollViewActivity.class));
    }

}
