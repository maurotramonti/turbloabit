package com.maurotramonti.turbolabit;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity {
    private WebView wv1;
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), 0);

        if (sharedPref.getInt("theme", 0) == 1) {
            this.setTheme(R.style.DarkTheme);
        }

        else if (sharedPref.getInt("theme", 0) == 0){
            this.setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (sharedPref.getInt("actionbar_showed", 1) == 0) {
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name);
        }

        else if (sharedPref.getInt("actionbar_showed", 1) == 1) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (sharedPref.getInt("theme", 0) == 0) getSupportActionBar().setIcon(R.drawable.logo_actionbar);
            else if (sharedPref.getInt("theme", 0) == 1) getSupportActionBar().setIcon(R.drawable.logo_actionbar_red);
            getSupportActionBar().setTitle("");
        }


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
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
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
                i.putExtra("theme", sharedPref.getInt("theme", 0));
                startActivity(i);
                break;
            case R.id.impostazioni_menu:
                Intent i2 = new Intent(this, SettingsActivity.class);
                i2.putExtra("theme", sharedPref.getInt("theme", 0));
                startActivity(i2);
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