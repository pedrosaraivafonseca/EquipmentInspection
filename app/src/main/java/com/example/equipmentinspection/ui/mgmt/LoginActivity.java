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
    private Button login_login_button;
    private Button login_register_button;
    private InspectorRepository repo;
    // Build the UI
    // Setup buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repo = InspectorRepository.getInstance();

        login_email_field = (EditText) findViewById(R.id.login_email);
        login_password_field = (EditText) findViewById(R.id.login_password);
        login_login_button = (Button) findViewById(R.id.login_login_button);
        login_register_button = (Button) findViewById(R.id.login_register_button);

        setupListeners();
    }

    //Buttons listener
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
                checkCredentials(login_email_field.getText().toString(), login_password_field.getText().toString());
            }
        });
    }

    private void checkCredentials(String email, String password) {
        if (!TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || !validEmail(email)){
            login_password_field.setError(getString(R.string.error_invalid_login));
        }
        attemptLogin(email, password);

    }

    private boolean validEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Check credentials
    private void attemptLogin(String email, String password) {



      repo.signIn(email, password, task -> {
          if (task.isSuccessful()) {
                  SharedPreferences.Editor editor = getSharedPreferences(PREFS_USER, 0).edit();
                  editor.putString("email", email);
                  editor.putString("logged", "true");
                  editor.apply();
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);

              }else {
              login_email_field.setError(getString(R.string.error_invalid_login));
              login_email_field.requestFocus();
              login_email_field.setText("");
              login_password_field.setText("");
          }
      });
    }
}
