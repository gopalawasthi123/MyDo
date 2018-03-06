package com.example.gopalawasthi.mydo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context,"myapp");
        builder.setContentTitle("MyDo");
        builder.setContentText("your do is ready");
        builder.setVibrate(new long[] {800,800,800,800});
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setLights(Color.RED,3000,2000);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent intent1 = new Intent(context,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent1,0);
        builder.setContentIntent(pendingIntent);
        Notification notification =  builder.build();

        notificationManager.notify(1,notification);

    }

}
