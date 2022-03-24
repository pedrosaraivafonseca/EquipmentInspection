package com.example.equipmentinspection.ui.mgmt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.ui.MainActivity;

public class SettingsActivity extends Activity {
    public static final String DARK_MODE = "isDark";

    Button darkMode;
    Button lightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SharedPreferences.Editor editor = getSharedPreferences("isDark", 0).edit();


        lightMode = (Button) findViewById(R.id.settings_light_button);
        darkMode = (Button) findViewById(R.id.settings_dark_button);



        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean(DARK_MODE, true);
                editor.apply();
            }
        });

        lightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean(DARK_MODE, false).apply();
                editor.apply();;
            }
        });

    }
}