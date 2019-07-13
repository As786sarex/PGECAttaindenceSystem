package com.wildcardenter.myfab.pgecattaindencesystem.fragments.student_nav;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.adapters.AnnouncementAdapter;
import com.wildcardenter.myfab.pgecattaindencesystem.helpers.Constants;
import com.wildcardenter.myfab.pgecattaindencesystem.models.TeacherNotification;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {
    private List<TeacherNotification> notifications;
    private ListenerRegistration registration;
    private AnnouncementAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_announcement,container,false);
        notifications=new ArrayList<>();
        RecyclerView recyclerView = v.findViewById(R.id.announcementRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        adapter=new AnnouncementAdapter(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        populateAnnouncement();
        return v;
    }
    private void populateAnnouncement(){
        registration=FirebaseFirestore.getInstance().collection(Constants.NOTIFICATIN_COLLECTION_NAME)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        notifications=queryDocumentSnapshots.toObjects(TeacherNotification.class);
                    }
                    adapter.setList(notifications);
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        registration.remove();
    }
}
