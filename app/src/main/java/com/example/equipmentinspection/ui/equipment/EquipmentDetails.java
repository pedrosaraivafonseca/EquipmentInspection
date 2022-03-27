package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.EquipmentEntity;

public class EquipmentDetails extends AppCompatActivity {

    ImageButton equipmentBackButton;
    private Toolbar equipmentToolbar;
    Button equipmentEdit;

    private TextView equipmentName;
    private EditText equipmentPrice;
    private EditText equipmentLastInspector;
    private EditText equipmentLastInspection;
    private EditText equipmentNextInspection;
    private EditText equipmentStatus;
    private EquipmentEntity equipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        /*
        Intent intent = getIntent();
        Long equipmentId = intent.getLongExtra("equipmentId", 0);
         */

        equipmentName = (TextView) findViewById(R.id.equipment_name_text);
        equipmentPrice = (EditText) findViewById(R.id.equipment_price);
        equipmentLastInspector = (EditText) findViewById(R.id.equipment_lastInspector);
        equipmentLastInspection = (EditText) findViewById(R.id.equipment_lastInspection);
        equipmentNextInspection = (EditText) findViewById(R.id.equipment_nextInspection);
        equipmentStatus = (EditText) findViewById(R.id.equipment_status);

        equipmentEdit= (Button) findViewById(R.id.equipment_edit_button);

        equipmentBackButton = (ImageButton) findViewById(R.id.equipment_back_button);

        equipmentToolbar = (Toolbar) findViewById(R.id.equipment_toolbar);
        equipmentToolbar.setTitle("Equipment Details");
        setSupportActionBar(equipmentToolbar);

        setupListeners();

    }

    private void setupListeners() {
        equipmentBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        equipmentEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EquipmentFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}