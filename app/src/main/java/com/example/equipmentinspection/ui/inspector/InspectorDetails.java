package com.example.equipmentinspection.ui.inspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.equipmentinspection.R;

public class InspectorDetails extends AppCompatActivity {

    ImageButton inspectorBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_details);

        inspectorBackButton = (ImageButton) findViewById(R.id.inspector_back_button);


        setTitle("Inspector Details");

        inspectorBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}