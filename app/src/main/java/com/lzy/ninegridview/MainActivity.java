package com.lzy.ninegridview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lzy.ninegridview.model.evaluation.EvaluationActivity;
import com.lzy.ninegridview.model.news.NewsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("使用RecyclerView展示news");
        strings.add("使用ListView展示Evaluation");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, NewsActivity.class);
                break;
            case 1:
                intent.setClass(this, EvaluationActivity.class);
                break;
        }
        startActivity(intent);
    }
}
