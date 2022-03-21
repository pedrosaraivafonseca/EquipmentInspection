package com.example.equipmentinspection.ui.inspection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.equipmentinspection.R;

public class InspectionAdd extends AppCompatActivity {

    private EditText inspectorInspectionDate;
    private EditText inspectorNextInspectionDate;
    private EditText inspectorInspector;
    private EditText status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_add);
    }
}