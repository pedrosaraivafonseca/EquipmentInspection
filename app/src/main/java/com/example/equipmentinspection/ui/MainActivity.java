package com.example.equipmentinspection.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.ui.equipment.EquipmentFragment;
import com.example.equipmentinspection.ui.inspection.InspectionFragment;
import com.example.equipmentinspection.ui.inspector.InspectorFragment;
import com.example.equipmentinspection.ui.mgmt.LoginActivity;
import com.example.equipmentinspection.ui.mgmt.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final boolean PREFS_SESSION = false;

    private Toolbar mainToolbar;
    private BottomNavigationView mainBottomNavigation;
    private FrameLayout mainFrame;

    private EquipmentFragment equipmentFragment;
    private InspectorFragment inspectorFragment;
    private InspectionFragment inspectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.DARK_MODE, 0);
        Boolean isDark = sharedPreferences.getBoolean("isDark", false);
        if (isDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);


        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Equipment Inspection");

        mainBottomNavigation = findViewById(R.id.main_bottom_navigation);

        equipmentFragment = new EquipmentFragment();
        inspectorFragment = new InspectorFragment();
        inspectionFragment = new InspectionFragment();

        setFragment(equipmentFragment);

        mainBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_equipment_toolbar_button:
                        setFragment(equipmentFragment);
                        return true;

                    case R.id.menu_inspector_toolbar_button:
                        setFragment(inspectorFragment);
                        return true;

                    case R.id.menu_inspection_toolbar_button:
                        setFragment(inspectionFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_USER, MODE_PRIVATE);
        if(sharedPreferences.getString("logged", "").equals("true")){

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
//        Intent intent = getIntent();
//        if(intent.getExtras() == null){
//            goToLogin();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logout_button:
                logout();
                return true;

            case R.id.action_settings_button:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            default:
                return false;
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(LoginActivity.PREFS_USER, 0).edit();
        editor.remove("logged");
        editor.apply();
//        SharedPreferences.Editor editor2 = getSharedPreferences(SettingsActivity.DARK_MODE, 0).edit();
//        editor2.remove(SettingsActivity.DARK_MODE);
//        editor2.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}