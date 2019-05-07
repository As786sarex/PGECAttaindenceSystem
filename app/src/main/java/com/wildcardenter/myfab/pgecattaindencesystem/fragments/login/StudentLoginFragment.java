package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


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
public class StudentLoginFragment extends Fragment {


    public StudentLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_login, container, false);

        return view;
    }

}
