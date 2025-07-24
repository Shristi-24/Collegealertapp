package com.example.collegealertapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

/**
 * Activity that allows users to add a new event to the Firebase Realtime Database.
 * Users must provide a title, description and date.  If any field is missing,
 * a toast prompts them to fill in all fields.  On successful submission the
 * activity finishes and a success message is shown.
 */
public class AddEventActivity extends AppCompatActivity {
    private EditText inputTitle, inputDesc, inputDate;
    private Button btnSubmit, btnBack;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_add_event);

        // Setup back button to simply finish this activity
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        inputTitle = findViewById(R.id.inputTitle);
        inputDesc  = findViewById(R.id.inputDesc);
        inputDate  = findViewById(R.id.inputDate);
        btnSubmit  = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            String desc  = inputDesc.getText().toString().trim();
            String date  = inputDate.getText().toString().trim();
            if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // Create a map representing the event data
            HashMap<String,String> ev = new HashMap<>();
            ev.put("title", title);
            ev.put("description", desc);
            ev.put("date", date);

            // Push the event to the "events" node in Firebase
            FirebaseDatabase.getInstance().getReference("events")
                    .push().setValue(ev)
                    .addOnSuccessListener(a -> {
                        Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show()
                    );
        });
    }
}
