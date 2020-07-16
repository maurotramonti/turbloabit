package com.maurotramonti.turbolabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    int switch_theme_state;
    int title_bar_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        int dark_theme = i.getIntExtra("theme", 0);
        if (dark_theme == 0) {
            this.setTheme(R.style.AppTheme);
        } else if (dark_theme == 1) {
            this.setTheme(R.style.DarkTheme);
        }
        switch_theme_state = dark_theme;
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        Switch theme_switch = (Switch) findViewById(R.id.dark_theme_switch);
        if (dark_theme == 1) {
            theme_switch.setChecked(true);
        } else {
            theme_switch.setChecked(false);
        }
        RadioButton rbi = (RadioButton) findViewById(R.id.radioButtonIcon);
        RadioButton rbt = (RadioButton) findViewById(R.id.radioButtonText);

        title_bar_item = sharedPref.getInt("actionbar_showed", 0);
        if (title_bar_item == 0) rbt.setChecked(true);
        else rbi.setChecked(true);

        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                if (switch_theme_state == 1) {
                    editor.putInt("theme", 0);
                    editor.commit();
                    switch_theme_state = 0;
                } else {
                    editor.putInt("theme", 1);
                    editor.commit();
                    switch_theme_state = 1;
                }
                Toast.makeText(SettingsActivity.this, "Tema cambiato", Toast.LENGTH_SHORT).show();
            }


        });


    }

    public void onRadioButtonClicked(View v) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.radioButtonIcon:
                editor.putInt("actionbar_showed", 1);
                editor.commit();
                break;
            case R.id.radioButtonText:
                editor.putInt("actionbar_showed", 0);
                editor.commit();
                break;
        }
    }
}