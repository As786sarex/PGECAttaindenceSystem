package com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildcardenter.myfab.pgecattaindencesystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherReportFragment extends Fragment {


    public TeacherReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_teacher_report, container, false);

        return view;
    }

}
