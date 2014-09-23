package MyUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class DownloadAPK {

	private Activity activity;
	ProgressDialog pDialog;

	public void show_download_popup(Activity activity_, String link, String name) {
		activity = activity_;
		this.pDialog = new ProgressDialog(activity);
		this.pDialog.setMessage("Đang tải file...");
		this.pDialog.setIndeterminate(false);
		this.pDialog.setMax(100);
		this.pDialog.setProgressStyle(1);
		this.pDialog.setCancelable(true);
		this.pDialog.setCanceledOnTouchOutside(false);
		new DownloadFileFromURL().execute(link, name);
	}

	class DownloadFileFromURL extends AsyncTask<String, String, String> {
		File file;

		DownloadFileFromURL() {
		}

		protected String doInBackground(String[] paramArrayOfString) {
			pDialog.show();
			try {
				URL localURL = new URL(paramArrayOfString[0]);
				URLConnection localURLConnection = localURL.openConnection();
				localURLConnection.connect();
				int i = localURLConnection.getContentLength();
				BufferedInputStream localBufferedInputStream = new BufferedInputStream(
						localURL.openStream(), 8192);
				File localFile = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ "/NewYear2014");
				if (!localFile.exists())
					localFile.mkdirs();
				this.file = new File(localFile, paramArrayOfString[1]);
				FileOutputStream localFileOutputStream = new FileOutputStream(
						this.file);
				byte[] arrayOfByte = new byte[1024];
				long l = 0L;
				while (true) {
					int j = localBufferedInputStream.read(arrayOfByte);
					if (j == -1) {
						localFileOutputStream.flush();
						localFileOutputStream.close();
						localBufferedInputStream.close();
						break;
					}
					l += j;
					String[] arrayOfString = new String[1];
					arrayOfString[0] = 100L * l / i + "";
					publishProgress(arrayOfString);
					localFileOutputStream.write(arrayOfByte, 0, j);
				}
			} catch (Exception localException) {
				Log.e("Error: ", localException.getMessage());
			}
			return null;
		}

		protected void onPostExecute(String paramString) {
			super.onPostExecute(paramString);
			pDialog.dismiss();
			try {
				Intent localIntent = new Intent("android.intent.action.VIEW");
				localIntent.setDataAndType(Uri.fromFile(this.file),
						"application/vnd.android.package-archive");
				activity.startActivity(localIntent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected void onProgressUpdate(String[] paramArrayOfString) {
			super.onProgressUpdate(paramArrayOfString);
			pDialog.setProgress(Integer.parseInt(paramArrayOfString[0]));
		}
	}
}
