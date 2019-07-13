package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav.AnnouncementFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav.StudentGenerateReport;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav.StudentLandingFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

public class StudentLandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "StudentLandingActivity";
    TextView announcement;
    private DrawerLayout drawer;
    FirebaseAuth auth;
    int count=0;
    AlertDialog.Builder builder;
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        builder=new AlertDialog.Builder(this,R.style.dialog_anim);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.Drawer_Opened, R.string.Drawer_Closed);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        announcement= (TextView) MenuItemCompat.getActionView(navigationView.getMenu()
                .findItem(R.id.student_nav_show_notice));
        announcement.setGravity(Gravity.CENTER_VERTICAL);
        announcement.setTypeface(null, Typeface.BOLD);
        announcement.setTextSize(17f);
        announcement.setTextColor(Color.parseColor("#9b0000"));
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container, new StudentLandingFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.startScan);
            getSupportActionBar().setTitle("Scan QR Code");
        }
        dialog=new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver=new NetworkStateReceiver(dialog);
        filter=new IntentFilter();
    }

    public void logOut() {
        auth.signOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    void setBadge(String count){
        announcement.setText(String.format(" %s ", count));

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.student_nav_show_notice) {

            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container,
                    new AnnouncementFragment())
                    .commitNow();
            getSupportActionBar().setTitle("Latest Announcements");
           /* try {
                String filename = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".pdf";
                File file = new File(filename);
                PdfWriter writer = new PdfWriter(file);

                PdfDocument document = new PdfDocument(writer);
                Document pdf = new Document(document);
                float[] colomWidth = {50f, 100f, 50f, 250f};
                Table table = new Table(colomWidth, true);
                table.setMarginTop(60f);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                table.addHeaderCell(new Cell().add(new Paragraph("Roll No")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER).setMinWidth(200));
                table.addHeaderCell(new Cell().add(new Paragraph("no Of Class")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                table.addHeaderCell(new Cell().add(new Paragraph("Percentage")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
                table.addHeaderCell(new Cell().add(new Paragraph("List of Dates  of class attended")).setBackgroundColor(DeviceGray.GRAY)
                        .setTextAlignment(TextAlignment.CENTER));

                FirebaseFirestore.getInstance().collection("CS301").get(Source.CACHE).addOnSuccessListener(this, snapshots -> {
                    for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                        table.addCell(new Cell().add(new Paragraph(snapshot.getId())).setMinWidth(200));
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(snapshot.getData().size()))));
                        com.itextpdf.layout.element.List listt = new com.itextpdf.layout.element.List().setKeepTogether(true);
                        for (Object resposnse : snapshot.getData().values()) {
                            listt.add(resposnse.toString()).setWidth(300);
                        }
                        table.addCell(new Cell().add(new Paragraph(((float) snapshot.getData().size() / 5.0) * 100 + "%")));
                        table.addCell(listt);
                    }
                    pdf.add(table);
                    pdf.close();
                    Log.d(TAG, "run: Successfully stored on " + filename);

                }).addOnFailureListener(this, e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/


        } else if (id == R.id.addtemp) {
            setBadge(String.valueOf(count));
            count++;
            /*Map<String, Object> mp = new HashMap<>();
            for (int i = 0; i < 4; i++) {
                mp.put(SimpleDateFormat.getDateTimeInstance()
                        .format(new Date()) + i, SimpleDateFormat.getDateTimeInstance()
                        .format(new Date()) + i);
                FirebaseFirestore.getInstance().collection("CS301")
                        .document("35000116047" + i).set(mp)
                        .addOnSuccessListener(this, aVoid -> {
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(this, e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            }*/

        } else if (id == R.id.startScan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container, new StudentLandingFragment())
                    .commit();
            getSupportActionBar().setTitle("Scan qr Code");
        } else if (id == R.id.openStudentReport) {
            getSupportFragmentManager().beginTransaction().replace(R.id.student_fragment_container, new StudentGenerateReport())
                    .addToBackStack(null)
                    .commit();
            getSupportActionBar().setTitle("Scan qr Code");
        }
        else if (id==R.id.drawer_student_logout){
            builder.setTitle("Log out?")
                    .setMessage("You're about to log out of your account.")
                    .setPositiveButton("Log out", (dialog, which) -> {
                        logOut();
                    })
                    .setNegativeButton("Cancel",null)
                    .create().show();
        }
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
    @Override
    protected void onResume() {
        super.onResume();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
