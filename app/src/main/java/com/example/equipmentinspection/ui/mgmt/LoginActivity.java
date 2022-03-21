package com.example.equipmentinspection.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText login_email_field;
    private EditText login_password_field;
    Button login_login_button;
    Button login_register_button;

    private InspectorRepository inspectorRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inspectorRepository = ((BaseApp) getApplicationContext()).getInspectorRepository();

        login_email_field = (EditText) findViewById(R.id.login_email);
        login_password_field = (EditText) findViewById(R.id.login_password);
        login_login_button = (Button) findViewById(R.id.login_login_button);
        login_register_button = (Button) findViewById(R.id.login_register_button);

        setupListeners();
    }

    private void setupListeners() {
        login_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(EditText text) {
        CharSequence string = text.getText().toString();
        return TextUtils.isEmpty(string);
    }


    private void checkLogin() {
        boolean isValid = true;
        if (isEmpty(login_email_field)) {
            login_email_field.setError("Enter email !");
            isValid = false;
        } else {
            if (!isEmail(login_email_field)) {
                login_email_field.setError("Enter valid email !");
                isValid = false;
            }
        }
        if (isEmpty(login_password_field)) {
            login_password_field.setError("Enter password !");
            isValid = false;
        }
        if (isValid) {
            String emailValue = login_email_field.getText().toString();
            String passwordValue = login_password_field.getText().toString();
            inspectorRepository.getInspectorByLogin(emailValue,passwordValue, getApplication()).observe(LoginActivity.this, inspectorEntity -> {
                if (inspectorEntity.getPasswordInspector().equals(passwordValue)) {
                    Toast toast = Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();

                    SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
                    editor.putString(MainActivity.PREFS_USER, inspectorEntity.getEmailInspector());
                    editor.apply();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                } else {
                    Toast toast = Toast.makeText(this, "Wrong login", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            });
        }
    }
}