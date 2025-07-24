// File: MyFirebaseMessagingService.java
package com.example.collegealertapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Service that handles incoming Firebase Cloud Messaging messages.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage msg) {
        Log.d("FCM_RECEIVED", "Message received!");

        String t = msg.getNotification() != null
                ? msg.getNotification().getTitle() : "New Event";
        String b = msg.getNotification() != null
                ? msg.getNotification().getBody()  : "Check the app";

        Intent i = new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(
                this, 0, i,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        String ch = "college_alerts";
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, ch)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(t)
                .setContentText(b)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(new NotificationChannel(
                    ch, "Alerts", NotificationManager.IMPORTANCE_HIGH
            ));
        }
        nm.notify(0, nb.build());
    }
}
