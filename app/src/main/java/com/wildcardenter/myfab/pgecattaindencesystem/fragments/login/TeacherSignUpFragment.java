package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Teacher;

public class TeacherSignUpFragment extends Fragment {
    private MaterialEditText name, email, password, confirmPassword, privateAccessCode, teacherAccessCode;


    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teacher_sign_up, container, false);
        name = v.findViewById(R.id.TeacherSignUpName);
        email = v.findViewById(R.id.TeacherSignUpEmail);
        password = v.findViewById(R.id.TeacherSignUpPassword);
        confirmPassword = v.findViewById(R.id.TeacherSignUpConfPassword);
        privateAccessCode = v.findViewById(R.id.TeacherSignUpPrivateACode);
        teacherAccessCode = v.findViewById(R.id.TeacherSignUpTeacherACode);
        v.findViewById(R.id.teacherSignUpLoginButton).setOnClickListener(b -> {
            String sname = name.getText().toString().trim();
            String semail = email.getText().toString().trim();
            String spass = password.getText().toString();
            String sConfPass = confirmPassword.getText().toString();
            String sprivateAc = privateAccessCode.getText().toString().trim();
            String sStudentCode = teacherAccessCode.getText().toString().trim();
            if (sname.isEmpty()) {
                name.setError("Name Required");
                return;
            }
            if (semail.isEmpty() || !semail.contains("@")) {
                email.setError("Email Required");
                return;
            }
            if (spass.isEmpty() || sConfPass.isEmpty()) {
                password.setError("Password Required");
                confirmPassword.setError("Doesn't Match");
                return;
            }
            if (!spass.equals(sConfPass) || spass.length() < 6) {
                confirmPassword.setError("Doesn't Match");
                return;
            }
            if (sprivateAc.isEmpty()) {
                privateAccessCode.setError("Required");
                return;
            }
            if (sStudentCode.isEmpty() || sStudentCode.length() < 4) {
                teacherAccessCode.setError("Required");
                return;
            }
            attemptSignUp(sname, semail, spass, sConfPass, sprivateAc, sStudentCode);
        });

        return v;
    }

    private void attemptSignUp(String sname, String semail, String spass,
                               String sConfPass, String sprivateAc, String sStudentCode) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("Please Wait");
        dialog.setMessage("Signing Up...");
        dialog.setCancelable(false);
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", (dialog1, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        dialog.show();
        FirebaseDatabase.getInstance().getReference("AccessCodes").child("TeacherAC")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer isCorrect = dataSnapshot.getValue(Integer.class);
                            Toast.makeText(getContext(), String.valueOf(isCorrect), Toast.LENGTH_SHORT).show();
                            if (isCorrect == null) {
                                dialog.cancel();
                                dialog.dismiss();
                                return;
                            }
                            if (sStudentCode.equals(String.valueOf(isCorrect))) {
                                Toast.makeText(getContext(), "inside if equal", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(semail, spass).addOnCompleteListener(getActivity(), task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "task succe", Toast.LENGTH_SHORT).show();
                                        Teacher teacher = new Teacher(sname,  semail, false, sprivateAc, null);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child("Teachers")
                                                .child(task.getResult().getUser().getUid()).setValue(teacher);
                                        FirebaseAuth.getInstance().signOut();
                                        dialog.cancel();
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "SignUp Successful please Proceed to Log In", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            dialog.cancel();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Something Went Wrong..Please Contact Admin.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

    }

}
