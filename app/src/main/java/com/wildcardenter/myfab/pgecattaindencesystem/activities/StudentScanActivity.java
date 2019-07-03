package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scan);
        pref=SharedPref.getSharedPref(this,STUDENT_SUBCODES_FILE);

        getWindow().setStatusBarColor(getResources().getColor(R.color.lightBlue));
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        findViewById(R.id.StudentSubmitAttendanceBtn).setOnClickListener(v->{
            String s= (String) pref.getData(contents[1],TYPE_STRING);
            if (s==null){
                HashMap<String,String> mp= new HashMap<>();
                mp.put(contents[1],contents[1]);
                FirebaseFirestore.getInstance().collection(STUDENT_SUBCODE_REF).document(firebaseAuth.getUid())
                        .set(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           pref.setData(contents[1],contents[1],TYPE_STRING);
                       }
                    }
                });


            }
            //makeTrannsaction(path,contents[1]);

        });
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
                    path = "Attendance" +"/"+ contents[1] + "/" + contents[2] + "/" + contents[3]
                            + "/" + "presentStudentList" +"/"+ firebaseAuth.getUid();
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
        mp.put(val, val);
        database.goOnline();
        String roll = (String) SharedPref.getSharedPref(this, LOGIN_TYPE_FILE).getData(ROLL_NO, TYPE_STRING);

        Resposnse resposnse = new Resposnse(roll, firebaseAuth.getUid(), System.currentTimeMillis());
        DatabaseReference reference=database.getReference(path);
        reference.setValue(resposnse).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection(subjectCode).document(roll).set(mp).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(this, "Attendance was Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        reference.removeValue();
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.goOffline();
    }
}
