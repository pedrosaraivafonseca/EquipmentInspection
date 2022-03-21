package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.async.EquipmentCreate;
import com.example.equipmentinspection.database.async.InspectorCreate;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.ui.mgmt.RegisterActivity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EquipmentAdd extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();

    Button equipmentAdd;

    private EditText equipmentName;
    private EditText equipmentPrice;
    private EditText equipmentWarrantyDate;
    private EditText equipmentPurchaseDate;

    private Toolbar equipmentToolbar;
    ImageButton equipmentBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_add);

        equipmentName = (EditText) findViewById(R.id.equipment_name_text);
        equipmentPrice = (EditText) findViewById(R.id.equipment_price);

        equipmentPurchaseDate = (EditText) findViewById(R.id.equipment_purchaseDate);
        DatePickerDialog.OnDateSetListener purchaseDate = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelPurchase();
        };
        equipmentPurchaseDate.setOnClickListener(view -> new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,purchaseDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        equipmentWarrantyDate = (EditText) findViewById(R.id.equipment_warrantyDate);
        DatePickerDialog.OnDateSetListener warrantyDate = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabelWarranty();
        };
        equipmentWarrantyDate.setOnClickListener(view -> new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,warrantyDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        equipmentAdd= (Button) findViewById(R.id.equipment_add_button);
        equipmentAdd.setOnClickListener(view -> saveChanges(
                equipmentName.getText().toString(),
                equipmentPrice.getText().toString(),
                equipmentPurchaseDate.getText().toString(),
                equipmentWarrantyDate.getText().toString()
        ));

        equipmentBackButton = (ImageButton) findViewById(R.id.equipment_back_button);
        equipmentToolbar = (Toolbar) findViewById(R.id.equipment_toolbar);
        setSupportActionBar(equipmentToolbar);
        setTitle("Equipment");
        setupListeners();
    }

    private void setupListeners() {
        equipmentBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveChanges(String equipmentNameString, String equipmentPriceString, String equipmentPurchaseDateString, String equipmentWarrantyDateString){

        //Error Handling
        if(equipmentPrice.getText().toString().isEmpty()){
            equipmentPrice.setError(getString(R.string.error_empty_field));
            equipmentPrice.requestFocus();
            return;
        }

        if(equipmentName.getText().toString().isEmpty()){
            equipmentName.setError(getString(R.string.error_empty_field));
            equipmentName.requestFocus();
            return;
        }

        if(!checkDate(equipmentPurchaseDateString,equipmentWarrantyDateString)){
            equipmentWarrantyDate.setError(getString(R.string.error_dates_field));
            equipmentWarrantyDate.requestFocus();
            return;
        }

        EquipmentEntity newEquipment = new EquipmentEntity();

        double price = Double.parseDouble(equipmentPriceString);
        newEquipment.setNameEquipment(equipmentNameString);
        newEquipment.setPriceEquipment(price);
        newEquipment.setPurchaseDateEquipment(equipmentPurchaseDateString);
        newEquipment.setWarrantyDateEquipment(equipmentWarrantyDateString);
        newEquipment.setNewEquipmentFields();

        new EquipmentCreate(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        }).execute(newEquipment);
    }

    private void setResponse(boolean response) {
        if (response) {
            Toast toast = Toast.makeText(this, "Equipment successfully created", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            //register_email.setError(getString(R.string.error_already_registered));
            //register_email.requestFocus();
        }
    }

    private void updateLabelWarranty(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        equipmentWarrantyDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateLabelPurchase(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        equipmentPurchaseDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private boolean checkDate(String stringDate1, String stringDate2)  {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate1);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date1.after(date2) || date1.equals(date2)){
            return false;
        }
        return true;
    }
}