package com.example.jobhub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobhub.Models.PassToVerification;
import com.example.jobhub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_VerificationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public PassToVerification passToVerification;
    private String phoneNumber;
    private Button getOpt, verifyOtp;
    private EditText edtOTP;
    private String verificationId;
    private String userType;
    private String name, email, mobile;
    private String recordNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification_activity);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Log.d("In OTP Verification","test2");
            passToVerification = (PassToVerification) bundle.getSerializable("PTVObj");
        }else{
            Toast.makeText(OTP_VerificationActivity.this, "Cannot retrieve data", Toast.LENGTH_LONG).show();
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        getOpt = findViewById(R.id.getOTP);
        edtOTP = findViewById(R.id.OTP_edit_text);
        verifyOtp = findViewById(R.id.verifyOTP);
        //phoneNumber = findViewById(R.id.phone_edit_text);
        phoneNumber = passToVerification.getMobile();
        recordNum = passToVerification.getRecordNum();
        name = passToVerification.getName();
        email = passToVerification.getEmail();
        mobile = passToVerification.getMobile();
        System.out.println("phoneNumber" + passToVerification.getMobile());

        userType = passToVerification.getUserType();
        Log.d("In OTP Verification","test1");

        // setting onclick listener for generate OTP button.
        getOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(OTP_VerificationActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+91" + phoneNumber;
                    sendVerificationCode(phone);
                }
            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(OTP_VerificationActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(userType.equals("hire")){
                                PassToVerification hirePTV = new PassToVerification(recordNum,name, email, mobile, userType);
                                Intent hireIntent = new Intent(OTP_VerificationActivity.this, HireSecondPageActivity.class);
                                hireIntent.putExtra("hirePTVObj", hirePTV);
                                startActivity(hireIntent);
                                finish();
                            }else{
                                PassToVerification seekerPTV = new PassToVerification(recordNum,name, email, mobile, userType);
                                Intent seekerIntent = new Intent(OTP_VerificationActivity.this, SeekerDocumentUploadActivity.class);
                                seekerIntent.putExtra("seekerPTVObj", seekerPTV);
                                startActivity(seekerIntent);
                                finish();
                            }

                        } else {
                            Toast.makeText(OTP_VerificationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)		 // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)				 // Activity (for callback binding)
                        .setCallbacks(mCallBack)		 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtOTP.setText(code);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OTP_VerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

}
