package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Student;

public class StudentLandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        findViewById(R.id.StudentLogOut).setOnClickListener(v-> startActivity(new Intent(StudentLandingActivity.this,TeacherGeneratorActivity.class)));
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
    }

    public void initiateScan(View view) {
        startActivity(new Intent(this,StudentScanActivity.class));
    }
}
