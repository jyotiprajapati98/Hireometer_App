package com.example.jobhub.Utilities;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Utility {
    private Context context;

    public static void setErrorTextListener(EditText editText, TextInputLayout textInputLayout){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static boolean ValidateEmail(String email){
        if(TextUtils.isEmpty(email)){
            return false;
        } else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
