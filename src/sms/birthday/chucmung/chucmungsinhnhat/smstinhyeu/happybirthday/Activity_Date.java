package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import java.util.ArrayList;

import tienich360.com.ManageADPopup;
import vn.adflex.ads.AdFlexAds;
import vn.adflex.ads.AdFlexAdsPosition;
import vn.adflex.ads.AdFlexCallbackResponse;
import vn.adflex.ads.AdFlexCallbackResponseType;
import Adapter.AdapterKara;
import MyUtil.Constrant;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.jimmyphan.utils.LinkStore;
import com.jimmyphan.utils.ReadFileAssets;

public class Activity_Date extends Activity {
	AdFlexAds adFlexAds;
	private ListView listView;
	private AdapterKara adapterListItem;
	private ArrayList<String> arrayList;
	private boolean isPopup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_date);
		try {
			String loi_chuc = ReadFileAssets.readAssetsFile(
					getApplicationContext(), "loichuc.txt");
			Constrant.noidung20_10 = loi_chuc.split("phancach");

			String loi_chuc_tieng_anh = ReadFileAssets.readAssetsFile(
					getApplicationContext(), "tienganh.txt");
			Constrant.SMS_ENG_20_10 = loi_chuc_tieng_anh.split("phancach");

			String loi_chuc_kute = ReadFileAssets.readAssetsFile(
					getApplicationContext(), "kute.txt");
			Constrant.SMS_KUTE = loi_chuc_kute.split("phancach");

		} catch (Exception e) {
			e.printStackTrace();
		}

		TextView txt_header = (TextView) findViewById(R.id.banner_text);
		txt_header.setText(R.string.app_name);
		arrayList = new ArrayList<String>();
		arrayList.add(getResources().getString(R.string.menu_3_sms_loi_chuc));
		arrayList.add(getResources().getString(R.string.menu_4_sms_tieng_anh));
		arrayList.add(getResources().getString(R.string.menu_5_sms_kute));
		arrayList.add("Ứng dụng khác");
		adapterListItem = new AdapterKara(Activity_Date.this, arrayList);
		listView = (ListView) findViewById(R.id.list2);
		listView.setAdapter(adapterListItem);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parentView, View childView,
					int position, long id) {
				Intent intent = null;
				if (ManageADPopup.check_show(Activity_Date.this)) {
					ManageADPopup.showpopup(Activity_Date.this);
				} else {
					switch (position) {
					case 0:
						intent = new Intent(Activity_Date.this,
								ActivityListItem.class);
						intent.putExtra("TYPE", 1);
						startActivity(intent);
						break;
					case 1:
						intent = new Intent(Activity_Date.this,
								ActivityListItem.class);
						intent.putExtra("TYPE", 2);
						startActivity(intent);
						break;
					case 2:
						intent = new Intent(Activity_Date.this,
								ActivityListItem.class);
						intent.putExtra("TYPE", 3);
						startActivity(intent);
//						adFlexAds.show(AdFlexAdsPosition.IN_APP);
						break;
					case 3:
						try {
							intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri
									.parse("market://search?q=pub:GameApp+Free"));
							startActivity(intent);
						} catch (Exception e) {
						}
						break;
					}
				}
			}
		});
		ManageADPopup.manage_ad(Activity_Date.this, "giangsinh1", "thienpv",
				"giangsinh1", "giangsinh1");
		adFlexAds = new AdFlexAds(this);
//		adFlexAds.show(AdFlexAdsPosition.START_APP);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog_bluesea(Activity_Date.this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isPopup) {
			ManageADPopup.IsClick = "000";
			ManageADPopup.save_del(Activity_Date.this);
		}
		isPopup = true;
	}
	
	public void showExitDialog_bluesea(final Activity activity) {
		AlertDialog.Builder ab = new AlertDialog.Builder(activity);
		ab.setMessage("Bạn có chắc muốn thoát ứng dụng?");
		ab.setNeutralButton("Tải ứng dụng Free",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						try {
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri
									.parse("market://search?q=pub:GameApp+Free"));
							activity.startActivity(intent);
						} catch (Exception e) {
						}
					}
				});
		ab.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				adFlexAds.show(AdFlexAdsPosition.EXIT_APP, new AdFlexCallbackResponse() {

		            public void onClose(AdFlexCallbackResponseType afcrt) {
		            	finish();
		            }

		        });
			}
		});
		ab.setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		ab.show();
	}
}
