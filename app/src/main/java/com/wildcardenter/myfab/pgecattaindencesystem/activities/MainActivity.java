package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Attendance;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Resposnse;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.test);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                .child("CS101").child(SimpleDateFormat.getDateInstance().format(new Date()));
        dialog=new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver=new NetworkStateReceiver(dialog);
        filter=new IntentFilter();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Attendance attendance = dataSnapshot.getValue(Attendance.class);
                if (attendance != null) {
                    if (attendance.getPresentStudentList() == null) {
                        Toast.makeText(MainActivity.this, "hashmap has null vaules", Toast.LENGTH_SHORT).show();
                    }
                    String test = attendance.getDate() + "\n" + attendance.getTimestamp()+"\n";
                    StringBuilder test1 = new StringBuilder();
                    ArrayList<Resposnse> resposnses = new ArrayList<>(attendance.getPresentStudentList().values());
                    for (Resposnse resposnse : resposnses) {
                        test1.append(resposnse.getRoll_no());
                    }
                    test = test + test1;
                    textView.setText(test);
                    Toast.makeText(MainActivity.this, ServerValue.TIMESTAMP.get("timestamp")+
                            "\n"+ServerValue.TIMESTAMP.get(".sv"), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
