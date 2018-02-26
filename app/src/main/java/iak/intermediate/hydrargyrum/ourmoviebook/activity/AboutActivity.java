package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

public class AboutActivity extends AppCompatActivity {

    WebView wbAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("About");

        wbAbout = (WebView) findViewById(R.id.wb_about);
        wbAbout.setBackgroundColor(0x00000000);
        String aboutText = "<html><body><p align=\"center\">";
        aboutText += "Our Movie Book App provide you list of Box Office films that you make favorite book list!<br/><br/>";
        aboutText += "This application submitted for final project of <br/>";
        aboutText += "Indonesia Android Kejar Program<br/>";
        aboutText += "https://indonesiaandroidkejar.id/<br/><br/>";
        aboutText += "Written with chocolate by <br/>";
        aboutText += "Muhammad Faishal<br/>";
        aboutText += "https://github.com/cuuprum/OurMovieBook<br/><br/>";
        aboutText += "This app under MIT License. 2018.";
        aboutText += "</p></body></html>";
        wbAbout.loadData(aboutText, "text/html", "utf-8");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, MovieActivity.class);
        startActivity(intent);
    }
}
