package com.wildcardenter.myfab.pgecattaindencesystem.fragments.teacher_nav;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.SharedPref;
import com.wildcardenter.myfab.pgecattaindencesystem.models.TeacherNotification;

import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.LOGIN_TYPE_FILE;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.NAME;
import static com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants.TYPE_STRING;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNotificationFragment extends Fragment {
    MaterialEditText notiTitle,notiBody;
    Button sendButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_create_notification, container, false);
        notiTitle=v.findViewById(R.id.createNotificationTitle);

        notiBody=v.findViewById(R.id.createNotificationBody);

        sendButton=v.findViewById(R.id.sentNotificationButton);
        sendButton.setOnClickListener(view->{
            sendButton.setVisibility(View.GONE);
            if (notiTitle.getText().toString().isEmpty()||notiBody.getText().toString().isEmpty()){
                return;
            }
            String name= (String) SharedPref.getSharedPref(getContext(),LOGIN_TYPE_FILE).getData(NAME,TYPE_STRING);
            sendNotification(notiTitle.getText().toString()+" : "+name,notiBody.getText().toString());
        });

        return v;
    }

    private void sendNotification(String title, String body) {
        TeacherNotification notification=new TeacherNotification(title,body);
        FirebaseFirestore.getInstance().collection(Constants.NOTIFICATIN_COLLECTION_NAME).add(notification).addOnCompleteListener(task->{
            sendButton.setVisibility(View.VISIBLE);
            if (task.isSuccessful()){
                Toast.makeText(getContext(), "Notification sent Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "Failed to send notification", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
