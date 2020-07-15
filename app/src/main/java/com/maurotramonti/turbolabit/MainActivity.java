package com.maurotramonti.turbolabit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private WebView wv1;
    private boolean actionbar_title;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), 0);
        int actionbar_config = sharedPref.getInt("actionbar_showed", 1);
        if (actionbar_config == 0) {
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name);
            actionbar_title = true;
        }

        else if (actionbar_config == 1) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.logo_actionbar);
            getSupportActionBar().setTitle("");
            actionbar_title = false;
        }


        WebView internet_browser = (WebView) findViewById(R.id.internet_browser);
        wv1=(WebView)findViewById(R.id.internet_browser);
        wv1.setWebViewClient(new TLIBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.loadUrl("https://turbolab.it");

        FloatingActionButton reloadButton = findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv1.reload();
            }
        });

        FloatingActionButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv1.canGoBack()) wv1.goBack();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.site_icon:
                wv1.loadUrl("https://turbolab.it");
                break;
            case R.id.forum_icon:
                wv1.loadUrl("https://turbolab.it/forum");
                break;
            case R.id.info_option:
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case R.id.scelta_testo_logo:
                if (actionbar_title) {
                    getSupportActionBar().setDisplayUseLogoEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setIcon(R.drawable.logo_actionbar);
                    getSupportActionBar().setTitle("");
                    actionbar_title = false;
                    item.setTitle("Mostra titolo");
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("actionbar_showed", 1);
                    editor.commit();
                }

                else {
                    getSupportActionBar().setTitle("Turbolab.it");
                    getSupportActionBar().setDisplayUseLogoEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    actionbar_title = true;
                    item.setTitle("Mostra icona");
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("actionbar_showed", 0);
                    editor.commit();
                }

                break;
            case R.id.quit_option:
                this.finish();
                break;

        }
        return false;
    }

    private class TLIBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}