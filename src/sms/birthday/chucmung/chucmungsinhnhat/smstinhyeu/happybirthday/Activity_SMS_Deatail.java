package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import MyUtil.Constrant;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.google.analytics.tracking.android.EasyTracker;
import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;

public class Activity_SMS_Deatail extends Activity implements AdListener {

	private InterstitialAd interstitial;
	private LayoutInflater inflater = null;
	private TelephonyManager telephonyManager;
	private ConnectivityManager connectivityManager;
	private Bundle receiveBundle;
	private int TYPE, INDEX;
	private TextView textView1, textView_Mau_so;
	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_sms_detail);

		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		receiveBundle = this.getIntent().getExtras();
		TextView textViewBanner = (TextView) findViewById(R.id.banner_text);
		textView1 = (TextView) findViewById(R.id.ac_sms_detail_content);
		button = (Button) findViewById(R.id.ac_sms_detail_button);

		ImageView button_next = (ImageView) findViewById(R.id.ac_sms_detail_next);
		ImageView button_pre = (ImageView) findViewById(R.id.ac_sms_detail_pre);
		textView_Mau_so = (TextView) findViewById(R.id.ac_sms_detail_mau_so);
		TYPE = receiveBundle.getInt("TYPE");
		INDEX = receiveBundle.getInt("INDEX");
		textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
		button_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CharSequence charSequence = "";
				switch (TYPE) {
				case 1:
					if (INDEX < Constrant.noidung20_10.length - 1) {
						INDEX++;
						textView1.setText(Constrant.noidung20_10[INDEX].trim());
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				case 2:
					if (INDEX < Constrant.SMS_ENG_20_10.length - 1) {
						INDEX++;
						textView1.setText(Constrant.SMS_ENG_20_10[INDEX].trim());
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				case 3:
					if (INDEX < Constrant.SMS_KUTE.length - 1) {
						INDEX++;
						textView1.setText(Constrant.SMS_KUTE[INDEX]);
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				}
			}
		});
		button_pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CharSequence charSequence = "";
				switch (TYPE) {
				case 1:
					if (INDEX > 0) {
						INDEX--;
						textView1.setText(Constrant.noidung20_10[INDEX]);
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				case 2:
					if (INDEX > 0) {
						INDEX--;
						textView1.setText(Constrant.SMS_ENG_20_10[INDEX].trim());
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				case 3:
					if (INDEX > 0) {
						INDEX--;
						textView1.setText(Constrant.SMS_KUTE[INDEX]);
						textView_Mau_so.setText("Mẫu số " + (INDEX + 1));
					}
					break;
				}
			}
		});
		CharSequence charSequence = "";
		switch (TYPE) {
		case 1:
			textViewBanner.setText("Tin nhắn tiếng Việt");
			textView1.setText(Constrant.noidung20_10[INDEX].trim());
			break;
		case 2:
			textViewBanner.setText("Tin nhắn tiếng Anh");
			textView1.setText(Constrant.SMS_ENG_20_10[INDEX].trim());
			break;
		case 3:
			textViewBanner.setText("Tin nhắn hình vui");
			textView1.setText(Constrant.SMS_KUTE[INDEX]);
			break;
		}
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent smsIntent = new Intent(Intent.ACTION_VIEW);
				smsIntent.setType("vnd.android-dir/mms-sms");
				switch (TYPE) {
				case 1:
					showChooseSMS();
					break;
				case 2:
					String string[] = Constrant.SMS_ENG_20_10[INDEX]
							.split("Lời dịch:");
					smsIntent.putExtra("sms_body", string[0].trim());
					startActivity(smsIntent);
					break;
				case 3:
					showChooseSMS2();
					break;
				}
			}
		});
		if (Constrant.show_amob % 2 == 1) {
			interstitial = new InterstitialAd(this, getResources().getString(
					R.string.ad_unit_id));
			AdRequest adRequest2 = new AdRequest();
			interstitial.loadAd(adRequest2);
			interstitial.setAdListener(this);
		}
		Constrant.show_amob++;
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

	private void showChooseSMS() {
		AlertDialog.Builder alertG = new AlertDialog.Builder(this);
		alertG.setTitle("Mời bạn chọn loại tin nhắn:");
		CharSequence[] arrChar = { "Tiếng Việt có dấu", "Tiếng Việt không dấu" };
		alertG.setSingleChoiceItems(arrChar, 0,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent smsIntent = new Intent(Intent.ACTION_VIEW);
							smsIntent.setType("vnd.android-dir/mms-sms");
							smsIntent.putExtra("sms_body",
									Constrant.noidung20_10[INDEX].trim());
							startActivity(smsIntent);
							break;
						case 1:
							Intent smsIntent2 = new Intent(Intent.ACTION_VIEW);
							smsIntent2.setType("vnd.android-dir/mms-sms");
							smsIntent2.putExtra(
									"sms_body",
									tienich360.com.Utils
											.toUnsignString(Constrant.noidung20_10[INDEX]
													.trim()));
							startActivity(smsIntent2);
							break;
						}
						dialog.dismiss();
					}
				});
		alertG.show();
	}

	private void showChooseSMS2() {
		AlertDialog.Builder alertG = new AlertDialog.Builder(this);
		alertG.setTitle("Mời bạn chọn loại tin nhắn:");
		CharSequence[] arrChar = { "Tiếng Việt có dấu", "Tiếng Việt không dấu" };
		alertG.setSingleChoiceItems(arrChar, 0,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent smsIntent = new Intent(Intent.ACTION_VIEW);
							smsIntent.setType("vnd.android-dir/mms-sms");
							smsIntent.putExtra("sms_body",
									Constrant.SMS_KUTE[INDEX]);
							startActivity(smsIntent);
							break;
						case 1:
							Intent smsIntent2 = new Intent(Intent.ACTION_VIEW);
							smsIntent2.setType("vnd.android-dir/mms-sms");
							smsIntent2.putExtra(
									"sms_body",
									tienich360.com.Utils
											.toUnsignString(Constrant.SMS_KUTE[INDEX]));
							startActivity(smsIntent2);
							break;
						}
						dialog.dismiss();
					}
				});
		alertG.show();
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		if (arg0 == interstitial) {
			interstitial.show();
		}
	}
}
