package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.dao.EquipmentDao;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.viewmodel.EquipmentDetailsViewModel;
import com.example.equipmentinspection.viewmodel.EquipmentListViewModel;

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

        Intent intent = getIntent();
        Long equipmentId = intent.getLongExtra("equipmentId", 0);

        EquipmentDetailsViewModel.Factory equipmentVMFactory = new EquipmentDetailsViewModel.Factory(getApplication(), equipmentId);

        EquipmentDetailsViewModel equipmentVM = equipmentVMFactory.create(EquipmentDetailsViewModel.class);

        equipmentVM.getEquipment().observe(this, equipmentEntity -> {
            equipment = equipmentEntity;
            updateContent();
        });

        equipmentName = (EditText) findViewById(R.id.equipment_name_text);
        equipmentName.setInputType(InputType.TYPE_NULL);
        equipmentName.setEnabled(false);
        equipmentName.requestFocus();

        equipmentPrice = (EditText) findViewById(R.id.equipment_price);
        equipmentPrice.setInputType(InputType.TYPE_NULL);
        equipmentPrice.setEnabled(false);
        equipmentPrice.requestFocus();

        equipmentLastInspector = (EditText) findViewById(R.id.equipment_lastInspector);
        equipmentLastInspector.setInputType(InputType.TYPE_NULL);
        equipmentLastInspector.setEnabled(false);
        equipmentLastInspector.requestFocus();

        equipmentLastInspection = (EditText) findViewById(R.id.equipment_lastInspection);
        equipmentLastInspection.setInputType(InputType.TYPE_NULL);
        equipmentLastInspection.setEnabled(false);
        equipmentLastInspection.requestFocus();

        equipmentNextInspection = (EditText) findViewById(R.id.equipment_nextInspection);
        equipmentNextInspection.setInputType(InputType.TYPE_NULL);
        equipmentNextInspection.setEnabled(false);
        equipmentNextInspection.requestFocus();

        equipmentStatus = (EditText) findViewById(R.id.equipment_status);
        equipmentStatus.setInputType(InputType.TYPE_NULL);
        equipmentStatus.setEnabled(false);
        equipmentStatus.requestFocus();

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

        equipmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipmentName = (EditText) findViewById(R.id.equipment_name_text);
                equipmentName.requestFocus();
            }
        });

        equipmentEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                equipmentName = (EditText) findViewById(R.id.equipment_name_text);

            }
        });
    }

    private void updateContent() {
        if (equipment != null) {
            equipmentName.setText(equipment.getNameEquipment());
            equipmentPrice.setText(Double.toString(equipment.getPriceEquipment()));
            equipmentLastInspector.setText(equipment.getLastInspectorEquipment());
            equipmentLastInspection.setText(equipment.getLastInspectionDateEquipment());
            equipmentNextInspection.setText(equipment.getNextInspectionDateEquipment());
            equipmentStatus.setText(equipment.getStatusEquipment());
        }
    }
}