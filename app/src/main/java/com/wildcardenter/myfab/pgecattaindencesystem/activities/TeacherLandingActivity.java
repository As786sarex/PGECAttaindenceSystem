package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav.CreateNotificationFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav.TeacherReportFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav.TeacherSubChooserFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

public class TeacherLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private AlertDialog.Builder builder;
    FirebaseAuth auth;
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_landing);
        Toolbar toolbar = findViewById(R.id.teacher_toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this, R.style.dialog_anim);
        drawer = findViewById(R.id.teacher_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.Drawer_Opened, R.string.Drawer_Closed);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.teacher_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,
                    new TeacherSubChooserFragment()).commit();
            navigationView.setCheckedItem(R.id.teacher_open_generator_frag);
            getSupportActionBar().setTitle("Generate QR Code");
        }
        dialog = new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver = new NetworkStateReceiver(dialog);
        filter = new IntentFilter();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.teacher_open_generator_frag) {
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,
                    new TeacherSubChooserFragment()).commit();
            getSupportActionBar().setTitle("Generate QR Code");
        } else if (id == R.id.teacher_generate_report_frag) {
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,
                    new TeacherReportFragment()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Generate Report");
        } else if (id == R.id.CreateNotification) {
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,
                    new CreateNotificationFragment()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Send Notifications");
        } else if (id == R.id.drawer_teacher_logout) {
            builder.setTitle("Log out?")
                    .setMessage("You're about to log out of your account.")
                    .setPositiveButton("Log out", (dialog, which) -> {
                        logOut();
                    })
                    .setNegativeButton("Cancel", null)
                    .create().show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
