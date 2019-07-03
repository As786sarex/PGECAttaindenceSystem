package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.StudentLandingActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherLoginFragment extends Fragment {

    private static final String TAG = "TeacherLoginFragment";

    private TextView teacherRegister;
    private EditText teacherEmail, teacherPassword, teacherAccessCode;
    private Button teacherLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_teacher_login, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        teacherRegister=view.findViewById(R.id.TeacherGoToSignUpBtn);
        teacherEmail=view.findViewById(R.id.TeacherSignInEmail);
        teacherPassword=view.findViewById(R.id.TeacherSignInPassword);
        teacherAccessCode=view.findViewById(R.id.TeacherSignInAccessCode);
        teacherLogin=view.findViewById(R.id.TeacherLoginButton);
        teacherRegister.setOnClickListener(v-> getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.loginFragContainer,new TeacherSignUpFragment()).commit());
        teacherLogin.setOnClickListener(v -> {
            String email = teacherEmail.getText().toString().trim();
            String pass = teacherPassword.getText().toString().trim();
            String accessCode = teacherAccessCode.getText().toString().trim();
            if (email.contains("@") && !pass.isEmpty() && !accessCode.isEmpty()&& accessCode.length()==4) {
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
                            Student student = dataSnapshot.getValue(Student.class);
                            if (student != null) {
                                if (student.getAccessCode() == accessCode) {
                                    dialog.cancel();
                                    startActivity(new Intent(getActivity(), StudentLandingActivity.class));
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
