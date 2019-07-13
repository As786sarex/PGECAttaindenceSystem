package com.wildcardenter.myfab.pgecattaindencesystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wildcardenter.myfab.pgecattaindencesystem.R;
import com.wildcardenter.myfab.pgecattaindencesystem.models.TeacherNotification;

import java.util.List;


/*
    Class On Package com.wildcardenter.myfab.pgecattaindencesystem.adapters
    
    Created by Asif Mondal on 10-07-2019 at 02:26
*/


public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<TeacherNotification> list;
    private Context context;

    public AnnouncementAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<TeacherNotification> list) {
        if (this.list!=null) {
            this.list.clear();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnnouncementViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.announcement_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.body.setText(list.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    protected class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.announceTitle);
            body=itemView.findViewById(R.id.announceBody);
        }
    }
}
