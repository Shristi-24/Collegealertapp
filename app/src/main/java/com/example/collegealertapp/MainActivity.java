// File: MainActivity.java
package com.example.collegealertapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerEvents;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private Button btnAddEvent, btnBack;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        // ✅ Ask for POST_NOTIFICATIONS permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        // ✅ Print FCM token to Logcat for testing
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("FCM", "FCM Token: " + token);
                });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(v ->
                startActivity(new Intent(this, AddEventActivity.class))
        );

        recyclerEvents = findViewById(R.id.recyclerEvents);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));
        eventList    = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        recyclerEvents.setAdapter(eventAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                eventList.clear();
                for (DataSnapshot snap : ds.getChildren()) {
                    Event e = snap.getValue(Event.class);
                    if (e != null) eventList.add(e);
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError err) { }
        });
    }
}