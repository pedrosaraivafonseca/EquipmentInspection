package com.example.equipmentinspection.ui.inspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.example.equipmentinspection.R;

public class InspectorAdd extends AppCompatActivity {

    private EditText inspectorFirstName;
    private EditText inspectorLastName;
    ImageButton inspectorBackButton;



    private Toolbar inspectorToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_add);

        inspectorFirstName = (EditText) findViewById(R.id.inspector_firstName);
        inspectorLastName = (EditText) findViewById(R.id.inspector_lastName);
        inspectorBackButton = (ImageButton) findViewById(R.id.inspector_back_button);

        inspectorBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}