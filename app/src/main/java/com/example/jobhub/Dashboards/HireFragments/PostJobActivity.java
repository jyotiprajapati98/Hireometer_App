package com.example.jobhub.Dashboards.HireFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jobhub.R;

import org.jetbrains.annotations.NotNull;

public class PostJobActivity extends Fragment {
    private EditText postName,jobType, salaryRange, jobLocation, description;
    private Spinner expSpinner;
    public PostJobActivity() {
        // required empty public constructor.
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_job_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postName = view.findViewById(R.id.postName);
        expSpinner = view.findViewById(R.id.ExpSpinner);
        initExpSpinner();


        //ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }

    private void initExpSpinner() {
        String[] items = new String[]{"0", "0-2", "2-5","5+"};
        //ArrayAdapter<String>adapter= new ArrayAdapter<String>(this,R.layout.post_job_activity,items);
        //expSpinner.setAdapter(adapter);
        //ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(getActivity(), R.array.languages, R.layout.post_job_activity);
        //expSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        expSpinner.setAdapter(adapt);
        expSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }
}
