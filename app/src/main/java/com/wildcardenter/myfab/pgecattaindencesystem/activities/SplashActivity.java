package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.LOGIN_TYPE_FILE;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private ImageView spqr;
    FirebaseAuth auth;
    FirebaseUser user;
    SharedPref  sharedPref;
    Boolean isVerifiedAccount;
    int type,type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        sharedPref=new SharedPref(this,LOGIN_TYPE_FILE);
        new Handler().postDelayed(() -> {
                if (user!=null) {
                    FirebaseDatabase.getInstance().getReference("Users/Students").child(auth.getUid())
                            .child("isVerified").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                isVerifiedAccount = dataSnapshot.getValue(Boolean.class);
                                if (isVerifiedAccount){
                                    startActivity(new Intent(SplashActivity.this,StudentLandingActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SplashActivity.this, "student not verified", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            type=43;
                        }
                    });
                    FirebaseDatabase.getInstance().getReference("Users/Teachers").child(auth.getUid())
                            .child("isVerified").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                isVerifiedAccount = dataSnapshot.getValue(Boolean.class);
                                if (isVerifiedAccount){
                                    startActivity(new Intent(SplashActivity.this,TeacherLandingActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SplashActivity.this, "Teacher Not Verified", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            type2=42;
                        }
                    });
                }
                else {
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                }
        }, 2000);        /*spqr = findViewById(R.id.spQr);
        String text = "Asif Mondal";// Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            spqr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        HashMap<String, Resposnse> present = new HashMap<>();
        Random random = new Random();
        Resposnse resposnse = new Resposnse("35000116047", "bhbvbvv", 33, System.currentTimeMillis());
        for (int i = 0; i <= 10; i++) {
            String key = String.valueOf(random.nextInt(3000000));
            present.put(key, resposnse);

        }
        Date date = new Date();
        Attendance attendance = new Attendance(TIMESTAMP, 32, present);
        DatabaseReference r = FirebaseDatabase.getInstance().getReference("Attendance")
                .child("CS101").child(SimpleDateFormat.getDateInstance().format(date));
        Toast.makeText(this, attendance.getPresentStudentList().toString(), Toast.LENGTH_SHORT).show();
        r.setValue(attendance);*/

    }

}
