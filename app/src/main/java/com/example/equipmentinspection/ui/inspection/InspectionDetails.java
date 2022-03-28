package com.example.equipmentinspection.ui.inspection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.viewmodel.InspectionDetailsViewModel;

public class InspectionDetails extends AppCompatActivity {

    ImageButton inspectionBackButton;
    private Toolbar inspectionToolbar;
    private InspectionEntity inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        Intent intent = getIntent();
        Long inspectionId = intent.getLongExtra("inspectionId", 0);

        InspectionDetailsViewModel.Factory inspectionVMFactory = new InspectionDetailsViewModel.Factory(getApplication(), inspectionId);

        InspectionDetailsViewModel inspectionVM = inspectionVMFactory.create(InspectionDetailsViewModel.class);

        inspectionVM.getInspection().observe(this, inspectionEntity -> {
            inspection = inspectionEntity;
            updateContent();
        });

        inspectionBackButton = (ImageButton) findViewById(R.id.inspection_back_button);

        inspectionToolbar = (Toolbar) findViewById(R.id.inspection_toolbar);
        inspectionToolbar.setTitle("Equipment Details");
        setSupportActionBar(inspectionToolbar);
        setTitle("Inspection Details");

        inspectionBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void updateContent() {
        if (inspection != null) {

        }
    }

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

                                Intent intent = new Intent(getApplication(), InspectionFragment.class);
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