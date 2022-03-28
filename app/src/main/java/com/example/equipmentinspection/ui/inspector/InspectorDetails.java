package com.example.equipmentinspection.ui.inspector;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.async.InspectorUpdate;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.viewmodel.InspectorDetailsViewModel;

public class InspectorDetails extends AppCompatActivity {

    private Toolbar inspectorToolbar;
    ImageButton inspectorBackButton;
    private InspectorEntity inspector;
    private EditText inspectorFirstName;
    private EditText inspectorLastName;
    private EditText inspectorMail;
    private Button inspectorEdit;
    private Button inspectorDelete;
    private Boolean isEditable;

    private static String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_details);

        inspectorBackButton = (ImageButton) findViewById(R.id.inspector_back_button);

        setTitle("Inspector Details");

        Intent intent = getIntent();
        Long inspectorId = intent.getLongExtra("inspectorId", 0);

        InspectorDetailsViewModel.Factory inspectorVMFactory = new InspectorDetailsViewModel.Factory(getApplication(), inspectorId);
        InspectorDetailsViewModel inspectorVM = inspectorVMFactory.create(InspectorDetailsViewModel.class);

        inspectorVM.getInspector().observe(this, inspectorEntity -> {
                    inspector = inspectorEntity;
                    updateContent();
        });

        inspectorEdit = (Button) findViewById(R.id.inspector_edit_button);
        inspectorDelete = (Button) findViewById(R.id.inspector_delete_button);

        view();

        inspectorToolbar = (Toolbar) findViewById(R.id.inspector_toolbar);
        inspectorToolbar.setTitle("Inspector");
        setSupportActionBar(inspectorToolbar);

        setupListeners();

    }

    private void setupListeners() {
        inspectorBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        inspectorEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEdit();
            }
        });

        inspectorDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = createDeleteDialog();
                alertDialog.show();
            }
        });
    }

    private AlertDialog createDeleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete " + inspector.getNameInspector())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        InspectorDetailsViewModel.Factory inspectorVMFactory = new InspectorDetailsViewModel.Factory(getApplication(), inspector.getIdInspector());
                        InspectorDetailsViewModel inspectorVM = inspectorVMFactory.create(InspectorDetailsViewModel.class);
                        inspectorVM.deleteInspector(inspector, new OnAsyncEventListener() {
                            @Override
                            public void onSuccess() {
                                Toast toast = Toast.makeText(getApplication(), "Account succesfully deleted", Toast.LENGTH_SHORT);
                                toast.show();

                                SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_USER, 0).edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast toast = Toast.makeText(getApplication(), "There was an error", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        return alertDialog;
    }

    private void goEdit() {
        if (!isEditable){
            LinearLayout linearLayout = findViewById(R.id.inspector_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            inspectorFirstName.setFocusable(true);
            inspectorFirstName.setEnabled(true);
            inspectorFirstName.setFocusableInTouchMode(true);

            inspectorLastName.setFocusable(true);
            inspectorLastName.setEnabled(true);
            inspectorLastName.setFocusableInTouchMode(true);

            inspectorMail.setFocusable(true);
            inspectorMail.setEnabled(true);
            inspectorMail.setFocusableInTouchMode(true);
        } else {
            LinearLayout linearLayout = findViewById(R.id.inspector_details_layout);
            linearLayout.setVisibility(View.VISIBLE);
            inspectorFirstName.setFocusable(false);
            inspectorFirstName.setEnabled(false);
            inspectorLastName.setFocusable(false);
            inspectorLastName.setEnabled(false);
            inspectorMail.setFocusable(false);
            inspectorMail.setEnabled(false);
            saveChanges(inspectorFirstName.getText().toString(), inspectorLastName.getText().toString(), inspectorMail.getText().toString());

        }
        isEditable = !isEditable;
    }

    private void saveChanges(String inspecFirstName, String inspecLastName, String inspectMail) {
        if (inspecFirstName.isEmpty()) {
            inspectorFirstName.setError(getString(R.string.error_empty_field));
            inspectorFirstName.requestFocus();
            return;
        }
        if (inspecLastName.isEmpty()) {
            inspectorLastName.setError(getString(R.string.error_empty_field));
            inspectorLastName.requestFocus();
            return;
        }
        if (inspectMail.isEmpty()) {
            inspectorMail.setError(getString(R.string.error_empty_field));
            inspectorMail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inspectMail).matches()) {
            inspectorMail.setError(getString(R.string.error_invalid_email));
            inspectorMail.requestFocus();
            return;
        }

        inspector.setFirstNameInspector(inspecFirstName);
        inspector.setNameInspector(inspecLastName);
        inspector.setEmailInspector(inspectMail);

        new InspectorUpdate(this, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                if (!mail.equals(inspectMail)){
                    System.out.println(mail);
                    System.out.println(inspectMail);
                    SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_USER, 0).edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        }).execute(inspector);
    }

    private void checkEdit() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_USER, 0);
        String userMail = sharedPreferences.getString("email", "");
        if (userMail.equals(mail)){
            inspectorEdit.setVisibility(View.VISIBLE);
            inspectorDelete.setVisibility(View.VISIBLE);
        }else {
            inspectorEdit.setVisibility(View.GONE);
            inspectorDelete.setVisibility(View.GONE);
        }
    }

    private void view() {
        isEditable = false;
        inspectorFirstName = (EditText) findViewById(R.id.inspector_firstName);
        inspectorFirstName.setEnabled(false);
        inspectorFirstName.requestFocus();

        inspectorLastName = (EditText) findViewById(R.id.inspector_lastName);
        inspectorLastName.setEnabled(false);
        inspectorLastName.requestFocus();

        inspectorMail = (EditText) findViewById(R.id.inspector_email);
        inspectorMail.setEnabled(false);
        inspectorMail.requestFocus();
    }


    private void updateContent() {
        if (inspector!=null){
            inspectorFirstName.setText(inspector.getFirstNameInspector());
            inspectorLastName.setText(inspector.getNameInspector());
            inspectorMail.setText(inspector.getEmailInspector());
            mail = inspector.getEmailInspector();
            checkEdit();
        }
    }
}