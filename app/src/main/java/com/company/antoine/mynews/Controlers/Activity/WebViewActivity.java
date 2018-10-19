package com.company.antoine.mynews.Controlers.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.company.antoine.mynews.R;

public class WebViewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.web_view_activity);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
