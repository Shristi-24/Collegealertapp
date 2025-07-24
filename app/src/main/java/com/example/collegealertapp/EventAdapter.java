package com.example.collegealertapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * RecyclerView.Adapter implementation for displaying a list of events.  Each
 * view holder binds the title, description and date of an event to the
 * corresponding TextViews in the event_item layout.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final List<Event> eventList;

    public EventAdapter(List<Event> list) {
        eventList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event e = eventList.get(position);
        holder.title.setText(e.getTitle());
        holder.description.setText(e.getDescription());
        holder.date.setText(e.getDate());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView description;
        final TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            title       = itemView.findViewById(R.id.textTitle);
            description = itemView.findViewById(R.id.textDescription);
            date        = itemView.findViewById(R.id.textDate);
        }
    }
}
