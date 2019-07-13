package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.scottyab.aescrypt.AESCrypt;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Resposnse;
import com.wildcardenter.myfab.pgecattaindencesystem.utils.NetworkStateReceiver;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.LOGIN_TYPE_FILE;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.PASSWORD;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.ROLL_NO;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.STUDENT_SUBCODES_FILE;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.STUDENT_SUBCODE_REF;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.TYPE_STRING;

public class StudentScanActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String[] contents;
    String path;
    SharedPref pref;
    AlertDialog.Builder builder;
    private NetworkStateReceiver receiver;
    private AlertDialog dialog;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scan);
        pref = SharedPref.getSharedPref(this, STUDENT_SUBCODES_FILE);
        builder = new AlertDialog.Builder(this, R.style.dialog_anim)
                .setCancelable(true)
                .setView(R.layout.dialog_failed);

        getWindow().setStatusBarColor(getResources().getColor(R.color.lightBlue));
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        findViewById(R.id.StudentSubmitAttendanceBtn).setOnClickListener(v -> {
            String s = (String) pref.getData(contents[1], TYPE_STRING);
            if (s == null) {
                HashMap<String, String> mp = new HashMap<>();
                mp.put(contents[1], contents[1]);
                FirebaseFirestore.getInstance().collection(STUDENT_SUBCODE_REF).document(firebaseAuth.getUid())
                        .set(mp).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        pref.setData(contents[1], contents[1], TYPE_STRING);
                    }
                });


            }
            makeTrannsaction(path, contents[1]);

        });
        dialog=new AlertDialog.Builder(this, R.style.dialog_anim)
                .setTitle("No Internet Connection!!!")
                .setMessage("Make sure that you're connected to the internet.")
                .setCancelable(false).create();
        receiver=new NetworkStateReceiver(dialog);
        filter=new IntentFilter();
        new IntentIntegrator(this).initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                finish();
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    String res = AESCrypt.decrypt(PASSWORD, result.getContents());
                    contents = res.split("@");
                    path = "attendance" + "/" + contents[1] + "/" + contents[2] + "/" + contents[3]
                            + "/" + "presentStudentList" + "/" + firebaseAuth.getUid();
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();

                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Decryption Error", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void makeTrannsaction(String path, String subjectCode) {
        Map<String, Object> mp = new HashMap<>();
        String val = SimpleDateFormat.getDateTimeInstance().format(new Date());
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        mp.put(val, val);
        database.goOnline();
        String roll = (String) SharedPref.getSharedPref(this, LOGIN_TYPE_FILE).getData(ROLL_NO, TYPE_STRING);

        Resposnse resposnse = new Resposnse(roll, firebaseAuth.getUid(), System.currentTimeMillis());
        DatabaseReference reference = database.getReference(path);
        reference.setValue(resposnse).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection(subjectCode).document("35000116047").update(mp).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        dialog.dismiss();
                        builder.setView(R.layout.dialog_successful)
                                .setOnDismissListener(dial -> finish()).create().show();
                        Toast.makeText(this, "Attendance was Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        reference.removeValue();
                        builder.create().show();
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });
            } else {
                dialog.dismiss();
                builder.create().show();
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.goOffline();
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
