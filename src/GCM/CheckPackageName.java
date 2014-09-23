package GCM;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

@SuppressLint("NewApi")
public class CheckPackageName {
	class PInfo {
		private String appname = "";
		private String pname = "";
		private String classname = "";
		private int versionCode = 0;
		private Drawable icon;

		private void prettyPrint() {
			Log.d("Applist ", appname + "   " + pname + "   " + classname
					+ "   " + versionCode);

		}
	}

	public boolean check_packagename(String packaggename, Activity activity) {
		ArrayList<PInfo> arrayList = getPackages(activity);
		for (int i = 0; i < arrayList.size(); i++) {
			PInfo info = arrayList.get(i);
			if (packaggename.equals(info.pname)) {
				return false;
			}
		}
		return true;
	}
	public ArrayList<PInfo> getPackages(Activity activity) {
		Log.d("AppNo", "Inside Packages");
		ArrayList<PInfo> apps = getInstalledApps(activity, false); /*
																	 * false =
																	 * no system
																	 * packages
																	 */
		final int max = apps.size();
		Log.d("AppNo", "no of apps " + max);
		for (int i = 0; i < max; i++) {
			apps.get(i).prettyPrint();

		}
		return apps;
	}

	public ArrayList<PInfo> getInstalledApps(Activity activity,
			boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		List<PackageInfo> packs = activity.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);

			if ((!getSysPackages) && (p.versionName == null)) {
				continue;
			}
			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(
					activity.getPackageManager()).toString();
			newInfo.pname = p.packageName;
			newInfo.classname = p.applicationInfo.className;
			newInfo.versionCode = p.versionCode;
			newInfo.icon = p.applicationInfo.loadIcon(activity
					.getPackageManager());

			Intent app = activity.getPackageManager()
					.getLaunchIntentForPackage(p.packageName);

			if (app != null) {
				// dleteintent(app, newInfo.appname, newInfo.icon);
				res.add(newInfo);
			}

		}
		return res;
	}
}
