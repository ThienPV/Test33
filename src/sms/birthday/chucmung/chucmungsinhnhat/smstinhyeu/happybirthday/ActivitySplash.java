package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import tienich360.com.Utils;
import GCM.SendGCM;
import MyUtil.Constrant;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.view.Window;

import com.google.analytics.tracking.android.EasyTracker;

public class ActivitySplash extends Activity {

	private MainCountDown countdown;
	private TelephonyManager telephonyManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Constrant.SUBID = Utils.getIMEI(telephonyManager);
		countdown = new MainCountDown(2000, 1000);
		countdown.start();
		SendGCM.registerGCM(this, Constrant.SUBID);
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

	public class MainCountDown extends CountDownTimer {
		public MainCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			Intent intent = new Intent(ActivitySplash.this, Activity_Date.class);
			finish();
			startActivity(intent);
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}
