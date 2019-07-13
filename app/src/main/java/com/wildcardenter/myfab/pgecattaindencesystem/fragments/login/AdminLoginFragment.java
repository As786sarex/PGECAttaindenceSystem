package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.MainActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.TeacherLandingActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Admin;

public class AdminLoginFragment extends Fragment {
    private static final String TAG = "AdminLoginFragment";

    private EditText adminEmail, adminPassword, adminAccessCode;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        adminEmail = view.findViewById(R.id.adminSignInEmail);
        adminPassword = view.findViewById(R.id.adminSignInPassword);
        adminAccessCode = view.findViewById(R.id.adminSignInPrivateAC);
        view.findViewById(R.id.adminSignInButton).setOnClickListener(v -> {
            String email = adminEmail.getText().toString().trim();
            String pass = adminPassword.getText().toString().trim();
            String accessCode = adminAccessCode.getText().toString().trim();
            if (email.contains("@") && !pass.isEmpty() && !accessCode.isEmpty()) {
                attemptAdminLogin(email, pass, Integer.parseInt(accessCode));
            } else {
                Toast.makeText(getContext(), "Fields Required", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.gotoAdminSignUp).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.loginFragContainer, new AdminSignUpFragment()).commit();

        });
        return view;
    }

    private void attemptAdminLogin(String email, String pass, int accessCode) {
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
                FirebaseDatabase.getInstance().getReference("Users/Admins")
                        .child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Admin admin = dataSnapshot.getValue(Admin.class);
                            if (admin != null) {
                                if (admin.getAccessCode().equals(String.valueOf(accessCode))) {
                                    Log.e("ac", "ac successful");
                                    dialog.cancel();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
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
