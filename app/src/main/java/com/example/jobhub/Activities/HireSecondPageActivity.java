package com.example.jobhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobhub.Dashboards.HireDashboardActivity;
import com.example.jobhub.Dashboards.SeekerDashboardActivity;
import com.example.jobhub.Models.PassToVerification;
import com.example.jobhub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HireSecondPageActivity extends AppCompatActivity {
    private PassToVerification hirePassToVerification;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser mUser;
    private List<PassToVerification> ptvList;
    private TextView recordNumber;
    private EditText offerSalary, company, designation,website;
    private Button submit;
    private String recordStr;
    private ProgressBar progressBar;
    //private RadioGroup job_Group;
    private String name,email, mobile, userType;
    private String jobType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_second_page_activity);

        //instance to get previous form data
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Log.d("Hire second page","test3");
            hirePassToVerification = (PassToVerification) bundle.getSerializable("hirePTVObj");
        }else{
            Toast.makeText(HireSecondPageActivity.this, "Cannot retrieve data Page 2", Toast.LENGTH_LONG).show();
            finish();
        }

        //firebase database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference("JobSeekers");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hire");

        name=hirePassToVerification.getName();
        email=hirePassToVerification.getEmail();
        mobile =hirePassToVerification.getMobile();
        userType=hirePassToVerification.getUserType();
        //button and editext
        recordNumber = findViewById(R.id.record_id);
        //job_Group = findViewById(R.id.job_selection);
        //offerSalary = findViewById(R.id.stipend_txt);
        System.out.println("sal"+offerSalary);
        designation = findViewById(R.id.designation_txt);
        company = findViewById(R.id.company_txt);
        website = findViewById(R.id.company_link_txt);
        submit = findViewById(R.id.submitBtn);
        ptvList = new ArrayList<>();
        getRecordCount();


        //button to submit the records
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecordID();
            }
        });
    }
    /*
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
                case R.id.both_radio_btn_rc:
                    if(checked)
                        break;
            }
        //boolean error = false;

        jobType = "";

        if(job_Group.getCheckedRadioButtonId()==R.id.intership_radio_btn_rc){
            jobType ="Intern";
        }else if(job_Group.getCheckedRadioButtonId()==R.id.job_radio_btn_rc){
            jobType ="Employee";
        }else if(job_Group.getCheckedRadioButtonId()==R.id.both_radio_btn_rc){
            jobType="Both";
        }


        //PTV_function(PTV);
    }

     */
    private void addRecordID() {
        //String offerSal = offerSalary.getText().toString();
        String desingnation = designation.getText().toString();
        String company_name = company.getText().toString();
        String company_website = website.getText().toString();
        //System.out.println("jobType"+jobType+"salary"+offerSal+"desingnation "+desingnation+"company_name"+company_name+"company_website"+company_website);
        hirePassToVerification = new PassToVerification(recordStr, name, email, mobile, userType,desingnation, company_name,company_website);
        //String jobTypeStr = hirePassToVerification.getJobType();
        String salaryTxt =hirePassToVerification.getOfferSal();
        String desingnationTxt = hirePassToVerification.getDesingnation();
        String companyNameText = hirePassToVerification.getCompany_name();
        String companySiteText = hirePassToVerification.getCompany_website();
        //passToVerification = new PassToVerification(resumeStr);
        //String resume = passToVerification.getUri();
        addToDatabase(recordStr,name, email, mobile, userType,desingnationTxt,companyNameText,companySiteText);

    }
    //record number
    private void getRecordCount() {
        updateCount();
        //get users for counting
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PassToVerification rc = snapshot.getValue(PassToVerification.class);
                if (rc != null)
                    ptvList.add(rc);
                updateCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //update account
    private void updateCount() {
        int count = ptvList.size() + 1;
        recordStr = String.valueOf(count);
        Log.d("Count Log", "String: " + recordStr);
    }

    private void addToDatabase(String recordNum, String nameStr, String emailTxt, String mobileTxt, String userTypeTxt,String desingnation, String CompanyName, String companyWebsite ) {
        hirePassToVerification.setRecordNum(recordNum);
        hirePassToVerification.setName(nameStr);
        hirePassToVerification.setEmail(emailTxt);
        hirePassToVerification.setMobile(mobileTxt);
        hirePassToVerification.setUserType(userTypeTxt);
        //hirePassToVerification.setJobType(jobType);
        //hirePassToVerification.setOfferSal(offerSalary);
        hirePassToVerification.setDesingnation(desingnation);
        hirePassToVerification.setCompany_name(CompanyName);
        hirePassToVerification.setCompany_website(companyWebsite);
        //hirePassToVerification.setResume(resume);

        databaseReference.child(hirePassToVerification.getRecordNum()).setValue(hirePassToVerification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HireSecondPageActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
                    Intent dashIntent = new Intent(HireSecondPageActivity.this, HireDashboardActivity.class);
                    dashIntent.putExtra("hirePTVObj",hirePassToVerification);
                    startActivity(dashIntent);
                    finish();
                }else{
                    Toast.makeText(HireSecondPageActivity.this, "Failed to create record: Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(HireSecondPageActivity.this, "Failed to create record: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(passToVerification);
                Toast.makeText(SeekerDocumentUploadActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SeekerDocumentUploadActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

         */
    }
}
