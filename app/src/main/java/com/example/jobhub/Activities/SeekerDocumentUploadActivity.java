package com.example.jobhub.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jobhub.Dashboards.SeekerDashboardActivity;
import com.example.jobhub.MainActivity;
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
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SeekerDocumentUploadActivity extends AppCompatActivity {

    private RadioGroup jobType;
    private Button selectResume, submit;
    private TextView resumePath, recordNumber;
    private ActivityResultLauncher<Intent> resultLauncher;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private List<PassToVerification> ptvList;
    private String recordStr;
    private String resumeStr;

    public PassToVerification passToVerification;
    private Uri resumeUri =null;
    private PassToVerification PTV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_upload_activity);
        //instance to get previous form data
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Log.d("In Doc Upload","test3");
            passToVerification = (PassToVerification) bundle.getSerializable("seekerPTVObj");
        }else{
            Toast.makeText(SeekerDocumentUploadActivity.this, "Cannot retrieve data Page 2", Toast.LENGTH_LONG).show();
            finish();
        }

        //firebase database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference("JobSeekers");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JobSeekers");


        //get data from previous page

        //button and editext
        recordNumber = findViewById(R.id.record_id);
        jobType = findViewById(R.id.job_selection);
        resumePath = findViewById(R.id.resume_tv);
        selectResume = findViewById(R.id.upload_resumeBtn);
        submit = findViewById(R.id.submitBtn);
        ptvList = new ArrayList<>();

        getRecordCount();
        //result launcher
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if(data!=null){
                            resumeUri = data.getData();
                            String sPath = resumeUri.getPath();
                            resumePath.setText(Html.fromHtml(
                                    ""+sPath
                            ));
                        }
                    }
                }
        );

        //button onclick
        selectResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(SeekerDocumentUploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SeekerDocumentUploadActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
                        selectPDF();
                }
            }
        });


    //button to submit the records
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecordID();
            }
        });
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

    private void updateCount() {
        int count = ptvList.size() + 1;
        recordStr = String.valueOf(count);
        Log.d("Count Log", "String: " + recordStr);
    }

    private void addRecordID() {
        String nameStr = passToVerification.getName();
        String mobileTxt =passToVerification.getMobile();
        String emailTxt = passToVerification.getEmail();
        String userTypeText = passToVerification.getUserType();
        resumeStr = resumeUri.toString();
        //passToVerification = new PassToVerification(resumeStr);
        //String resume = passToVerification.getUri();
        addToDatabase(recordStr,nameStr,emailTxt,mobileTxt,userTypeText,resumeStr);

    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_OK){
            //Progress bar
            dialog = new ProgressDialog(this);
            dialog.setMessage("Data Uploading");
            dialog.show();
        }
    }

 */

    private void selectPDF() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }else{
            Toast.makeText(getApplicationContext(),"Please give permission",Toast.LENGTH_SHORT).show();
        }
    }

    private void addToDatabase(String recordNum,String nameStr, String emailTxt, String mobileTxt, String userTypeTxt, String resume) {
        passToVerification.setRecordNum(recordNum);
        passToVerification.setName(nameStr);
        passToVerification.setEmail(emailTxt);
        passToVerification.setMobile(mobileTxt);
        passToVerification.setUserType(userTypeTxt);
        passToVerification.setResume(resume);

        databaseReference.child(passToVerification.getRecordNum()).setValue(passToVerification).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SeekerDocumentUploadActivity.this,"Data Added",Toast.LENGTH_SHORT).show();
                    Intent dashIntent = new Intent(SeekerDocumentUploadActivity.this, SeekerDashboardActivity.class);
                    startActivity(dashIntent);
                    finish();
                }else{
                    Toast.makeText(SeekerDocumentUploadActivity.this, "Failed to create record: Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SeekerDocumentUploadActivity.this, "Failed to create record: " + e.getMessage(), Toast.LENGTH_SHORT).show();

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
