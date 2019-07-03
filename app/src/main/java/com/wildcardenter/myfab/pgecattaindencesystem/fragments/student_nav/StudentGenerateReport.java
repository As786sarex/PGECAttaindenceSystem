package com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;

import java.util.Map;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.STUDENT_SUBCODES_FILE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentGenerateReport extends Fragment {
    private SharedPref pref;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_generate_report, container, false);
        pref = SharedPref.getSharedPref(getContext(), STUDENT_SUBCODES_FILE);
        RadioGroup rgp = view.findViewById(R.id.studentReportGenRadioGroup);
        RadioGroup.LayoutParams rprms;
        Map<String, ?> col = pref.getAllvalues();
        if (col != null) {
            for (Object s : col.values()) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setTextSize(18f);
                radioButton.setText(s.toString());
                radioButton.setId(View.generateViewId());
                rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                rgp.addView(radioButton, rprms);
            }
        }
        view.findViewById(R.id.StudentGenerateSubReportBtn).setOnClickListener(v -> {

            RadioButton rb = view.findViewById(rgp.getCheckedRadioButtonId());
            if (rb != null) {
                Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
