package com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.StudentScanActivity;

public class StudentLandingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_student_landing, container, false);
        v.findViewById(R.id.ScanQrCode).setOnClickListener(view->{
            initiateScan();
        });
        return  v;
    }

    void initiateScan() {
        startActivity(new Intent(getContext(), StudentScanActivity.class));
    }

}
