package sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

public class Activity_About extends Activity {

	private LayoutInflater inflater;
	private TelephonyManager telephonyManager;
	private ConnectivityManager connectivityManager;
	private WebView webView;
	private int type, typemenu, index;
	private TextView bannertext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_content);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bannertext = (TextView) findViewById(R.id.banner_text);
		bannertext.setSelected(true);
		bannertext.setText("Nhịp sinh học là gì?");
		type = getIntent().getIntExtra("TYPEHOME", 0);
		typemenu = getIntent().getIntExtra("TYPEMENU", 0);
		index = getIntent().getIntExtra("INDEX", 0);
		webView = (WebView) findViewById(R.id.webview_new);
		webView.getSettings().setSupportZoom(true);
		new GetDataTask().execute();
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
	private class GetDataTask extends AsyncTask<Void, Void, Integer> {
		private ProgressDialog dialog = new ProgressDialog(Activity_About.this);

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				openAbout("file:///android_asset/a.html", true);
			} catch (Exception e) {
				e.printStackTrace();
				openAbout("file:///android_asset/a.html", true);
			}
			return 1;
		}

		@Override
		protected void onPreExecute() {
			dialog.setMessage("Đang tải dữ liệu...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result != null) {
			}
		}
	}

	public InputStream imageFetch(String source) throws MalformedURLException,
			IOException {
		URL url = new URL(source);
		Object o = url.getContent();
		InputStream content = (InputStream) o;
		return content;
	}

	public void openAbout(String strAboutPath, boolean bType) {
		WebView wvInfo = (WebView) findViewById(R.id.webview_new);
		if (bType == false) // about of dictionary
		{
			String strLine;
			StringBuilder sbInfo = new StringBuilder();
			File fInfo = new File(strAboutPath);
			if (fInfo.exists() && fInfo.length() < 8096) {
				try {
					BufferedReader br = new BufferedReader(
							new FileReader(fInfo));
					// Read File Line By Line
					while ((strLine = br.readLine()) != null) {
						sbInfo.append(strLine);
					}

					// Close the input stream
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
			}
			wvInfo.loadDataWithBaseURL(null, sbInfo.toString(), "text/html",
					"UTF-8", "about:blank");
		} else {
			wvInfo.loadUrl(strAboutPath);
		}
	}
}
