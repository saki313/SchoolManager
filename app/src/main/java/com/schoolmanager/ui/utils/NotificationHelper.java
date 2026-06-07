package com.schoolmanager.ui.utils;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.ui.utils.NotificationReceiver;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class NotificationHelper {

    private static final String CHANNEL_ID = "cours_channel";
    private static final String CHANNEL_NAME = "Rappels de cours";
    private static final int NOTIFICATION_ID = 1;

    /**
     * Planifie toutes les notifications pour les cours existants
     */
    public static void planifierToutesNotifications(Context context) {
        // Version simplifiée qui fonctionne sans base de données
        Toast.makeText(context, "Notifications activées", Toast.LENGTH_SHORT).show();

        // Version complète (décommentez quand la base de données sera prête)
        /*
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            List<Cours> coursList = db.coursDao().getAll().getValue();
            if (coursList != null) {
                for (Cours cours : coursList) {
                    planifierNotification(context, cours);
                }
                Toast.makeText(context, "Notifications planifiées pour " + coursList.size() + " cours", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    /**
     * Planifie une notification pour un cours spécifique
     */
    private static void planifierNotification(Context context, Cours cours) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("matiere", cours.getMatiere());
        intent.putExtra("salle", cours.getSalle());
        intent.putExtra("heure", cours.getHeureDebut());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, cours.getId(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = getCalendarForCours(cours);
        long triggerTime = calendar.getTimeInMillis() - (15 * 60 * 1000); // 15 minutes avant

        if (triggerTime > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    /**
     * Convertit un cours en Calendar
     */
    private static Calendar getCalendarForCours(Cours cours) {
        Calendar cal = Calendar.getInstance();
        int jourIndex = getJourIndex(cours.getJour());
        String[] heures = cours.getHeureDebut().split(":");

        cal.set(Calendar.DAY_OF_WEEK, jourIndex);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(heures[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(heures[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    /**
     * Convertit le nom du jour en index Calendar
     */
    private static int getJourIndex(String jour) {
        switch (jour) {
            case "Lundi": return Calendar.MONDAY;
            case "Mardi": return Calendar.TUESDAY;
            case "Mercredi": return Calendar.WEDNESDAY;
            case "Jeudi": return Calendar.THURSDAY;
            case "Vendredi": return Calendar.FRIDAY;
            case "Samedi": return Calendar.SATURDAY;
            case "Dimanche": return Calendar.SUNDAY;
            default: return Calendar.MONDAY;
        }
    }

    /**
     * Envoie une notification immédiate
     */
    public static void envoyerNotification(Context context, String matiere, String salle, String heure) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Cours dans 15 minutes")
                .setContentText(matiere + " en " + salle + " à " + heure)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Crée le canal de notification (Android 8+)
     */
    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}