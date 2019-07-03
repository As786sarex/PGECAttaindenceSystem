package com.wildcardenter.myfab.pgecattaindencesystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.fragments.settings.StudentSettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_container,new StudentSettingsFragment())
                .commit();

    }
}
