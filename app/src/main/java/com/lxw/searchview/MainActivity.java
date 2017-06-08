package com.lxw.searchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchView = (SearchView) findViewById(R.id.search_view);
    }

    public void start(View v) {
        mSearchView.startAnimation(SearchView.STATE_ANIM_START);
    }
    public void stop(View v) {
        mSearchView.startAnimation(SearchView.STATE_ANIM_STOP);
    }
    
}
