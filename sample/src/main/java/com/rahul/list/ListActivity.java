package com.rahul.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//Developed by Rahul Mane https://github.com/rahulandroid
public class ListActivity extends AppCompatActivity {
    // A setting for how many items should be loaded at once from the server
    Context context;
    private ListView listView;
    private ArrayList<String> arr;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListAdapter(context, null);
        arr = new ArrayList<>();

        final String[] names = new String[]{"John", "Brown", "Black", "Luck"};

        final Random rnd = new Random();

        for (int i = 0; i < 20; ++i)
            arr.add(names[0]);

        View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        final ProgressBar progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);

        adapter = new ListAdapter(context, arr);
        if (adapter != null)
            listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean isLoading = false;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemsCount, int totalItemsCount) {
                boolean toAdd = firstVisibleItem + visibleItemsCount >= totalItemsCount && !isLoading;
                if (toAdd) {
                    load(totalItemsCount - 1);
                }
            }

            private void load(int itemsCount) {
                isLoading = true;
                if (itemsCount > 50) {
                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 20; ++i)
                            adapter.addElement(names[0]);

                        adapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, 2000);
            }
        });
        listView.addFooterView(footer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load the first page to start demo

    }

}
