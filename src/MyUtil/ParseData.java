package MyUtil;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Data.LiveWallPaper;

public class ParseData {

	// public K_USER parse(JSONObject jsonObject) {
	// K_USER myItem = new K_USER();
	//
	// String TAG_ = "",;
	// try {
	// = jsonObject.getString(TAG_);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// myItem.set();
	// return myItem;
	// }

	// public KARA parse(JSONObject jsonObject) {
	// KARA myItem = new KARA();
	// String ="";
	// String TAG_ ="",;
	// try {
	// = jsonObject.getString(TAG_);
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// myItem.set();
	// return myItem;
	// }
	public LiveWallPaper parseLiveWallPaper(JSONObject jsonObject) {
		LiveWallPaper myItem = new LiveWallPaper();
		String TenFile = "TenFile";
		String TAG_TenFile = "TenFile";
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			TenFile = jsonObject.getString(TAG_TenFile);
			JSONArray jsonArray = jsonObject.getJSONArray("ListPic");
			for (int i = 0; i < jsonArray.length(); i++) {
				String string = (String) jsonArray.get(i);
				arrayList.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		myItem.setTenFile(TenFile);
		myItem.setArrayList(arrayList);
		return myItem;
	}

	public ArrayList<LiveWallPaper> parseArrayLiveWallPaper(String string_json) {
		ArrayList<LiveWallPaper> arrayListLiveWallPapers = new ArrayList<LiveWallPaper>();
		try {
			JSONArray jsonArray = new JSONArray(string_json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				LiveWallPaper liveWallPaper = parseLiveWallPaper(jsonObject);
				arrayListLiveWallPapers.add(liveWallPaper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayListLiveWallPapers;
	}
}
