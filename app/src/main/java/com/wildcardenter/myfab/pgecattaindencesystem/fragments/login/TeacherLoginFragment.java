package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.TeacherLandingActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Teacher;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.EMAIL;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.LOGIN_TYPE_FILE;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.NAME;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.TYPE_STRING;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherLoginFragment extends Fragment {

    private static final String TAG = "TeacherLoginFragment";

    private EditText teacherEmail, teacherPassword, teacherAccessCode;
    private FirebaseAuth firebaseAuth;
    private SharedPref pref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        SharedPref.setNullMethod();
        pref = SharedPref.getSharedPref(getContext(), Constants.TEACHER_ALLOTED_SUBS);
        TextView teacherRegister = view.findViewById(R.id.TeacherGoToSignUpBtn);
        teacherEmail = view.findViewById(R.id.TeacherSignInEmail);
        teacherPassword = view.findViewById(R.id.TeacherSignInPassword);
        teacherAccessCode = view.findViewById(R.id.TeacherSignInAccessCode);
        Button teacherLogin = view.findViewById(R.id.TeacherLoginButton);
        teacherRegister.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.loginFragContainer, new TeacherSignUpFragment()).commit());
        teacherLogin.setOnClickListener(v -> {
            String email = teacherEmail.getText().toString().trim();
            String pass = teacherPassword.getText().toString().trim();
            String accessCode = teacherAccessCode.getText().toString().trim();
            if (email.contains("@") && !pass.isEmpty() && !accessCode.isEmpty() && accessCode.length() == 4) {
                attemptTeacherLogin(email, pass, Integer.parseInt(accessCode));
            } else {
                Toast.makeText(getContext(), "Fields Required", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void attemptTeacherLogin(String email, String pass, int accessCode) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Log In");
        dialog.setMessage("Logging You In....");
        dialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnFailureListener(getActivity(), e -> {
            Log.e(TAG, e.getMessage());
            dialog.cancel();
        }).addOnCompleteListener(getActivity(), task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error : " + task.getException());
            } else {
                FirebaseDatabase.getInstance().getReference("Users/Teachers")
                        .child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Teacher teacher = dataSnapshot.getValue(Teacher.class);
                            if (teacher != null) {
                                if (teacher.getAccessCode().equals(String.valueOf(accessCode))) {
                                    Log.e("ac","ac successful");
                                    dialog.cancel();
                                    if (teacher.getAllottedSubjects() != null) {
                                        for (String s : teacher.getAllottedSubjects().keySet()) {
                                            pref.setData(s, s, TYPE_STRING);
                                        }
                                        SharedPref.setNullMethod();
                                        SharedPref sharedPref = SharedPref.getSharedPref(getContext(), LOGIN_TYPE_FILE);
                                        sharedPref.setData(EMAIL, teacher.getEmail(), TYPE_STRING);
                                        sharedPref.setData(NAME, teacher.getName(), TYPE_STRING);
                                    }
                                    startActivity(new Intent(getActivity(), TeacherLandingActivity.class));
                                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();

                                } else {
                                    Toast.makeText(getContext(), "Access Code Does Not Match", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    dialog.cancel();

                                }
                            }
                        } else {
                            Log.d(TAG, "onDataChange: Snapshot not exist");
                            firebaseAuth.signOut();
                            dialog.cancel();

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        firebaseAuth.signOut();
                    }
                });
            }
        });
    }

}
