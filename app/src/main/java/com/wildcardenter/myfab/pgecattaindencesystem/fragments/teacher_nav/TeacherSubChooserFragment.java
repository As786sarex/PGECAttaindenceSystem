package com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.activities.TeacherGeneratorActivity;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;

import java.util.Map;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.TEACHER_ALLOTED_SUBS;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSubChooserFragment extends Fragment {

    private SharedPref pref;
    private CharSequence[] chars;
    private int choice=-1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_teacher_sub_chooser_fragmnt, container, false);
        pref=SharedPref.getSharedPref(getContext(), TEACHER_ALLOTED_SUBS);

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);
        v.findViewById(R.id.GenerateQRCodeBtn).setOnClickListener(view->{
            Map<String, ?> mp=pref.getAllvalues();
            if (mp!=null) {
                chars=new CharSequence[mp.size()];
                int i=0;
                for (String s:mp.keySet()){
                    chars[i]=s;
                    i++;
                }
            }
            showListDialog(builder);
        });
        return v;
    }

    private void showListDialog(AlertDialog.Builder builder) {
        builder.setTitle("Choose a subject")
                .setIcon(R.mipmap.ic_launcher_round)
                .setSingleChoiceItems(chars, -1, (dialog, which) -> {
                    choice=which;
                })
                .setPositiveButton("Generate", (dialog, which) -> {
                    if (choice!=-1) {
                        Intent intent = new Intent(getContext(), TeacherGeneratorActivity.class);
                        intent.putExtra("Sub_code",chars[choice]);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(), "Please select a subject", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnDismissListener(dialog -> {
                    choice=-1;
                });
        builder.show();


    }


}
