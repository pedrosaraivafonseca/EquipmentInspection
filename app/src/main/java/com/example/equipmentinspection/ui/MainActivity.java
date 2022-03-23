package com.example.equipmentinspection.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.RecyclerAdapter;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.ui.equipment.EquipmentFragment;
import com.example.equipmentinspection.ui.inspection.InspectionFragment;
import com.example.equipmentinspection.ui.inspector.InspectorFragment;
import com.example.equipmentinspection.ui.mgmt.LoginActivity;
import com.example.equipmentinspection.ui.mgmt.SettingsActivity;
import com.example.equipmentinspection.viewmodel.EquipmentListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    private Toolbar mainToolbar;
    private BottomNavigationView mainBottomNavigation;
    private FrameLayout mainFrame;
    private FloatingActionButton mainAddButton;

    private EquipmentFragment equipmentFragment;
    private InspectorFragment inspectorFragment;
    private InspectionFragment inspectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Equipment Inspection");

        mainBottomNavigation = findViewById(R.id.main_bottom_navigation);

        mainAddButton = findViewById(R.id.main_add_buttom);

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
        Intent intent = getIntent();
        if(intent.getExtras() == null){
            goToLogin();
        }


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
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_USER, 0).edit();
        editor.remove(MainActivity.PREFS_USER);
        editor.apply();

        goToLogin();
    }
}