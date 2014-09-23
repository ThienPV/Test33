package GCM;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.ActivitySplash;
import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;

public class NotificationController {
	private Context mcontext;
	private String JsonData;

	public NotificationController(Context context, String jsondata) {
		this.mcontext = context;
		this.JsonData = jsondata;
	}

	public void execute() {
		generateNotification(mcontext);
	}

	public void generateNotification(Context context) {
		try {
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context);
			builder.setAutoCancel(true);
			JSONObject json;
			json = new JSONObject(JsonData);
			String title = json.getString("Title");
			String message = json.getString("Content");
			String url = json.getString("Link");
			String Type = json.getString("Type");
			// String Packagename = json.getString("Packagename");
			builder.setContentTitle(title);
			builder.setContentText(message);
			builder.setSmallIcon(R.drawable.menu_icon_new);
			builder.setDefaults(Notification.DEFAULT_ALL);
			builder.setWhen(System.currentTimeMillis());
			Intent resultIntent = null;
			 if (Type.equals("0")) {
			
			 } else if (Type.equals("1")) {
			 resultIntent = new Intent(context, ActivitySplash.class);
			 } else if (Type.equals("2")) {
			 resultIntent = new Intent(Intent.ACTION_VIEW);
			 resultIntent.setData(Uri.parse(url));
			 }
//			resultIntent = new Intent(Intent.ACTION_VIEW);
//			resultIntent.setData(Uri.parse(url));
			// Intent resultIntent = new Intent(Intent.ACTION_VIEW);
			// Intent resultIntent = new Intent(context,
			// MainActivity.class);
			// resultIntent.setData(Uri.parse(url));

			resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent resultPendingIntent = PendingIntent.getActivity(
					context, 0, resultIntent, 0);

			builder.setContentIntent(resultPendingIntent);
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(new Random().nextInt(100),
					builder.build());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
