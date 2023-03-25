package tr.com.emrecoban.dgsasistani;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Mr. SHEPHERD on 31.05.2017.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody()); // Mesaj içeriği alınıp bildirim gösteren metod çağırılıyor

    }

    private void showNotification(String message) {

        Intent i = new Intent(getApplicationContext(),AnaEkran.class); // Bildirime basıldığında hangi aktiviteye gidilecekse
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



        NotificationCompat.BigTextStyle Metin = new NotificationCompat.BigTextStyle();
        Metin.bigText(message);



        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        int color = getResources().getColor(R.color.colorPrimary);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(tr.com.emrecoban.dgsasistani.R.drawable.bildiri_) // Bildirim simgesi
                .setAutoCancel(true) // Kullanıcı bildirime girdiğinde otomatik olarak silinsin. False derseniz bildirim kalıcı olur.
                .setContentTitle("Dikey Geçiş Asistanı") // Bildirim başlığı
                .setContentText(message) // Bildirim mesajı
                .setDefaults(Notification.DEFAULT_ALL)
                .setColor(color)
                .setStyle(Metin)
                .setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }



}


