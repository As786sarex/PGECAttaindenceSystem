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
public class TeacherSignUpFragment extends Fragment {


    public TeacherSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_sign_up, container, false);
    }

}
