package com.example.equipmentinspection.ui.inspection;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MediatorLiveData;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.R;
import com.example.equipmentinspection.adapter.RecyclerAdapter;
import com.example.equipmentinspection.adapter.SpinnerAdapter;
import com.example.equipmentinspection.database.async.EquipmentCreate;
import com.example.equipmentinspection.database.async.EquipmentUpdate;
import com.example.equipmentinspection.database.async.InspectionCreate;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.viewmodel.EquipmentListViewModel;
import com.example.equipmentinspection.viewmodel.InspectorListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

public class InspectionAdd extends AppCompatActivity {

    private List<InspectorEntity> inspectors;
    private InspectorListViewModel inspectorViewModel;

    private List<EquipmentEntity> equipments;
    private EquipmentListViewModel equipmentViewModel;
    private EquipmentEntity equipmentToUpdate;
    private InspectionEntity inspectionCreated;
    private String inspectorName;

    private EditText inspectionInspectionDate;
    private Spinner inspectionEquipment;
    private Spinner inspectionInspector;
    ImageButton inspectionBackButton;
    private Toolbar inspectionToolbar;
    private Button inspectionAddButton;

    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_add);

        inspectionToolbar = (Toolbar) findViewById(R.id.inspection_toolbar);
        setSupportActionBar(inspectionToolbar);
        setTitle("Equipment");
        setTitle("Inspection");

        inspectionEquipment = (Spinner) findViewById(R.id.inspection_equipment_spinner);
        inspectionInspector = (Spinner) findViewById(R.id.inspection_inspector_spinner);
        setupSpinners();

        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);
        inspectionBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        inspectionAddButton = (Button) findViewById(R.id.inspection_add_button);
        inspectionAddButton.setOnClickListener(view -> saveChanges(
                inspectionInspectionDate.getText().toString(),
                inspectionEquipment.getSelectedItem(),
                inspectionInspector.getSelectedItem()
        ));
    }

    private void updateLabelInspectionDate(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        inspectionInspectionDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void saveChanges(String date, Object equipment1, Object inspector1){
        //Error Handling
        if(!checkDate(date)){
            inspectionInspectionDate.setError(getString(R.string.error_inspection_date));
            inspectionInspectionDate.requestFocus();
            return;
        }
        InspectorEntity inspector = (InspectorEntity) inspector1;

        EquipmentEntity equipment = (EquipmentEntity) equipment1;

        InspectionEntity newInspection = new InspectionEntity();

        newInspection.setIdInspectorInspection(inspector.getIdInspector());
        newInspection.setIdEquipmentInspection(equipment.getIdEquipment());
        newInspection.setNameEquipmentInspection(equipment.getNameEquipment());
        newInspection.setDateInspection(date);
        newInspection.setStatusInspection("To do");

        new InspectionCreate(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                inspectionCreated = newInspection;
                equipmentToUpdate = equipment;
                inspectorName = inspector.toString();
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        }).execute(newInspection);
    }

    private void setResponse(boolean response) {
        if (response) {
            updateEquipment(equipmentToUpdate);

            Toast toast = Toast.makeText(this, "Inspection successfully created", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, InspectionFragment.class);
            startActivity(intent);

        } else {
            inspectionInspectionDate.setError(getString(R.string.generic_error));
            inspectionInspectionDate.requestFocus();
        }
    }

    private void setupSpinners(){
        setupInspectorSpinner();
        setupEquipmentSpinner();
    }

    private void setupInspectorSpinner(){
        inspectors = new ArrayList<>();

        ArrayAdapter<InspectorEntity> adapterInspector = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, inspectors);
        adapterInspector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inspectorViewModel = new InspectorListViewModel(this.getApplication(), InspectorRepository.getInstance());

        inspectorViewModel.getInspector().observe(this, inspectorEntities -> {
            if(inspectorEntities != null){
                inspectors = inspectorEntities;
                adapterInspector.addAll(inspectors);
                adapterInspector.notifyDataSetChanged();
            }
        });

        inspectionInspector.setAdapter(adapterInspector);

        inspectionInspector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InspectorEntity inspector = (InspectorEntity) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupEquipmentSpinner(){
        equipments = new ArrayList<>();

        ArrayAdapter<EquipmentEntity> adapterEquipment = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, equipments);
        adapterEquipment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        equipmentViewModel = new EquipmentListViewModel(this.getApplication(), EquipmentRepository.getInstance());

        equipmentViewModel.getEquipments().observe(this, equipmentEntities -> {
            if(equipmentEntities != null){
                equipments = equipmentEntities;
                adapterEquipment.addAll(equipments);
                adapterEquipment.notifyDataSetChanged();
            }
        });

        inspectionEquipment.setAdapter(adapterEquipment);

        inspectionEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EquipmentEntity equipment = (EquipmentEntity) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean checkDate(String stringDate1)  {
        if(stringDate1.isEmpty()){
            return false;
        }
        Date date1 = null;
        Date today = new Date();
        String todayString;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        todayString = formatter.format(today);

        try {
            date1 = formatter.parse(stringDate1);
            today = formatter.parse(todayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date1.equals(today)){
            return false;
        }
        return true;
    }

    private void updateEquipment(EquipmentEntity equipment){
        equipment.setStatusEquipment("To be inspected");
        equipment.setNextInspectionDateEquipment(inspectionCreated.getDateInspection());
        equipment.setLastInspectorEquipment(inspectorName);

        new EquipmentUpdate(this, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        }).execute(equipment);
    }
}