package com.example.equipmentinspection.ui.inspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.viewmodel.EquipmentDetailsViewModel;
import com.example.equipmentinspection.viewmodel.InspectionDetailsViewModel;

public class InspectionDetails extends AppCompatActivity {

    ImageButton inspectionBackButton;
    private Toolbar inspectionToolbar;
    private InspectionEntity inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        Intent intent = getIntent();
        Long inspectionId = intent.getLongExtra("inspectionId", 0);

        InspectionDetailsViewModel.Factory inspectionVMFactory = new InspectionDetailsViewModel.Factory(getApplication(), inspectionId);

        InspectionDetailsViewModel inspectionVM = inspectionVMFactory.create(InspectionDetailsViewModel.class);

        inspectionVM.getInspection().observe(this, inspectionEntity -> {
            inspection = inspectionEntity;
            updateContent();
        });

        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);

        inspectionToolbar = (Toolbar) findViewById(R.id.inspection_toolbar);
        inspectionToolbar.setTitle("Equipment Details");
        setSupportActionBar(inspectionToolbar);
        setTitle("Inspection Details");

        inspectionBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateContent() {
        if (inspection != null) {

        }
    }
}