package com.example.equipmentinspection.ui.inspection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.async.EquipmentUpdate;
import com.example.equipmentinspection.database.async.InspectionUpdate;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.ui.equipment.EquipmentFragment;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.viewmodel.EquipmentDetailsViewModel;
import com.example.equipmentinspection.viewmodel.InspectionDetailsViewModel;
import com.example.equipmentinspection.viewmodel.InspectorDetailsViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InspectionDetails extends AppCompatActivity {

    ImageButton inspectionBackButton;
    Button inspectionEdit;
    Button inspectionDelete;
    Button inspectionValidate;
    EditText inspectionDate;
    EditText inspectionInspector;
    EditText inspectionEquipment;
    EditText inspectionStatus;
    private Toolbar inspectionToolbar;
    private InspectionEntity inspection;
    private boolean isEditable;
    InspectorDetailsViewModel inspectorVM;
    InspectorEntity inspector;
    EquipmentEntity equipment;
    EquipmentDetailsViewModel equipmentVM;
    InspectionDetailsViewModel inspectionVM;
    final Calendar myCalendar= Calendar.getInstance();
    Long inspectionId;
    Long inspectorId;
    Long equipmentId;

    //Build the UI, get the intents from fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        Intent intent = getIntent();
        inspectionId = intent.getLongExtra("inspectionId", 0);
        inspectorId = intent.getLongExtra("inspectorId", 0);
        equipmentId = intent.getLongExtra("equipmentId", 0);


        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);
        inspectionDelete = (Button) findViewById(R.id.inspection_delete_button);
        inspectionEdit = (Button) findViewById(R.id.inspection_edit_button);
        inspectionValidate = (Button) findViewById(R.id.inspection_validate_button);

        inspectionToolbar = (Toolbar) findViewById(R.id.inspection_toolbar);
        inspectionToolbar.setTitle("Equipment Details");
        setSupportActionBar(inspectionToolbar);
        setTitle("Inspection Details");

        InspectorDetailsViewModel.Factory factory = new InspectorDetailsViewModel.Factory(getApplication(), inspectorId);
        inspectorVM = factory.create(InspectorDetailsViewModel.class);

        inspectorVM.getInspector().observe(this, inspectorEntity -> {
            inspector = inspectorEntity;
            updateInspectorName();
        });

        InspectionDetailsViewModel.Factory inspectionVMFactory = new InspectionDetailsViewModel.Factory(getApplication(), inspectionId);
        inspectionVM = inspectionVMFactory.create(InspectionDetailsViewModel.class);

        inspectionVM.getInspection().observe(this, inspectionEntity -> {
            inspection = inspectionEntity;
            updateContent();
        });

        EquipmentDetailsViewModel.Factory equipmentVMFactory = new EquipmentDetailsViewModel.Factory(getApplication(), equipmentId);
        equipmentVM = equipmentVMFactory.create(EquipmentDetailsViewModel.class);

        equipmentVM.getEquipment().observe(this, equipmentEntity -> {
            equipment = equipmentEntity;
        });

        view();

        setupListeners();
    }

    //Setup onclick listeners for buttons
    private void setupListeners() {

        inspectionBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        inspectionEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goEdit();
            }
        });

        inspectionValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox =  createValidateDialog();
                diaBox.show();
            }
        });

        inspectionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox =  createDeleteDialog();
                diaBox.show();
            }
        });
    }

    //Update content of Inspection and Inspector entities LiveData
    private void updateContent() {
        InspectionEntity ins = inspection;
            if(ins != null) {
                inspectionDate.setText(ins.getDateInspection());
                inspectionEquipment.setText(ins.getNameEquipmentInspection());
                inspectionStatus.setText(ins.getStatusInspection());
                checkInspection(ins);
            }
    }
    private void updateInspectorName(){
        InspectorEntity inspec = inspector;
            if(inspec != null) {
                InspectionEntity ins = inspection;
                inspectionInspector.setText(inspec.toString());
                checkInspector(inspec);
            }
    }

    //Builds the view to make every Edittext uneditable
    private void view(){
        isEditable = false;

        inspectionDate = (EditText) findViewById(R.id.inspection_date_text);
        inspectionDate.setEnabled(false);
        inspectionDate.requestFocus();

        inspectionInspector = (EditText) findViewById(R.id.inspection_inspector);
        inspectionInspector.setEnabled(false);
        inspectionInspector.requestFocus();

        inspectionEquipment = (EditText) findViewById(R.id.inspection_equipmentName);
        inspectionEquipment.setEnabled(false);
        inspectionEquipment.requestFocus();

        inspectionStatus = (EditText) findViewById(R.id.inspection_status);
        inspectionStatus.setEnabled(false);
        inspectionStatus.requestFocus();
    }

    //On edit button click, makes fields editable
    private void goEdit(){
        if (!isEditable){
            LinearLayout linearLayout = findViewById(R.id.inspection_details_layout);
            linearLayout.setVisibility(View.VISIBLE);

            inspectionDate.setEnabled(true);
            inspectionDate.setFocusableInTouchMode(true);

            DatePickerDialog.OnDateSetListener inspectionDate1 = (view, year, month, day) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabelDate();
            };
            inspectionDate.setOnClickListener(view -> new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,inspectionDate1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());
            inspectionDate.setFocusable(false);

        } else {
            LinearLayout linearLayout = findViewById(R.id.inspection_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            inspectionDate.setFocusable(false);
            inspectionDate.setEnabled(false);
            saveChanges(inspectionDate.getText().toString());
        }
        isEditable = !isEditable;
    }

    //Save changes made to the inspection
    private void saveChanges(String dateInsp){
        if (inspectionDate.getText().toString().isEmpty()) {
            inspectionDate.setError(getString(R.string.error_empty_field));
            inspectionDate.requestFocus();
            return;
        }

        inspection.setDateInspection(dateInsp);

        new InspectionUpdate(this, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        }).execute(inspection);
    }

    //Create a delete confirmation dialog to delete the inspection
    private AlertDialog createDeleteDialog() {
        AlertDialog dialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete " + inspection.toString())

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        InspectionDetailsViewModel.Factory inspectionVMFactory = new InspectionDetailsViewModel.Factory(getApplication(), inspection.getIdInspection());
                        InspectionDetailsViewModel inspectionVM = inspectionVMFactory.create(InspectionDetailsViewModel.class);
                        inspectionVM.deleteInspection(inspection, new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {
                                Toast toast = Toast.makeText(getApplication(), "Inspection successfully deleted", Toast.LENGTH_LONG);
                                toast.show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    //Create a validation confirmation dialog to validate the inspection
    private AlertDialog createValidateDialog() {
        AlertDialog dialogBox = new AlertDialog.Builder(this)
                .setTitle("Validate")
                .setMessage("Do you want to validate " + inspection.toString())

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        InspectionDetailsViewModel.Factory inspectionVMFactory = new InspectionDetailsViewModel.Factory(getApplication(), inspection.getIdInspection());
                        InspectionDetailsViewModel inspectionVM = inspectionVMFactory.create(InspectionDetailsViewModel.class);
                        inspection.setStatusInspection("Done");

                        equipment.setLastInspectionDateEquipment(inspection.getDateInspection());
                        equipment.setStatusEquipment("Inspected");
                        equipment.setLastInspectorEquipment(inspector.toString());

                        equipmentVM.updateEquipment(equipment, new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });

                        inspectionVM.updateInspection(inspection, new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {
                                Toast toast = Toast.makeText(getApplication(), "Inspection successfully validated", Toast.LENGTH_LONG);
                                toast.show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                }).create();
        return dialogBox;
    }

    //Update date in datepicker for edition
    private void updateLabelDate(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        inspectionDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    //Check if the user mail = to the one logged in
    //If so, inspection can be deleted, edited and validated
    //If not, the buttons are hidden
    //If inspection status is "Done", buttons are hidden
    private void checkInspector(InspectorEntity inspectorCheck) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_USER, 0);
        String userMail = sharedPreferences.getString("email", "");

        if (userMail.equals(inspectorCheck.getEmailInspector())){
            inspectionEdit.setVisibility(View.VISIBLE);
            inspectionValidate.setVisibility(View.VISIBLE);
            inspectionDelete.setVisibility(View.VISIBLE);
        }else {
            inspectionEdit.setVisibility(View.GONE);
            inspectionValidate.setVisibility(View.GONE);
            inspectionDelete.setVisibility(View.GONE);
        }
    }

    private void checkInspection(InspectionEntity inspectionCheck){
        if(inspectionCheck.getStatusInspection().equals("Done")){
            inspectionEdit.setVisibility(View.GONE);
            inspectionValidate.setVisibility(View.GONE);
            inspectionDelete.setVisibility(View.GONE);
        }
    }
}