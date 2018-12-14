package com.foreverflightlogs.foreverflightlogs;

// credit to https://gist.github.com/jirawatee/85d4b46a89b9ae821b63c31f5d5189de and
// https://www.youtube.com/watch?time_continue=12&v=uuiuVDb2bug for most of the code below

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  /**
   * Called when message is received.
   *
   * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
   */
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    // Handle FCM messages here.
    // If the application is in the foreground handle both data and notification messages here.
    // Also if you intend on generating your own notifications as a result of a received FCM
    // message (firebase cloud messaging), here is where that should be initiated. See
    // sendNotification method below.
    RemoteMessage.Notification notification = remoteMessage.getNotification();
    Map<String, String> data = remoteMessage.getData();

    sendNotification(notification, data);
  }

  /**
   * Called if InstanceID token is updated. This may occur if the security of
   * the previous token had been compromised. Note that this is called when the InstanceID token
   * is initially generated so this is where you would retrieve the token.
   */
  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // Instance ID token to your app server.
    sendRegistrationToServer(token);
  }

  private void sendRegistrationToServer(String token) {
    // Implement this method to send token to your app server.
  }
  // [END on_new_token]

  /**
   * Create and show a custom notification containing the received FCM message.
   *
   * @param notification FCM notification payload received.
   * @param data FCM data payload received.
   */
  private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
        .setContentTitle(notification.getTitle())
        .setContentText(notification.getBody())
        .setAutoCancel(true)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setContentIntent(pendingIntent)
        .setContentInfo(notification.getTitle())
        .setLargeIcon(icon)
        .setColor(Color.RED)
        .setLights(Color.RED, 1000, 300)
        .setDefaults(Notification.DEFAULT_VIBRATE)
        .setSmallIcon(R.mipmap.ic_launcher);

    try {
      String picture_url = data.get("picture_url");
      if (picture_url != null && !"".equals(picture_url)) {
        URL url = new URL(picture_url);
        Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        notificationBuilder.setStyle(
            new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
        );
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Notification Channel is required for Android O and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(
          "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
      );
      channel.setDescription("channel description");
      channel.setShowBadge(true);
      channel.canShowBadge();
      channel.enableLights(true);
      channel.setLightColor(Color.RED);
      channel.enableVibration(true);
      channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
      notificationManager.createNotificationChannel(channel);
    }

    notificationManager.notify(0, notificationBuilder.build());
  }
}
