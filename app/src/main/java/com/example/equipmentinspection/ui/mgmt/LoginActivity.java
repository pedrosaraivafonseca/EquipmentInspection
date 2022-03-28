package com.example.equipmentinspection.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.equipmentinspection.R;
import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.database.repository.InspectorRepository;
import com.example.equipmentinspection.ui.MainActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_USER = "user";
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
                attemptLogin(login_email_field.getText().toString(), login_password_field.getText().toString());
            }
        });
    }

    private void attemptLogin(String email, String password) {
      InspectorRepository  repo = InspectorRepository.getInstance();

      repo.getInspectorByLogin(email, password, getApplication()).observe(LoginActivity.this, inspectorEntity -> {
          if (inspectorEntity != null) {
              if (inspectorEntity.getPasswordInspector().equals(password)) {
                  SharedPreferences.Editor editor = getSharedPreferences(PREFS_USER, 0).edit();
                  editor.putString("email", email);
                  editor.putString("logged", "true");
                  editor.apply();
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);

              } else {
                  login_password_field.setError(getString(R.string.error_incorrect_password));
                  login_password_field.requestFocus();
                  login_password_field.setText("");
              }
          }else {
            login_email_field.setError(getString(R.string.error_invalid_login));
            login_email_field.requestFocus();
            login_email_field.setText("");
            login_password_field.setText("");
        }
      });
    }
}
