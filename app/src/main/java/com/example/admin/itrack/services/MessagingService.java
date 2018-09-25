package com.example.admin.itrack.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.admin.itrack.R;
import com.example.admin.itrack.activity.HomeActivity;
import com.example.admin.itrack.activity.NotificationDetails;
import com.example.admin.itrack.utils.KeyConfig;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MessagingService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("title"), remoteMessage.getData().get("image_url"));
    }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showNotification(String message, String title, String image_url) {

        Intent i = new Intent(this, NotificationDetails.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //You should use an actual ID instead
        int notificationId = new Random().nextInt(60000);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,notificationId,i,PendingIntent.FLAG_UPDATE_CURRENT);



        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = getBitmapfromUrl(image_url);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(notificationId,builder.build());
    }



}
