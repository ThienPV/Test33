package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import java.util.ArrayList;

import vn.adflex.ads.AdFlexAds;
import vn.adflex.ads.AdFlexAdsPosition;
import Adapter.AdapterListItem;
import Data.SMS;
import MyUtil.Constrant;
import MyUtil.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class ActivityListItem extends Activity {

	private AdapterListItem arrayAdapter;
	private ListView listV;
	private Bundle receiveBundle;
	private int TYPE;
	private ArrayList<SMS> arrayM;
	AdFlexAds adFlexAds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_list);
		arrayM = new ArrayList<SMS>();
		arrayAdapter = new AdapterListItem(this, R.layout.listmusic, arrayM);
		listV = (ListView) findViewById(R.id.dung);
		receiveBundle = this.getIntent().getExtras();
		TextView textViewBanner = (TextView) findViewById(R.id.banner_text);
		TYPE = receiveBundle.getInt("TYPE");
		switch (TYPE) {
		case 1:
			textViewBanner.setText("Tin nhắn tiếng Việt");
			break;
		case 2:
			textViewBanner.setText("Tin nhắn tiếng Anh");
			break;
		case 3:
			textViewBanner.setText("Tin nhắn hình vui");
			break;
		}
		listV.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView parentView, View childView,
					int position, long id) {
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
		listV.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView parentView, View childView,
					int position, long id) {
				if (position < arrayM.size()) {
					Intent intern = null;
					intern = new Intent(ActivityListItem.this,
							Activity_SMS_Deatail.class);
					intern.putExtra("INDEX", position);
					intern.putExtra("TYPE", TYPE);
					startActivity(intern);
				}
			}
		});
		new TaskGetData().execute();

		if (Constrant.show_amob % 2 == 0 && Constrant.show_amob > 0) {
			adFlexAds = new AdFlexAds(this);
			adFlexAds.show(AdFlexAdsPosition.IN_APP);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!Utils.checkConnect(ActivityListItem.this, getBaseContext()))
			EasyTracker.getInstance(this).activityStart(this); // Add this
																// method.
	}

	@Override
	public void onStop() {
		super.onStop();
		if (!Utils.checkConnect(ActivityListItem.this, getBaseContext()))
			EasyTracker.getInstance(this).activityStop(this); // Add this
																// method.
	}

	// -------------------------------------------------------------------------------
	private class TaskGetData extends AsyncTask<Integer, Void, Integer> {
		private ProgressDialog dialog = new ProgressDialog(
				ActivityListItem.this);

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			arrayAdapter.setArrayList(arrayM);
			arrayAdapter.notifyDataSetChanged();
			listV.setAdapter(arrayAdapter);
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Đang tải dữ liệu...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			SMS sms;
			switch (TYPE) {
			case 1:
				for (int i = 0; i < Constrant.noidung20_10.length; i++) {
					sms = new SMS();
					sms.setContent(Constrant.noidung20_10[i].trim());
					arrayM.add(sms);
				}
				break;
			case 2:
				for (int i = 0; i < Constrant.SMS_ENG_20_10.length; i++) {
					sms = new SMS();
					sms.setContent(Constrant.SMS_ENG_20_10[i].trim());
					arrayM.add(sms);
				}
				break;
			case 3:
				for (int i = 0; i < Constrant.SMS_KUTE.length; i++) {
					sms = new SMS();
					sms.setContent(Constrant.SMS_KUTE[i]);
					arrayM.add(sms);
				}
				break;
			}

			return 1;
		}
	}
}
