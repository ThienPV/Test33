package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import GCM.NotificationController;
import GCM.SendGCM;
import MyUtil.Constrant;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
public class GCMIntentService extends GCMBaseIntentService {

	@Override
	  protected String[] getSenderIds(Context context) {
	     String[] ids = new String[1];
	     ids[0] = "LoiRoi";
	     return ids;
	  }
	@SuppressWarnings("hiding")
	private static final String TAG = "GCMIntentService";

	@Override
	public void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		String PACKAGE_NAME = getApplicationContext().getPackageName();
		SendGCM.sendgcm(registrationId+"", PACKAGE_NAME, Constrant.SUBID);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String payload = intent.getStringExtra("payload");
		Log.i(TAG, "payload: " + payload);
		new NotificationController(getApplicationContext(), payload).execute();
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i(TAG, "Device unregistered: regId = " + arg1);
	}
}
