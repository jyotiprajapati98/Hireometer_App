package com.example.jobhub.Dashboards;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.jobhub.Adapter.SeekerViewPagerAdapter;
import com.example.jobhub.Dashboards.SeekerFragments.AllJobActivity;
import com.example.jobhub.Dashboards.SeekerFragments.AppliedJobActivity;
import com.example.jobhub.Dashboards.SeekerFragments.JobApplicationStatusActivity;
import com.example.jobhub.R;
import com.google.android.material.tabs.TabLayout;


public class SeekerDashboardActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private SeekerViewPagerAdapter seekerviewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeker_dashboard_activity);
        viewPager = findViewById(R.id.pager);

        // setting up the adapter
        seekerviewPagerAdapter = new SeekerViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        seekerviewPagerAdapter.add(new AllJobActivity(), "All Jobs");
        seekerviewPagerAdapter.add(new AppliedJobActivity(), "Applied");
        seekerviewPagerAdapter.add(new JobApplicationStatusActivity(), "Status of Application");

        // Set the adapter
        viewPager.setAdapter(seekerviewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_activity, menu);
        return true;
    }

/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}
