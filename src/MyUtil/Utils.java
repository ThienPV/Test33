package MyUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class Utils {
	static final int BUFFER_SIZE = 256;

	// static Context cont;

	/**
	 * Lay du lieu tu server theo dang string theo url de phan tich Du lieu tra
	 * ve kieu String
	 * */
	public String getDataFromServer(String url) {
		System.out.println("*******URL****:" + url);
		StringBuilder builder = new StringBuilder();
		URL updateURL;
		try {
			String html = "";
			updateURL = new URL(url);
			URLConnection conn = updateURL.openConnection();
			conn.setConnectTimeout(20000);
			InputStream is = conn.getInputStream();
			//
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
			// + conn.getContentLength());
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(BUFFER_SIZE);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			/* Convert the Bytes read to a String. */
			html = new String(baf.toByteArray(), "utf-8");
			builder.append(html);
			return builder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	/**
	 * 
	 * @param year
	 * @return -3 : Chuoi nam sinh chua co -2 : nam sinh lon hon 2100 hoac nho
	 *         hon 1900 -1 : co loi khi chuyen 0 : ok
	 */
	public static Integer checkYear(String year) {
		Integer result = 0;
		if (year.length() < 1) {
			return -3;
		}
		try {
			result = Integer.parseInt(year.trim());
			if (result > 2100 | result < 1900) {
				result = -2;
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = -1;
		}
		return result;
	}

	/**
	 * 
	 * @param male
	 * @return 0 : Nam 1: Nu
	 */
	public static Integer checkMale(Boolean male) {
		Integer string = -1;
		if (male) {
			string = 0;
		} else {
			string = 1;
		}
		return string;
	}

	/**
	 * 
	 * @param date
	 * @param month
	 * @param year
	 * @return -2 : chua nhap ngay hoac nhap khong dung -3 : chua nhap thang
	 *         hoac nhap khong dung -4 : chua nhap nam hoac nhap khong dung -5:
	 *         loi du lieu vao hoac nhap khong dung 0 : ok
	 */
	public static Integer checkBirthday(String date, String month, String year) {
		// System.out.println("Ngay thang sinh:"+date+";"+month+";"+year);
		Integer result = 0;
		int year1 = 0, month1 = 0, day = 0;
		boolean isOk = false;
		try {
			if (date.length() < 1) {
				result = -2;
				isOk = true;
				return result;
			} else {
				day = Integer.parseInt(date);
			}
			if (month.length() < 1) {
				result = -3;
				isOk = true;
				return result;
			} else {
				month1 = Integer.parseInt(month);
			}
			if (year.length() < 1) {
				result = -4;
				isOk = true;
				return result;
			} else {
				year1 = Integer.parseInt(year);
			}
		} catch (Exception e) {
			isOk = true;
			result = -5;
			// System.out.println("Chuyen sang kieu nguyen sai roi");
			return result;
		}
		// if ((year1 % 4 != 0 & month1 == 2)) {
		// }
		if (!isOk) {
			if (year1 < 1900 || year1 > 2100) {
				result = -4;
				return result;
			}
			if (day < 1 | day > 31) {
				result = -2;
				return result;
			}
			if (month1 < 1 | month1 > 12) {
				result = -3;
				return result;
			}
			if (month1 == 4 | month1 == 6 | month1 == 9 | month1 == 11) {
				if (day == 31) {
					result = -2;
					return result;
				}
			}
			if (month1 == 2) {
				if (!((year1 % 4) == 0)) {
					if (day > 28) {
						result = -2;
						// System.out.println("Vao nam 1");
						return result;
					}
				} else {
					if (day > 29) {
						// System.out.println("Vao nam 2");
						result = -2;
						return result;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Tra ve chuoi tieng viet khong dau tu chuoi tieng viet co dau
	 * 
	 * @param str
	 * @return
	 */

	// chuyen tieng viet co dau thanh tieng viet khog dau
	public static String toUnsignString(String str) {
		char[] chararray = str.toCharArray();
		for (int i = 0; i < chararray.length; i++) {
			int j;
			if ((j = tiengvietcodau.indexOf(chararray[i])) > -1)
				chararray[i] = tiengvietkhongdau.charAt(j);
		}
		return String.valueOf(chararray);
	}

	private final static String tiengvietcodau = "\341\340\u1EA3\343\u1EA1\u0103\u1EAF\u1EB1\u1EB3\u1EB5\u1EB7\342\u1EA5\u1EA7\u1EA9\u1EAB\u1EAD\351\350\u1EBB\u1EBD\u1EB9\352\u1EBF\u1EC1\u1EC3\u1EC5\u1EC7\355\354\u1EC9\u0129\u1ECB\363\362\u1ECF\365\u1ECD\364\u1ED1\u1ED3\u1ED5\u1ED7\u1ED9\u01A1\u1EDB\u1EDD\u1EDF\u1EE1\u1EE3\372\371\u1EE7\u0169\u1EE5\u01B0\u1EE9\u1EEB\u1EED\u1EEF\u1EF1\375\u1EF3\u1EF7\u1EF9\u1EF5\u0111\301\300\u1EA2\303\u1EA0\u0102\u1EAE\u1EB0\u1EB2\u1EB4\u1EB6\302\u1EA4\u1EA6\u1EA8\u1EAA\u1EAC\311\310\u1EBA\u1EBC\u1EB8\312\u1EBE\u1EC0\u1EC2\u1EC4\u1EC6\315\314\u1EC8\u0128\u1ECA\323\322\u1ECE\325\u1ECC\324\u1ED0\u1ED2\u1ED4\u1ED6\u1ED8\u01A0\u1EDA\u1EDC\u1EDE\u1EE0\u1EE2\332\331\u1EE6\u0168\u1EE4\u01AF\u1EE8\u1EEA\u1EEC\u1EEE\u1EF0\335\u1EF2\u1EF6\u1EF8\u1EF4\u0110";
	private final static String tiengvietkhongdau = "aaaaaaaaaaaaaaaaaeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyydAAAAAAAAAAAAAAAAAEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYD";

	// private TelephonyManager telephonyManager;
	// private ConnectivityManager connectivityManager;
	// ham kiem tra may co ket noi internet hay khong?
	public static boolean isConnected(TelephonyManager telephonyManager,
			ConnectivityManager connectivityManager) {
		if (telephonyManager == null || connectivityManager == null) {
			return false;
		}
		boolean roaming = telephonyManager.isNetworkRoaming();
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		return info != null && info.isConnected() && !roaming;
	}

	public static String getIMEI(TelephonyManager telephonyManager) {
		String emei = "";
		emei = telephonyManager.getDeviceId() + "";
		return emei;
	}

	public static boolean checkConnect(Activity ac, Context ctx) {
		ConnectivityManager conMan = (ConnectivityManager) ac
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// mobile
		NetworkInfo mobile = conMan
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		// Wife
		NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		System.out.println("check wifi:" + wifi.isAvailable() + ";"
				+ wifi.isConnected());
		System.out.println("check 3g:" + mobile.isAvailable() + ";"
				+ mobile.isConnected());
		if (wifi.isConnected()) {
			return true;
		} else if (mobile.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	// public static void showConfirm(String string, Activity activity,
	// LayoutInflater inflater, OnClickListener click, byte type) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	// View viewConfirm = inflater.inflate(
	// topteen.Activity.R.layout.util_popup_dialog, null);
	// final TextView mes = (TextView) viewConfirm
	// .findViewById(R.id.popup_msg);
	// builder.setView(viewConfirm);
	// builder.setTitle("");
	// final AlertDialog dialog = builder.create();
	// // dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
	// dialog.show();
	// mes.setText(string);
	// Button okConfirm = (Button) viewConfirm.findViewById(R.id.popup_accept);
	// okConfirm.setWidth(100);
	// okConfirm.setOnClickListener(click);
	// Button cancelConfirm = (Button) viewConfirm
	// .findViewById(R.id.popup_cancel);
	// if (type == 1) {
	// cancelConfirm.setVisibility(View.GONE);
	// } else {
	// cancelConfirm.setOnClickListener(new OnClickListener() {
	//
	// // @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// }
	// });
	// }
	// }

	// public static void showConfirm(String string, Activity activity,
	// LayoutInflater inflater, OnClickListener click,
	// OnClickListener clickCancel) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	// View viewConfirm = inflater.inflate(R.layout.util_popup_dialog, null);
	// final TextView mes = (TextView) viewConfirm
	// .findViewById(R.id.popup_msg);
	// builder.setView(viewConfirm);
	// builder.setTitle("");
	// final AlertDialog dialog = builder.create();
	// // dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
	// dialog.show();
	//
	// mes.setText(string);
	// Button okConfirm = (Button) viewConfirm.findViewById(R.id.popup_accept);
	// okConfirm.setWidth(100);
	// okConfirm.setOnClickListener(click);
	// Button cancelConfirm = (Button) viewConfirm
	// .findViewById(R.id.popup_cancel);
	// cancelConfirm.setOnClickListener(clickCancel);
	// }

//	public static void showConfirm(String string, Activity activity,
//			LayoutInflater inflater) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		View viewConfirm = inflater.inflate(R.layout.util_popup_dialog, null);
//		final TextView mes = (TextView) viewConfirm
//				.findViewById(R.id.popup_msg);
//		builder.setView(viewConfirm);
//		builder.setTitle("");
//		final AlertDialog dialog = builder.create();
//		// dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
//		dialog.show();
//
//		mes.setText(string);
//		Button okConfirm = (Button) viewConfirm.findViewById(R.id.popup_accept);
//		okConfirm.setWidth(100);
//		Button cancelConfirm = (Button) viewConfirm
//				.findViewById(R.id.popup_cancel);
//		cancelConfirm.setVisibility(View.GONE);
//		okConfirm.setOnClickListener(new OnClickListener() {
//
//			// @Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//	}

	private static SharedPreferences mShareRefs = null;

	// private static String SUBID_KEY = "subid";

	/**
	 * Luu subid vao may, tat may van giu dc thong tin
	 * 
	 * @param subid
	 * @param ac
	 */
	// luu subid
	public static void saveSubid(String subid_key, String subid, Activity ac) {
		try {
			mShareRefs = ac.getSharedPreferences(subid_key,
					Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mShareRefs.edit();
			// put du lieu vao editor
			editor.putString(subid_key, subid);
			// luu editor
			editor.commit();
			System.out.println("Luu vao thanh cong:" + subid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Luu ko thanh cong:");
		}
	}

	/**
	 * lay thong tin subid luu trong may ra
	 * 
	 * @param ac
	 * @return
	 */
	// lay subid
	public static String getSubid(String subid_key, Activity ac) {
		String subID = "";
		mShareRefs = ac.getSharedPreferences(subid_key, Activity.MODE_PRIVATE);
		subID = mShareRefs.getString(subid_key, "");
		// mShareRefs.
		return subID;
	}

	/**
	 * tra anh tu link http
	 * 
	 * @param ctx
	 * @param url
	 * @return
	 */
	public static Drawable getImageHTTP(Context ctx, String url) {
		try {
			InputStream is = (InputStream) fetch(url.replace(" ", "%20"));
			// URL updateURL = new URL(url);
			// URLConnection conn = updateURL.openConnection();
			// InputStream is1 = conn.getInputStream();
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  duong dan: "+url+
			// "        "+Runtime.getRuntime().freeMemory());
			Drawable d = Drawable.createFromStream(is, "src");
			// d= resize(d,90,130);
			// System.out.println("Do dai anh lay ve:"+d.getIntrinsicHeight()+";"+d.getIntrinsicWidth());
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError ot) {
			ot.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Drawable getImageHTTP(Context ctx, String url, int width,
			int hight) {
		try {
			InputStream is = (InputStream) fetch(url.replace(" ", "%20"));
			// URL updateURL = new URL(url);
			// URLConnection conn = updateURL.openConnection();
			// InputStream is1 = conn.getInputStream();
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  duong dan: "+url+
			// "        "+Runtime.getRuntime().freeMemory());
			Drawable d = Drawable.createFromStream(is, "src");
			d = resize(d, width, hight);
			// System.out.println("Do dai anh lay ve:"+d.getIntrinsicHeight()+";"+d.getIntrinsicWidth());
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError ot) {
			ot.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * dung trong ham lay anh tu link http
	 * 
	 * @param address
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static Object fetch(String address) throws MalformedURLException,
			IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}

	/**
	 * lay do dai cua 1 chuoi String, dung trong ham cat chuoi theo do dai
	 * 
	 * @param string
	 * @param edit
	 * @return
	 */
	public static int getStringLength(String string, TextView edit) {
		Rect bounds = new Rect();
		Paint textPaint = edit.getPaint();
		textPaint.getTextBounds(string, 0, string.length(), bounds);
		return bounds.width();
	}

	/**
	 * ham nay de cat chuoi theo do dai, chua hoan hao lam, da dung trong ung
	 * dung xongchao
	 * 
	 * @param string
	 * @param lenght
	 * @param edit
	 * @return
	 */
	public static String subStringByLength(String string, int lenght,
			TextView edit) {

		if (getStringLength(string, edit) <= lenght) {
			return string;
		} else {
			int lengthTotal = getStringLength(string, edit) - lenght;
			String result = "";
			String[] stringslipbyspace = string.split(" ");
			if (stringslipbyspace.length > 0) {
				int index = 0;
				for (int i = stringslipbyspace.length - 1; i >= 0; i--) {
					result += stringslipbyspace[stringslipbyspace.length - 1]
							+ " ";
					if (getStringLength(result, edit) >= lengthTotal) {
						index = i;
						i = -3;
						break;
					}
				}
				result = "";
				System.out.println("Index:" + index + ";do dai mang:"
						+ stringslipbyspace.length);
				for (int i = 0; i < index; i++) {
					result += stringslipbyspace[i] + " ";
				}
				System.out.println("Chuoi cat dc:" + result);
			} else {
				result = string;
			}
			return result;
		}
	}

	/**
	 * ham nay con loi ra ky tu dac biet giua cac ky tu trong file
	 * 
	 * @param activity
	 * @param fileNam
	 * @return
	 */
	public static String readFileTEXT(Activity activity, String fileNam) {
		String result = "";
		try {
			InputStream input = activity.getAssets().open(fileNam);
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			result = new String(buffer);
			result = result.replace("\r\n", "");
			result = result.replace("|", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Ham nay chua chay dc do chua chinh dc duong dan toi file can doc
	 * 
	 * @param activity
	 * @param fileNam
	 * @return
	 */
	public static String readFileTEXT2(Activity activity, String fileNam) {
		String result = "";
		FileInputStream fis;
		final StringBuffer storedString = new StringBuffer();

		try {
			fis = activity.openFileInput(fileNam);
			DataInputStream dataIO = new DataInputStream(fis);
			String strLine = null;

			if ((strLine = dataIO.readLine()) != null) {
				storedString.append(strLine);
			}

			dataIO.close();
			fis.close();
			result = storedString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * ham nay dung de doc file text, da thanh cong, can co file text dung file
	 * mau
	 * 
	 * @param activity
	 * @param filename
	 *            file mau: ten: conf.txt noi dung: #khoi tao versionid hien
	 *            thoi #Tue Apr 12 23:47:35 ICT 2011 versionid=1 partner=tien
	 *            host=http://210.245.94.141:8585/
	 */
	// public static void readConfig(Activity activity, String filename) {
	// try {
	// Properties properties = new Properties();
	// InputStream input = activity.getAssets().open(filename);
	// properties.load(input);
	// input.close();
	// String partner = properties.getProperty("partner", "tien2");
	// String host = properties.getProperty("host", "tien2");
	// // Constrant.PARTNER = partner.trim();
	// // Constrant.DOMAIN = host.trim();
	// // System.out.println("********host********=" + host);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

	// /lay du lieu json tu url http get
	// static InputStream is = null;
	// static JSONObject jObj = null;
	// static String json = "";

	public static JSONObject getJSONFromUrl(String url) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			System.out.println("######## dung luoeng lay ve:" + sb.length());
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return jObj;
	}

	/**
	 * http post
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getJSONFromUrlPost(String host, String data) {
		// Making HTTP request
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		try {
			URL url = new URL(host);

			URLConnection conn = url.openConnection();
			// HttpURLConnection http = (HttpURLConnection) conn;
			// System.out.println("respon:"+http.getResponseCode());
			conn.setDoOutput(true);
			conn.addRequestProperty("Content-Length", data.length() + "");
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			is = conn.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			System.out.println("######## dung luoeng lay ve:" + sb.length());
			is.close();
			json = sb.toString();
			// wr.close();
			// rd.close();
			jObj = new JSONObject(json);
			// wr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		// try {
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// Log.e("Buffer Error", "Error converting result " + e.toString());
		// }
		//
		// // try parse the string to a JSON object
		// try {
		//
		// } catch (JSONException e) {
		// Log.e("JSON Parser", "Error parsing data " + e.toString());
		// }

		// return JSON String

		return jObj;
	}

	public static JSONObject getJSONFromUrlSlide(String url) {
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			// BufferedReader rd = new BufferedReader(new
			// InputStreamReader(is));
			// String line;
			// while ((line = rd.readLine()) != null) {
			// // Process line...
			// System.out.println("***Noi Dung lay ve:"+line);
			// }
			// rd.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			// System.out.println("###Noi dung lay ve:"+line);
			is.close();
			json = sb.toString();

			jObj = new JSONObject("{slide:" + json + "}");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

		return jObj;
	}

	// public static Drawable ImageOperations(Context ctx, String url) {
	// try {
	// InputStream is = (InputStream) fetch2(url);
	// Drawable d = Drawable.createFromStream(is, "src");
	// // d= resize(d,90,130);
	// return d;
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// return null;
	// } catch (OutOfMemoryError ot){
	// ot.printStackTrace();
	// // Drawable dr = ctx.getResources().getDrawable(R.drawable.bg_film);
	// return null;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	// public static Object fetch2(String address) throws
	// MalformedURLException,IOException {
	// URL url = new URL(address);
	// Object content = url.getContent();
	// return content;
	// }
	public static Drawable resize(Drawable image, int width, int hight) {
		// System.out.println("Size first:"+image.getIntrinsicHeight()+";width:"+image.getIntrinsicWidth());
		try {
			Bitmap d = ((BitmapDrawable) image).getBitmap();
			Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, width, hight,
					false);
			// Drawable draw = new BitmapDrawable(bitmapOrig);
			// System.out.println("Size first:"+draw.getIntrinsicHeight()+";width:"+draw.getIntrinsicWidth());
			return new BitmapDrawable(bitmapOrig);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static String getDateFromTimestamp(String timestamp_string) {
		String date = "";
		try {
			// String epochString = "1380027282";
			long epoch = Long.parseLong(timestamp_string);
			Date expiry = new Date(epoch * 1000);

			SimpleDateFormat sdf;

			sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			System.out.println("Time:  " + sdf.format(expiry));
			date = sdf.format(expiry);
			date = date.substring(0, date.indexOf(" "));
			// date =
			// expiry.getDate()+"-"+expiry.getMonth()+"-"+expiry.getYear();
			// System.out.println("Hoan thanhh doi time:");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return date;
	}

	// [3:22:29 PM] Liar: // Getting the current epoch time
	// long epoch = System.currentTimeMillis()/1000;
	// System.out.println("Epoch time =>"+ epoch);
	public static long getTimeStampCurrent() {
		return System.currentTimeMillis() / 1000;
	}

	// public static void showConfirm(AlertDialog dialog,String string, Activity
	// activity,
	// LayoutInflater inflater, OnClickListener click, byte type) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	// View viewConfirm = inflater.inflate(R.layout.util_popup_dialog, null);
	// final TextView mes = (TextView) viewConfirm
	// .findViewById(R.id.popup_msg);
	// builder.setView(viewConfirm);
	// builder.setTitle("");
	// dialog = builder.create();
	// // dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
	// dialog.show();
	//
	// mes.setText(string);
	// Button okConfirm = (Button) viewConfirm.findViewById(R.id.popup_accept);
	// okConfirm.setWidth(100);
	// okConfirm.setOnClickListener(click);
	//
	// Button cancelConfirm = (Button) viewConfirm
	// .findViewById(R.id.popup_cancel);
	// if (type == 1) {
	// cancelConfirm.setVisibility(View.GONE);
	// } else {
	// cancelConfirm.setOnClickListener(new OnClickListener() {
	//
	// // @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// }
	// });
	// }
	// }
	// private static int getIndexAmLich(int nam) {
	// int index = 0;
	// if (nam >= Constrant.minNamSinh && nam <= Constrant.maxNamSinh) {
	// index = nam - Constrant.minNamSinh;
	// } else if (nam < Constrant.minNamSinh) {
	// index = (nam + (Constrant.minNamSinh / nam) * 60)
	// - Constrant.minNamSinh;
	// } else if (nam > Constrant.maxNamSinh) {
	// index = (nam - (nam / Constrant.maxNamSinh) * 60)
	// - Constrant.minNamSinh;
	// }
	// return index;
	// }
	//
	// public static String getNamSinhAmLich(int nam) {
	// String namsinh = "";
	// try {
	// namsinh = Constrant.TuoiXayNha_TenNam[getIndexAmLich(nam)];
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// return namsinh;
	// }

	// String[] noidung = { "Dương cưu", "Kim ngưu", "Song tử", "Cự giải",
	// "Sư tử", "Xử nữ", "Thiên binh", "Hổ cáp", "Nhân mã", "Ma kết",
	// "Bảo bình", "Song ngư" };
	// String[] noidung2 = { "21/3-19/4", "20/4-20/5", "21/5-21/6", "22/6-22/7",
	// "23/7-22/8", "23/8-22/9", "23/9-22/10", "23/10-21/11",
	// "22/11-21/12", "22/12-19/1", "20/1-18/2", "19/2-20/3" };

	public static int getIndexCung(int date, int month, int year) {
		int result = 0;
		Date birthday = new Date(year, month, date);
		Date date1 = new Date(year, 3, 21);
		Date date2 = new Date(year, 4, 19);
		Date date3 = new Date(year, 5, 20);
		Date date4 = new Date(year, 6, 21);
		Date date5 = new Date(year, 7, 22);
		Date date6 = new Date(year, 8, 22);
		Date date7 = new Date(year, 9, 22);
		Date date8 = new Date(year, 10, 22);
		Date date9 = new Date(year, 11, 21);
		Date date10 = new Date(year, 12, 21);
		Date date11 = new Date(year, 1, 19);
		Date date12 = new Date(year, 2, 18);
		Date date13 = new Date(year, 3, 20);

		if (birthday.getTime() >= date1.getTime()
				& birthday.getTime() <= date2.getTime()) {
			result = 0;
		} else if (birthday.getTime() > date2.getTime()
				& birthday.getTime() <= date3.getTime()) {
			result = 1;
		} else if (birthday.getTime() > date3.getTime()
				& birthday.getTime() <= date4.getTime()) {
			result = 2;
		} else if (birthday.getTime() > date4.getTime()
				& birthday.getTime() <= date5.getTime()) {
			result = 3;
		} else if (birthday.getTime() > date5.getTime()
				& birthday.getTime() <= date6.getTime()) {
			result = 4;
		} else if (birthday.getTime() > date6.getTime()
				& birthday.getTime() <= date7.getTime()) {
			result = 5;
		} else if (birthday.getTime() > date7.getTime()
				& birthday.getTime() <= date8.getTime()) {
			result = 6;
		} else if (birthday.getTime() > date8.getTime()
				& birthday.getTime() <= date9.getTime()) {
			result = 7;
		} else if (birthday.getTime() > date9.getTime()
				& birthday.getTime() <= date10.getTime()) {
			result = 8;
		} else if (birthday.getTime() > date11.getTime()
				& birthday.getTime() <= date12.getTime()) {
			result = 10;
		} else if (birthday.getTime() > date12.getTime()
				& birthday.getTime() <= date13.getTime()) {
			result = 11;
		} else {
			result = 9;
		}
		return result;
	}

	public static int getContent12ConGiap(int soConGiap) {
		soConGiap = soConGiap % 12;
		int result = 0;
		try {
			result = soConGiap % 12;
			;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}
}
