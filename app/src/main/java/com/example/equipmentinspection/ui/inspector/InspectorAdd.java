package com.example.equipmentinspection.ui.inspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.example.equipmentinspection.R;

public class InspectorAdd extends AppCompatActivity {

    Button inspectorAdd;

    private EditText inspectorFirstName;
    private EditText inspectorLastName;


    private Toolbar inspectorToolbar;
    ImageButton inspectorBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_add);

        inspectorFirstName = (EditText) findViewById(R.id.inspector_firstName);
        inspectorLastName = (EditText) findViewById(R.id.inspector_lastName);


    }
}