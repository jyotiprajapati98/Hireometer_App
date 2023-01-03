package com.example.jobhub.Dashboards.HireFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobhub.Dashboards.HireDashboardActivity;
import com.example.jobhub.Models.PassToVerification;
import com.example.jobhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HireUserProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;
    private TextView name, email, mobile, designation, companyName, companyLink;
    private ProgressDialog pd;
    private List<PassToVerification> ptv;
    private PassToVerification passToVerification;
    private List<String> keys;
    private ListView listView;
    private String enteredUserName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_user_profile_activity);
        //to access database
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference().child("Hire");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Log.d("In Dashboard","test2");
            passToVerification = (PassToVerification) bundle.getSerializable("hirePTVObj");
        }else{
            Toast.makeText(HireUserProfileActivity.this, "Cannot retrieve data", Toast.LENGTH_LONG).show();
            finish();
        }
        enteredUserName = passToVerification.getName();
        name=findViewById(R.id.ProfileName);

        getData();

        /*
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //dbRef = firebaseDatabase.getReference("Hire");
        //dbRef = firebaseDatabase.getReference().child("Hire").child(firebaseUser.getUid()).child("Hire");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Hire").child(firebaseUser.getUid());
        System.out.println("Successfully fetched user data: " + firebaseUser.getUid());

        //initialize the values

        name=findViewById(R.id.ProfileName);
        email=findViewById(R.id.ProfileEmail);
        mobile=findViewById(R.id.ProfileMobile);
        designation = findViewById(R.id.ProfileDesignation);
        companyName=findViewById(R.id.ProfileComName);
        companyLink=findViewById(R.id.ProfileComLink);
        //Query query = dbRef.orderByChild("mobile").equalTo(firebaseUser.getPhoneNumber());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //retrive data
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    String nameStr=""+dataSnapshot1.child("name").getValue().toString();

                    String emailStr=""+dataSnapshot1.child("email").getValue();
                    String mobileStr=""+dataSnapshot1.child("mobile").getValue();
                    String desingnationStr=""+dataSnapshot1.child("desingnation").getValue();
                    String company_nameStr=""+dataSnapshot1.child("company_name").getValue();
                    String company_websiteStr=""+dataSnapshot1.child("company_website").getValue();

                    //set the values
                    name.setText(nameStr);
                    email.setText(emailStr);
                    mobile.setText(mobileStr);
                    designation.setText(desingnationStr);
                    companyName.setText(company_nameStr);
                    companyLink.setText(company_websiteStr);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

         */

    }

    private void getData() {
        Query checkUser = dbRef.orderByChild("name").equalTo(enteredUserName);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    //passToVerification = dataSnapshot.getValue(PassToVerification.class);
                    //String txt = passToVerification.getName()+" : "+passToVerification.getCompany_name();
                    String nameStr=""+dataSnapshot1.child("name").getValue().toString();

                    String emailStr=""+dataSnapshot1.child("email").getValue();
                    String mobileStr=""+dataSnapshot1.child("mobile").getValue();
                    String desingnationStr=""+dataSnapshot1.child("desingnation").getValue();
                    String company_nameStr=""+dataSnapshot1.child("company_name").getValue();
                    String company_websiteStr=""+dataSnapshot1.child("company_website").getValue();

                }
               // adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
