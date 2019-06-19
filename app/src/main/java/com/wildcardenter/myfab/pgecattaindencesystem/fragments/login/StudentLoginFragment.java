package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.content.Intent;
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
import com.wildcardenter.myfab.pgecattaindencesystem.activities.StudentLandingActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLoginFragment extends Fragment {

    private static final String TAG = "StudentLoginFragment";

    private TextView register;
    private EditText studentEmail, studentPassword, studentAccessCode;
    private Button studentLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_login, container, false);
        register = view.findViewById(R.id.gotoStudentSignUp);
        studentEmail = view.findViewById(R.id.studentSignInEmail);
        studentPassword = view.findViewById(R.id.studentSignInPassword);
        studentAccessCode = view.findViewById(R.id.studentSignInPrivateAC);
        studentLogin = view.findViewById(R.id.studentSignInButton);
        firebaseAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.loginFragContainer, new StudentSignUpFragment()).commit();

        });
        studentLogin.setOnClickListener(v -> {
            String email = studentEmail.getText().toString().trim();
            String pass = studentPassword.getText().toString().trim();
            String accessCode = studentAccessCode.getText().toString().trim();
            if (email.contains("@") && !pass.isEmpty() && !accessCode.isEmpty()) {
                attemptStudentLogin(email, pass, Integer.parseInt(accessCode));
            } else {
                Toast.makeText(getContext(), "Fields Required", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void attemptStudentLogin(String email, String pass, int accessCode) {
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
                Toast.makeText(getContext(), task.getResult().getUser().getUid(), Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("Users/Students")
                        .child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Student student = dataSnapshot.getValue(Student.class);
                            if (student != null) {
                                if (student.getAccessCode() == accessCode) {
                                    dialog.cancel();
                                    startActivity(new Intent(getActivity(), StudentLandingActivity.class));
                                    getActivity().finish();
                                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Access Code Does Not Match", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    dialog.cancel();

                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Snapsot not exist", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            dialog.cancel();

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.cancel();
                        firebaseAuth.signOut();
                    }
                });
            }
        });
    }

}
