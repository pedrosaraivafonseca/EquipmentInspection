package com.example.equipmentinspection.ui.inspection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.equipmentinspection.R;

public class InspectionDetails extends AppCompatActivity {

    ImageButton inspectionBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);

        setTitle("Inspection Details");

        inspectionBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}