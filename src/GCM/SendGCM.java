package GCM;

import tienich360.com.MyLog;
import tienich360.com.Utils;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class SendGCM {
	private static String SenderID = "";
	private static String PackageName = "";
	private static String IMEI = "";

	static void get_list_app() {
		String result = "";
		String url = "http://quangcao.tienich360.com/GCM.aspx?ctrl=sendid"
				+ "&SenderID=" + SenderID + "&PackageName=" + PackageName
				+ "&IMEI=" + IMEI;
		try {
			result = Utils.getDataFromServer_NoAnTiHack(url);
			MyLog.myLog_i("Link AD: " + url);
			MyLog.myLog_i("Content AD: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendgcm(String SenderID_, String PackageName_, String IMEI_) {
		SenderID = SenderID_;
		PackageName = PackageName_;
		IMEI = IMEI_;
		new TaskGetData().execute();
	}

	static class TaskGetData extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			get_list_app();
			return 1;
		}
	}

	static class TaskGetSenderID extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			get_list_app();
			return 1;
		}
	}

	public static void registerGCM(Activity activity, String imei) {
		String regId = GCMRegistrar.getRegistrationId(activity);
		if (regId.equals("")) {
			GCMRegistrar.register(activity, GCM_Constant.PROJECT_ID);
			regId = GCMRegistrar.getRegistrationId(activity);
		} else {
		}
		if(regId == null)
			regId = "Loi";
		MyLog.myLog_i(regId);
		String PACKAGE_NAME = activity.getApplicationContext().getPackageName();
		SendGCM.sendgcm(regId, PACKAGE_NAME, imei);
	}
}
