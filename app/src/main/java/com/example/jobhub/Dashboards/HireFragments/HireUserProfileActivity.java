package com.example.jobhub.Dashboards.HireFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_user_profile_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //dbRef = firebaseDatabase.getReference("Hire");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Hire").child(firebaseUser.getUid()).child("Hire");

        //initialize the values
        name=findViewById(R.id.ProfileName);
        email=findViewById(R.id.ProfileEmail);
        mobile=findViewById(R.id.ProfileMobile);
        designation = findViewById(R.id.ProfileDesignation);
        companyName=findViewById(R.id.ProfileComName);
        companyLink=findViewById(R.id.ProfileComLink);
        Query query = dbRef.orderByChild("mobile").equalTo(firebaseUser.getPhoneNumber());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //retrive data
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    String nameStr=""+dataSnapshot1.child("name").getValue();
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

    }
}
