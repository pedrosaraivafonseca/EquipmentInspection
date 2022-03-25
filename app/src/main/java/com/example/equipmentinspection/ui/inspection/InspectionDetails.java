package com.example.equipmentinspection.ui.inspection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.equipmentinspection.R;

public class InspectionDetails extends AppCompatActivity {

    ImageButton inspectionBackButton;
    private Toolbar inspectionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);

        inspectionToolbar = (Toolbar) findViewById(R.id.equipment_toolbar);
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
}