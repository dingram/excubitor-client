package com.dmidroid.ingress.excubitor;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

  @Override
  protected void onError(Context context, String errorId) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onMessage(Context context, Intent intent) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onRegistered(Context context, String regid) {
    // TODO Auto-generated method stub
    // send regid to server to register
  }

  @Override
  protected void onUnregistered(Context context, String regid) {
    // TODO Auto-generated method stub
    // send regid to server to unregister
  }

}
