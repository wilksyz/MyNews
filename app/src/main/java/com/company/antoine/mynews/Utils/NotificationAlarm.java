package com.company.antoine.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.company.antoine.mynews.Controlers.Activity.MainActivity;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;

public class NotificationAlarm extends BroadcastReceiver{

    CompositeDisposable disposable = new CompositeDisposable();
    Map<String,String> queryData = new HashMap<>();
    private String SAVE_TERM = "save term";
    private String SAVE_SECTION = "save section";
    private int mNumberArticle = 0;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        controlNewArticles(context);
        executeHttpRequestArticleSearch();
    }

    public void controlNewArticles(Context context){
        Date mDate = new Date();
        SimpleDateFormat string = new SimpleDateFormat("yyyyMMdd");
        String mDateString = string.format(mDate);

        String mQueryTerm = context.getSharedPreferences("My settings", MODE_PRIVATE).getString(SAVE_TERM, null);
        String mSection = context.getSharedPreferences("My settings", MODE_PRIVATE).getString(SAVE_SECTION, null);

        queryData.clear();
        queryData.put("begin_date", mDateString);
        queryData.put("q", mQueryTerm);
        queryData.put("fq", mSection);
    }

    private void executeHttpRequestArticleSearch(){
        disposable.add((Disposable) NYTimesStreams.streamFetchTimesArticleSearch(queryData).subscribeWith(getDisposable()));
    }

    private DisposableObserver<TimesArticleAPI> getDisposable(){
        return new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI mostPopular) {
                mNumberArticle = mostPopular.getResponse().getDocs().size();
                Log.e("TAG","nb article  "+mNumberArticle);
                sendNotification();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error Notification",e);
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete!!");
            }
        };
    }

    private void sendNotification(){
        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent1 = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent1, PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("My News");
        Log.e("TAG","Notif!!!!  ");
        inboxStyle.addLine("You have "+mNumberArticle+" new articles today!!!");

        // 3 - Create a Channel (Android 8)
        String channelId = "My channel ID";

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        .setSmallIcon(R.drawable.ic_announcement)
                        .setContentTitle("My News")
                        .setContentText("My News")
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message provenant de Firebase";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // 7 - Show notification
        notificationManager.notify("TAG", 120, notificationBuilder.build());
    }
}
