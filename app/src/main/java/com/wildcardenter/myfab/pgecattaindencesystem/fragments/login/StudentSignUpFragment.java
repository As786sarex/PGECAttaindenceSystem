package com.wildcardenter.myfab.pgecattaindencesystem.fragments.login;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSignUpFragment extends Fragment {
    private MaterialEditText name,email,rollno,password,confirmPassword,privateAccessCode,studentAccessCode;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_student_sign_up, container, false);
        name=v.findViewById(R.id.studentSignUpName);
        email=v.findViewById(R.id.studentSignUpEmail);
        rollno=v.findViewById(R.id.studentSignUpRollNo);
        password=v.findViewById(R.id.studentSignUpPassword);
        confirmPassword=v.findViewById(R.id.studentSignUpConfPassword);
        privateAccessCode=v.findViewById(R.id.studentSignUpPrivateAC);
        studentAccessCode=v.findViewById(R.id.studentSignUpStudentAC);
        Button signUp = v.findViewById(R.id.button);
        signUp.setOnClickListener(view->{
            String sname=name.getText().toString().trim();
            String semail=email.getText().toString().trim();
            String srollNo=rollno.getText().toString().trim();
            String spass=password.getText().toString();
            String sConfPass=confirmPassword.getText().toString();
            String sprivateAc=privateAccessCode.getText().toString().trim();
            String sStudentCode=studentAccessCode.getText().toString().trim();
            if (sname.isEmpty()){
                name.setError("Name Required");
                return;
            }
            if (semail.isEmpty()){
                email.setError("Email Required");
                return;
            }
            if (srollNo.isEmpty()){
                rollno.setError("Roll Number Required");
                return;
            }
            if (spass.isEmpty()||sConfPass.isEmpty()){
                password.setError("Password Required");
                confirmPassword.setError("Doesn't Match");
                return;
            }
            if (!spass.equals(sConfPass)){
                confirmPassword.setError("Doesn't Match");
                return;
            }
            if (sprivateAc.isEmpty()){
                privateAccessCode.setError("Required");
                return;
            }
            if (sStudentCode.isEmpty())
            {
                studentAccessCode.setError("Required");
                return;
            }

            attemptLogin(sname,semail,srollNo,spass,sConfPass,sprivateAc,sStudentCode);

        });


        return v;
    }

    private void attemptLogin(String sname, String semail, String srollNo,
                              String spass, String sConfPass, String sprivateAc, String sStudentCode) {
        ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setTitle("Please Wait");
        dialog.setMessage("Signing Up...");
        dialog.setCancelable(false);
        dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel",(dialog1, which) -> {
            dialog.cancel();
            dialog.dismiss();
        });
        dialog.show();
        FirebaseDatabase.getInstance().getReference("AccessCodes").child("StudentAC")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Integer isCorrect=dataSnapshot.getValue(Integer.class);
                            Toast.makeText(getContext(), String.valueOf(isCorrect), Toast.LENGTH_SHORT).show();
                            if (isCorrect==null){
                                dialog.cancel();
                                dialog.dismiss();
                                return;
                            }
                            if (sStudentCode.equals(String.valueOf(isCorrect))){
                                Toast.makeText(getContext(), "inside if equal", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(getActivity(), task -> {
                                   if (task.isSuccessful()){
                                       Toast.makeText(getContext(), "task succe", Toast.LENGTH_SHORT).show();
                                       Student student=new Student(sname,srollNo,false,semail,Integer.valueOf(sprivateAc));
                                       FirebaseDatabase.getInstance().getReference("Users")
                                               .child("Students")
                                               .child(task.getResult().getUser().getUid()).setValue(student);
                                       FirebaseAuth.getInstance().signOut();
                                       dialog.cancel();
                                       dialog.dismiss();
                                       Toast.makeText(getContext(), "SignUp Successful please Proceed to Log In", Toast.LENGTH_SHORT).show();
                                   }
                                });
                            }
                        }
                        else{
                            dialog.cancel();
                            dialog.dismiss();
                            Toast.makeText(getContext(),"Something Went Wrong..Please Contact Admin.",
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
