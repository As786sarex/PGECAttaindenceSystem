package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.scottyab.aescrypt.AESCrypt;
import com.wildcardenter.myfab.pgecattaindencesystem.R;

import java.security.GeneralSecurityException;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.PASSWORD;

public class StudentScanActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scan);
        firebaseAuth=FirebaseAuth.getInstance();
        new IntentIntegrator(this).initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                finish();
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    String res= AESCrypt.decrypt(PASSWORD,result.getContents());
                    String[] contents=res.split("@");
                    String path="Attendance"+contents[1]+"/"+contents[2]+"/"+contents[3]
                            +"/"+"presentStudentList"+firebaseAuth.getUid();
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
}
