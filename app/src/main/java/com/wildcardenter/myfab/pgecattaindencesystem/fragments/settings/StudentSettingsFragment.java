package com.wildcardenter.myfab.pgecattaindencesystem.fragments.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;/*
    Class On Package com.wildcardenter.myfab.pgecattaindencesystem.fragments.settings
    
    Created by Asif Mondal on 28-06-2019 at 17:30
*/

import com.wildcardenter.myfab.pgecattaindencesystem.R;


public class StudentSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.student_settings_fragment, "rootkey");
    }

}
