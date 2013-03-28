package com.dmidroid.ingress.excubitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

public class GCMIntentService extends GCMBaseIntentService {

  @Override
  protected void onError(Context context, String errorId) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onMessage(Context context, Intent intent) {
      String portal = intent.getStringExtra("portal");
      String item = intent.getStringExtra("item");
      int smallIcon = R.drawable.ic_launcher;
      if (item.equals("Shield")) {
          smallIcon = R.drawable.ic_stat_notify_shield_destroyed;
      } else if (item.equals("Resonator")) {
          smallIcon = R.drawable.ic_stat_notify_resonator_destroyed;
      } else if (item.equals("Link")) {
          smallIcon = R.drawable.ic_stat_notify_link_destroyed;
      } else if (item.equals("Field")) {
          smallIcon = R.drawable.ic_stat_notify_field_destroyed;
      }
      String by = intent.getStringExtra("by");
      String time = intent.getStringExtra("time");
      String lat = intent.getStringExtra("lat");
      String lng = intent.getStringExtra("lng");
      
      StringBuilder contentText = new StringBuilder();
      contentText.append("A ");
      contentText.append(item.toLowerCase(Locale.ENGLISH));
      contentText.append(" on ");
      contentText.append(portal);
      contentText.append(" was destroyed by ");
      contentText.append(by);
      contentText.append(" at ");
      contentText.append(time);

      NotificationCompat.Builder builder = 
              new NotificationCompat.Builder(context)
                  .setSmallIcon(smallIcon)
                  .setContentTitle(item + " destroyed")
                  .setContentText(contentText.toString())
                  .setVibrate(new long[] { 0, 300, 200, 300, 200 })
                  .setAutoCancel(true);
      
      NotificationManager notiManager = (NotificationManager) 
              context.getSystemService(Context.NOTIFICATION_SERVICE);
      notiManager.notify(0, builder.build());
  }

  @Override
  protected void onRegistered(final Context context, String regid) {
      SharedPreferences prefs = context.getSharedPreferences(
              "excubitor prefs", MODE_PRIVATE);
      String account = prefs.getString("account", "");
      
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost("http://www.yoursite.com/registration_script.php");

      try {
          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
          nameValuePairs.add(new BasicNameValuePair("regid", regid));
          nameValuePairs.add(new BasicNameValuePair("account", account));
          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          httpclient.execute(httppost);
          GCMRegistrar.setRegisteredOnServer(context, true);
      } catch (ClientProtocolException e) {
          // TODO Auto-generated catch block
      } catch (IOException e) {
          // TODO Auto-generated catch block
      }
  }

  @Override
  protected void onUnregistered(Context context, String regid) {
      if (GCMRegistrar.isRegisteredOnServer(context)) {
          HttpClient httpclient = new DefaultHttpClient();
          HttpPost httppost = new HttpPost("http://www.yoursite.com/unregistration_script.php");

          try {
              List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
              nameValuePairs.add(new BasicNameValuePair("regid", regid));
              httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
              httpclient.execute(httppost);
              GCMRegistrar.setRegisteredOnServer(context, false);
          } catch (ClientProtocolException e) {
              // TODO Auto-generated catch block
          } catch (IOException e) {
              // TODO Auto-generated catch block
          }          
      }
  }

}