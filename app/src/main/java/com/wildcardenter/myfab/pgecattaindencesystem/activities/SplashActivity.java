package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView spqr;
    FirebaseAuth auth;
    FirebaseUser user;
    Boolean isVerifiedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(Color.WHITE);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        spqr = findViewById(R.id.spQr);
        spqr.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_off_anim));


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            if (user != null) {
                FirebaseDatabase.getInstance().getReference("Users/Students").child(auth.getUid())
                        .child("accessCode").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            startActivity(new Intent(SplashActivity.this, StudentLandingActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SplashActivity.this, "student not verified", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                FirebaseDatabase.getInstance().getReference("Users/Teachers").child(auth.getUid())
                        .child("verified").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            isVerifiedAccount = dataSnapshot.getValue(Boolean.class);
                            if (isVerifiedAccount) {
                                startActivity(new Intent(SplashActivity.this, TeacherLandingActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SplashActivity.this, "Teacher Not Verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
