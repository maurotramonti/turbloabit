package com.maurotramonti.turbolabit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if (i.getIntExtra("theme", 0) == 0) this.setTheme(R.style.AppTheme);
        else if (i.getIntExtra("theme", 0) == 1) this.setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_info);

    }


}