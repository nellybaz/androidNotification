package com.example.notificationapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//    NotificationManager notificationManager;
    private String CHANNEL_ID = "com.example.notificationap";
    String textTitle = "How to create a push Notification";
    String textContent = "This push notification was created by Nelson, " +
            "following the official android documentation on how to create a push notification";

    String channel_name = "channel_name";
    String channel_description = "channel_description";
    int notificationId = 453453;

    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = channel_name;
            String description = channel_description;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            android.app.NotificationChannel channel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void sendNotification(View view) {
        android.content.Intent snoozeIntent = new android.content.Intent(this, MainActivity.class);
        snoozeIntent.setAction("ACTION_SNOOZE");
        snoozeIntent.putExtra("EXTRA_NOTIFICATION_ID", 0);


        String replyLabel = "Enter your text to reply";
        androidx.core.app.RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        // Build a PendingIntent for the reply action to trigger.
        android.app.PendingIntent replyPendingIntent =
                android.app.PendingIntent.getBroadcast(getApplicationContext(),
                        343,
                        snoozeIntent,
                        android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input.
        androidx.core.app.NotificationCompat.Action action =
                new androidx.core.app.NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground,
                        getString(R.string.label), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .addAction(action)
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT);

        androidx.core.app.NotificationManagerCompat notificationManager = androidx.core.app.NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}
