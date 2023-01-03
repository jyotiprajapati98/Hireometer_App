package com.example.jobhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobhub.Activities.LoginActivity;
import com.example.jobhub.Activities.OTP_VerificationActivity;
import com.example.jobhub.Models.PassToVerification;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.text.TextUtils.isEmpty;
import static com.example.jobhub.Utilities.Utility.setErrorTextListener;

public class MainActivity extends AppCompatActivity {

    // variable for FirebaseAuth class
    //private FirebaseAuth mAuth;
    private EditText edtPhone, edtName, edtEmail;
    private RadioGroup user_Group;

    private PassToVerification PTV;
    private DatabaseReference databaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private List<PassToVerification> records;
    private String recordNum;
    private int count;
    private Button Loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mAuth = FirebaseAuth.getInstance();
        // initializing variables for button and Edittext.
        edtName = findViewById(R.id.name_edit_text);
        edtEmail =findViewById(R.id.email_edit_text);
        edtPhone = findViewById(R.id.phone_edit_text);
        user_Group = findViewById(R.id.user_selection);

        Loginbutton = findViewById(R.id.Loginbutton);
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }//oncreate method closed

    public void userTypeClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if(view.getId()!=0)
        switch(view.getId()) {
            case R.id.hire_radio_btn_rc:
                if (checked){
                    break;
                }
            case R.id.get_job_radio_btn_rc:
                if (checked)
                    break;
        }

        boolean error = false;
        String nameString = edtName.getText().toString().trim();
        String emailString = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String userType = "";

            if(user_Group.getCheckedRadioButtonId()==R.id.hire_radio_btn_rc){
                userType = "hire";
            }else if(user_Group.getCheckedRadioButtonId() == R.id.get_job_radio_btn_rc){
                userType = "seeker";
            }

            //check name
            if(isEmpty(nameString)){
                TextInputLayout textInputLayout = findViewById(R.id.name_input_layout_rc);
                textInputLayout.setError("Field is empty");
                setErrorTextListener(edtName,textInputLayout);
                error = true;
            }

            //check email
            if (isEmpty(emailString)) {
                TextInputLayout textInputLayout = findViewById(R.id.email_input_layout_rc);
                textInputLayout.setError("Field is empty");
                setErrorTextListener(edtEmail,textInputLayout);
                error = true;
            }
        //check phone
        if (phoneNumber.length() < 10) {
            TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.phone_input_layout_rc);
            if (isEmpty(phoneNumber))
                textInputLayout.setError("Field empty");
            else
                textInputLayout.setError("Must be at least 10 digits");
            setErrorTextListener(edtPhone, textInputLayout);
            error = true;
        }


        if (!error) {
            System.out.println("user name"+nameString+"Email id "+emailString+"phone number"+phoneNumber+"User type"+userType);
            PTV = new PassToVerification(nameString, emailString, phoneNumber, userType);
            PTV_function(PTV);
        }


    }//function closed

    private void PTV_function(PassToVerification ptv) {
        Intent intent = new Intent(MainActivity.this, OTP_VerificationActivity.class);
        intent.putExtra("PTVObj", ptv);
        startActivity(intent);
    }

}//class closed
