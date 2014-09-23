package Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;

public class AdapterKara extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> data;
	private static LayoutInflater inflater = null;

	public AdapterKara(Activity a, ArrayList<String> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);
		TextView title = (TextView) vi.findViewById(R.id.list_home_item_text); // title
		String song = data.get(position);
		title.setText(song);
		return vi;
	}
}