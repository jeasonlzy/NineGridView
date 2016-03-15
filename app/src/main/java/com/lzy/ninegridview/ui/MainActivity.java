package com.lzy.ninegridview.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lzy.ninegridview.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("Fill Style");
        strings.add("Grid Style");
        strings.add("List Grid");
        strings.add("Test");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, FillStyleActivity.class);
                break;
            case 1:
                intent.setClass(this, GridStyleActivity.class);
                break;
            case 2:
                intent.setClass(this, ListImageActivity.class);
                break;
            case 3:
                intent.setClass(this, TestNineActivity.class);
                break;
        }
        startActivity(intent);
    }
}
