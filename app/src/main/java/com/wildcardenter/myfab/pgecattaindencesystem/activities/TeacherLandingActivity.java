package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav.StudentLandingFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav.TeacherSubChooserFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;

public class TeacherLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_landing);
        Toolbar toolbar = findViewById(R.id.teacher_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.teacher_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.Drawer_Opened, R.string.Drawer_Closed);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.teacher_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,
                    new TeacherSubChooserFragment()).commit();
            navigationView.setCheckedItem(R.id.startScan);
            getSupportActionBar().setTitle("Generate QR Code");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            if (!getSupportActionBar().isShowing()) {
                getSupportActionBar().show();
            }

        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.open_settings_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.open_Settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
