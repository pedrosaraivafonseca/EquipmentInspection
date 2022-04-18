package com.example.equipmentinspection.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.R;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.ui.MainActivity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_email;
    private EditText register_password;
    private EditText register_lastname;
    private EditText register_firstname;
    private EditText register_password_retype;

    private Button register_register_button;
    private Button register_login_button;

    private InspectorRepository repository;

    //Build UI
    //Buttons listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        repository = ((BaseApp) getApplication()).getInspectorRepository();

        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_password_retype = findViewById(R.id.register_password_retype);
        register_lastname = findViewById(R.id.register_lastname);
        register_firstname = findViewById(R.id.register_firstname);
        register_register_button = findViewById(R.id.register_register_button);
        register_login_button = findViewById(R.id.register_login_button);

        register_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });

        register_register_button.setOnClickListener(view -> saveChanges(
                register_email.getText().toString(),
                register_firstname.getText().toString(),
                register_lastname.getText().toString(),
                register_password.getText().toString(),
                register_password_retype.getText().toString()
        ));
    }

    //Create new inspector with data given
    private void saveChanges(String email, String firstname, String lastname, String password1, String password2){
        if(!password1.equals(password2)){
            register_password.setError(getString(R.string.error_password_retype));
            register_password.requestFocus();
            register_password.setText("");
            register_password_retype.setText("");
            return;
        }

        if (password1.length() < 6){
            register_password.setError(getString(R.string.error_invalid_password));
            register_password.requestFocus();
            register_password.setText("");
            register_password_retype.setText("");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            register_email.setError(getString(R.string.error_invalid_email));
            register_email.requestFocus();
            return;
        }

        if(register_lastname.getText().toString().isEmpty()){
            register_lastname.setError(getString(R.string.error_empty_field));
            register_lastname.requestFocus();
            return;
        }

        InspectorEntity inspector = new InspectorEntity(lastname, firstname, email, password1);

        repository.register(inspector, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                SharedPreferences.Editor editor = getSharedPreferences(LoginActivity.PREFS_USER, 0).edit();
                editor.putString(LoginActivity.PREFS_USER, inspector.getEmail());
                editor.apply();

                setResponse(true);

            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e);
                setResponse(false);
            }
        });
    }

    //Check if account can be created
    private void setResponse(Boolean response) {
        System.out.println(response);
        if (response) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Account successfully created", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            register_email.setError(getString(R.string.error_already_registered));
            register_email.requestFocus();
        }
    }
}