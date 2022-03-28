package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private boolean isEditable;

    private TextView equipmentName;
    private EditText equipmentPrice;
    private EditText equipmentPurchase;
    private EditText equipmentWarranty;
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

        view();

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
                goEdit();
            }
        });
    }

    private void view(){
        isEditable = false;
        equipmentName = (EditText) findViewById(R.id.equipment_name_text);
        equipmentName.setEnabled(false);
        equipmentName.requestFocus();

        equipmentPrice = (EditText) findViewById(R.id.equipment_price);
        equipmentPrice.setEnabled(false);
        equipmentPrice.requestFocus();

        equipmentPurchase = (EditText) findViewById(R.id.equipment_purchaseDate);
        equipmentPurchase.setEnabled(false);
        equipmentPurchase.requestFocus();

        equipmentWarranty = (EditText) findViewById(R.id.equipment_warranty);
        equipmentWarranty.setEnabled(false);
        equipmentWarranty.requestFocus();

        equipmentLastInspector = (EditText) findViewById(R.id.equipment_lastInspector);
        equipmentLastInspector.setEnabled(false);
        equipmentLastInspector.requestFocus();

        equipmentLastInspection = (EditText) findViewById(R.id.equipment_lastInspection);
        equipmentLastInspection.setEnabled(false);
        equipmentLastInspection.requestFocus();

        equipmentNextInspection = (EditText) findViewById(R.id.equipment_nextInspection);
        equipmentNextInspection.setEnabled(false);
        equipmentNextInspection.requestFocus();

        equipmentStatus = (EditText) findViewById(R.id.equipment_status);
        equipmentStatus.setEnabled(false);
        equipmentStatus.requestFocus();
    }

    private void goEdit(){
        if (!isEditable){
            LinearLayout linearLayout = findViewById(R.id.equipment_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            equipmentName.setFocusable(true);
            equipmentName.setEnabled(true);
            equipmentName.setFocusableInTouchMode(true);

            equipmentPrice.setFocusable(true);
            equipmentPrice.setEnabled(true);
            equipmentPrice.setFocusableInTouchMode(true);

            equipmentPurchase.setFocusable(true);
            equipmentPurchase.setEnabled(true);
            equipmentPurchase.setFocusableInTouchMode(true);

            equipmentLastInspector.setFocusable(true);
            equipmentLastInspector.setEnabled(true);
            equipmentLastInspector.setFocusableInTouchMode(true);

            equipmentLastInspection.setFocusable(true);
            equipmentLastInspection.setEnabled(true);
            equipmentLastInspection.setFocusableInTouchMode(true);

            equipmentNextInspection.setFocusable(true);
            equipmentNextInspection.setEnabled(true);
            equipmentNextInspection.setFocusableInTouchMode(true);

            equipmentStatus.setFocusable(true);
            equipmentStatus.setEnabled(true);
            equipmentStatus.setFocusableInTouchMode(true);
        } else {

            LinearLayout linearLayout = findViewById(R.id.equipment_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            equipmentName.setFocusable(false);
            equipmentName.setEnabled(false);
            equipmentPrice.setFocusable(false);
            equipmentPrice.setEnabled(false);
            equipmentPurchase.setFocusable(false);
            equipmentPurchase.setEnabled(false);
            equipmentLastInspector.setFocusable(false);
            equipmentLastInspector.setEnabled(false);
            equipmentLastInspection.setFocusable(false);
            equipmentLastInspection.setEnabled(false);
            equipmentNextInspection.setFocusable(false);
            equipmentNextInspection.setEnabled(false);
            equipmentStatus.setFocusable(false);
            equipmentStatus.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void saveChanges(String equipName, String equipWarranty, String equipStatus){
        if(equipmentName.getText().toString().isEmpty()){
            equipmentName.setError(getString(R.string.error_empty_field));
            equipmentName.requestFocus();
            return;
        }

        if (equipmentWarranty.getText().toString().isEmpty()){
            equipmentWarranty.setError(getString(R.string.error_empty_field));
            equipmentWarranty.requestFocus();
            return;
        }

        if (equipmentStatus.getText().toString().isEmpty()) {
            equipmentStatus.setError(getString(R.string.error_empty_field));
            equipmentStatus.requestFocus();
            return;
        }



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