package com.example.equipmentinspection.ui.inspection;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MediatorLiveData;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.SpinnerAdapter;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.viewmodel.InspectorListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class InspectionAdd extends AppCompatActivity {

    private EditText inspectionInspectionDate;
    private Spinner inspectionEquipment;
    private Spinner inspectionInspector;

    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_add);

        inspectionEquipment = (Spinner) findViewById(R.id.inspection_equipment_spinner);
        inspectionInspector = (Spinner) findViewById(R.id.inspection_inspector_spinner);

        inspectionInspectionDate = (EditText) findViewById(R.id.inspection_date_text);
        DatePickerDialog.OnDateSetListener inspectionDate = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelInspectionDate();
        };

        inspectionInspectionDate.setOnClickListener(view -> new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                inspectionDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateLabelInspectionDate(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        inspectionInspectionDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}