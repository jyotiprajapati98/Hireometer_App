package com.example.jobhub.Dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.jobhub.Activities.OTP_VerificationActivity;
import com.example.jobhub.Adapter.HirePageAdapter;
import com.example.jobhub.Dashboards.HireFragments.AlreadyPostedJobActivity;
import com.example.jobhub.Dashboards.HireFragments.HireUserProfileActivity;
import com.example.jobhub.Dashboards.HireFragments.PostJobActivity;
import com.example.jobhub.Dashboards.HireFragments.ViewApplicantActivity;
import com.example.jobhub.MainActivity;
import com.example.jobhub.Models.PassToVerification;
import com.example.jobhub.R;
import com.google.android.material.tabs.TabLayout;

public class HireDashboardActivity extends AppCompatActivity {
    public PassToVerification passToVerification;
    private HirePageAdapter hirePageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_dashboard_activity);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Log.d("In Dashboard","test2");
            passToVerification = (PassToVerification) bundle.getSerializable("hirePTVObj");
        }else{
            Toast.makeText(HireDashboardActivity.this, "Cannot retrieve data", Toast.LENGTH_LONG).show();
            finish();
        }

        viewPager = findViewById(R.id.Hire_Pager);
        // setting up the adapter
        hirePageAdapter = new HirePageAdapter(getSupportFragmentManager());

        // add the fragments
        hirePageAdapter.add(new PostJobActivity(), "Post New Job");
        hirePageAdapter.add(new AlreadyPostedJobActivity(), "Already Posted Jobs");
        hirePageAdapter.add(new ViewApplicantActivity(), "See Applicants");

        // Set the adapter
        viewPager.setAdapter(hirePageAdapter);
        tabLayout = findViewById(R.id.hire_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //get extract the data
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hire_top_bar_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.hireProfile){
            hireUserProfile();
        }else if(id==R.id.logout){
            System.out.println("Logout");
        }
        return true;
    }
    public void hireUserProfile(){
        Intent intent = new Intent(HireDashboardActivity.this, HireUserProfileActivity.class);
        intent.putExtra("hirePTVObj", passToVerification);
        startActivity(intent);
    }
}

