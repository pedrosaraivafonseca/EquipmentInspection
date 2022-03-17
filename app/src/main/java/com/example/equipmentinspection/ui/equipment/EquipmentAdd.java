package com.example.equipmentinspection.ui.equipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.equipmentinspection.R;

public class EquipmentAdd extends AppCompatActivity {

    Button equipmentAdd;

    private EditText equipmentName;
    private EditText equipmentPrice;
    private EditText equipmentLastInspector;
    private EditText equipmentLastInspection;
    private EditText equipmentNextInspection;
    private EditText equipmentStatus;

    private Toolbar equipmentToolbar;
    ImageButton equipmentBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_add);

//        Intent intent = getIntent();
//        String equipment = intent.getStringExtra("id");

        equipmentName = (EditText) findViewById(R.id.equipment_name_text);
        equipmentPrice = (EditText) findViewById(R.id.equipment_price);
        equipmentLastInspector = (EditText) findViewById(R.id.equipment_lastInspector);
        equipmentLastInspection = (EditText) findViewById(R.id.equipment_lastInspection);
        equipmentNextInspection = (EditText) findViewById(R.id.equipment_nextInspection);
        equipmentStatus = (EditText) findViewById(R.id.equipment_status);

        equipmentAdd= (Button) findViewById(R.id.equipment_edit_button);


//        listView = findViewById(R.id.equipment_listview);
//        listView.setItemsCanFocus(true);


        equipmentBackButton = (ImageButton) findViewById(R.id.equipment_back_button);
        equipmentToolbar = (Toolbar) findViewById(R.id.equipment_toolbar);
        setSupportActionBar(equipmentToolbar);
        setTitle("Equipment");
        setupListeners();

//        ListView listView = findViewById(R.id.equipment_listview);
//        List<String> list = new ArrayList<>();
//        list.add("Name");
//        list.add("Price");
//        list.add("PurchaseDate");
//        list.add("WarrantDate");
//        list.add("LastInspection");
//        list.add("LastInspector");
//        list.add("Status");
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(arrayAdapter);

    }

    private void setupListeners() {
        equipmentBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        equipmentAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

//    private boolean isDate(EditText text) {
//        CharSequence date = text.getText().toString();
//        return (!TextUtils.isEmpty(email) && Patterns..matcher(email).matches());
//    }
//
//    private boolean isEmpty(EditText text) {
//        CharSequence string = text.getText().toString();
//        return TextUtils.isEmpty(string);
//    }
//
//
//    private void checkInfo() {
//        boolean isValid = true;
//        if (isEmpty(login_email_field)) {
//            login_email_field.setError("Enter email !");
//            isValid = false;
//        } else {
//            if (!isEmail(login_email_field)) {
//                login_email_field.setError("Enter valid email !");
//                isValid = false;
//            }
//        }
//        if (isEmpty(login_password_field)) {
//            login_password_field.setError("Enter password !");
//            isValid = false;
//        }
//        if (isValid) {
//            String emailValue = login_email_field.getText().toString();
//            String passwordValue = login_password_field.getText().toString();
//            if (emailValue.equals(email) && passwordValue.equals(password)) {
//                Toast toast = Toast.makeText(this, "Login successfull", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                this.finish();
//            } else {
//                Toast toast = Toast.makeText(this, "Wrong login", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();
//            }
//        }
//    }
}
