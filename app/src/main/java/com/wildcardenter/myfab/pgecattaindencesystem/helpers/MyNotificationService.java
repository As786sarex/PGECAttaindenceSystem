package com.wildcardenter.myfab.pgecattaindencesystem.helpers;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/*
    Class On Package com.wildcardenter.myfab.pgecattaindencesystem.helpers
    
    Created by Asif Mondal on 05-07-2019 at 19:59
*/


public class MyNotificationService extends FirebaseMessagingService {

    NotificationHelper helper;

    @Override
    public void onCreate() {
        super.onCreate();
        helper=new NotificationHelper(this);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        helper.createNotification(title,body);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper=null;
    }
}
