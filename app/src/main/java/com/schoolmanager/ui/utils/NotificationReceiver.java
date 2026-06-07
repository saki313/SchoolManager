package com.schoolmanager.ui.utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String matiere = intent.getStringExtra("matiere");
        String salle = intent.getStringExtra("salle");
        String heure = intent.getStringExtra("heure");
        NotificationHelper.envoyerNotification(context, matiere, salle, heure);
    }
}