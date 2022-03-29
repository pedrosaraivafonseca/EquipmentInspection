package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.async.EquipmentUpdate;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.viewmodel.EquipmentDetailsViewModel;

public class EquipmentDetails extends AppCompatActivity {

    ImageButton equipmentBackButton;
    private Toolbar equipmentToolbar;
    Button equipmentEdit;
    Button equipmentDelete;
    private boolean isEditable;

    private TextView equipmentName;
    private EditText equipmentPrice;
    private EditText equipmentPurchase;
    private EditText equipmentWarranty;
    private EditText equipmentLastInspector;
    private EditText equipmentLastInspection;
    private EditText equipmentNextInspection;
    private EditText equipmentStatus;

    private EquipmentEntity equipment;


    //Build the UI, get the equipmentId from EquipmentFragment intent,
    //Setup observers for data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        Intent intent = getIntent();
        Long equipmentId = intent.getLongExtra("equipmentId", 0);

        EquipmentDetailsViewModel.Factory equipmentVMFactory = new EquipmentDetailsViewModel.Factory(getApplication(), equipmentId);

        EquipmentDetailsViewModel equipmentVM = equipmentVMFactory.create(EquipmentDetailsViewModel.class);

        equipmentVM.getEquipment().observe(this, equipmentEntity -> {
            equipment = equipmentEntity;
            updateContent();
        });

        view();

        equipmentEdit = (Button) findViewById(R.id.equipment_edit_button);
        equipmentDelete = (Button) findViewById(R.id.equipment_delete_button);
        equipmentBackButton = (ImageButton) findViewById(R.id.equipment_back_button);

        equipmentToolbar = (Toolbar) findViewById(R.id.equipment_toolbar);
        equipmentToolbar.setTitle("Equipment Details");
        setSupportActionBar(equipmentToolbar);

        setupListeners();
    }

    //Setup the click listeners
    //Back is to go back
    //Edit is to edit the fields
    //Delete is to delete the equipment
    private void setupListeners() {
        equipmentBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        equipmentEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goEdit();
            }
        });

        equipmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog diaBox =  createDeleteDialog();
               diaBox.show();
            }
        });
    }

    //View created to display the information without being editable
    private void view(){
        isEditable = false;
        equipmentName = (EditText) findViewById(R.id.equipment_name_text);
        equipmentName.setEnabled(false);
        equipmentName.requestFocus();

        equipmentPrice = (EditText) findViewById(R.id.equipment_price);
        equipmentPrice.setEnabled(false);
        equipmentPrice.requestFocus();

        equipmentPurchase = (EditText) findViewById(R.id.equipment_purchaseDate);
        equipmentPurchase.setEnabled(false);
        equipmentPurchase.requestFocus();

        equipmentWarranty = (EditText) findViewById(R.id.equipment_warranty);
        equipmentWarranty.setEnabled(false);
        equipmentWarranty.requestFocus();

        equipmentLastInspector = (EditText) findViewById(R.id.equipment_lastInspector);
        equipmentLastInspector.setEnabled(false);
        equipmentLastInspector.requestFocus();

        equipmentLastInspection = (EditText) findViewById(R.id.equipment_lastInspection);
        equipmentLastInspection.setEnabled(false);
        equipmentLastInspection.requestFocus();

        equipmentNextInspection = (EditText) findViewById(R.id.equipment_nextInspection);
        equipmentNextInspection.setEnabled(false);
        equipmentNextInspection.requestFocus();

        equipmentStatus = (EditText) findViewById(R.id.equipment_status);
        equipmentStatus.setEnabled(false);
        equipmentStatus.requestFocus();
    }

    //Called by EditButton, makes the fields editable or not
    private void goEdit(){
        if (!isEditable){
            LinearLayout linearLayout = findViewById(R.id.equipment_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            equipmentStatus.setFocusable(true);
            equipmentStatus.setEnabled(true);
            equipmentStatus.setFocusableInTouchMode(true);

        } else {
            LinearLayout linearLayout = findViewById(R.id.equipment_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            equipmentStatus.setFocusable(false);
            equipmentStatus.setEnabled(false);
            saveChanges(equipmentStatus.getText().toString());
        }
        isEditable = !isEditable;
    }

    //Save the changes made to the EquipmentEntity in the DB via update
    private void saveChanges(String equipStatus){
        if (equipmentStatus.getText().toString().isEmpty()) {
            equipmentStatus.setError(getString(R.string.error_empty_field));
            equipmentStatus.requestFocus();
            return;
        }

        equipment.setStatusEquipment(equipStatus);

        new EquipmentUpdate(this, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(Exception e) {
            }
        }).execute(equipment);
    }

    //Use to get the equipment from LiveData
    private void updateContent() {
        if (equipment != null) {
            equipmentName.setText(equipment.getNameEquipment());
            equipmentPrice.setText(Double.toString(equipment.getPriceEquipment()));
            equipmentPurchase.setText(equipment.getPurchaseDateEquipment());
            equipmentWarranty.setText(equipment.getWarrantyDateEquipment());
            equipmentLastInspector.setText(equipment.getLastInspectorEquipment());
            equipmentLastInspection.setText(equipment.getLastInspectionDateEquipment());
            equipmentNextInspection.setText(equipment.getNextInspectionDateEquipment());
            equipmentStatus.setText(equipment.getStatusEquipment());
        }
    }

    //Builds a dialog to confirm or not the deletion of the equipment
    private AlertDialog createDeleteDialog() {
        AlertDialog dialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete " + equipment.getNameEquipment())

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        EquipmentDetailsViewModel.Factory equipmentVMFactory = new EquipmentDetailsViewModel.Factory(getApplication(), equipment.getIdEquipment());
                        EquipmentDetailsViewModel equipmentVM = equipmentVMFactory.create(EquipmentDetailsViewModel.class);
                            equipmentVM.deleteEquipment(equipment, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Toast toast = Toast.makeText(getApplication(), "Equipment successfully deleted", Toast.LENGTH_LONG);
                                    toast.show();

                                    Intent intent = new Intent(getApplication(), EquipmentFragment.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Toast toast = Toast.makeText(getApplication(), "There was an error", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                }).create();
        return dialogBox;
    }
}