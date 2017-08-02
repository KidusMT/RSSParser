package com.kidusmt.android.rssparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_rss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_rss = (TextView)findViewById(R.id.tv_main);
        ReadRSS rss = new ReadRSS(this);
        rss.execute();
    }
}
