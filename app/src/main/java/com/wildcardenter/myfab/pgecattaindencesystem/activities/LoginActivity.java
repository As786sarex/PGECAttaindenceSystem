package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.AdminLoginFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.StudentLoginFragment;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.login.TeacherLoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void openTeacherLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loginFragContainer,new TeacherLoginFragment())
                .commit();
    }

    public void openAdminLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loginFragContainer,new AdminLoginFragment()).commit();
    }

    public void openStudentLoginFragment(View view) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.loginFragContainer,new StudentLoginFragment()).commit();
    }
}
