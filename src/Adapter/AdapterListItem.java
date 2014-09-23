package Adapter;

import java.util.ArrayList;

import Data.SMS;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterListItem extends ArrayAdapter<SMS> {

	ArrayList<SMS> arrayList;
	Context context;

	public AdapterListItem(Context context, int textID,
			ArrayList<SMS> objects) {
		super(context, textID, objects);
		this.context = context;
		arrayList = objects;
	}

	public ArrayList<SMS> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<SMS> arrayList) {
		this.arrayList = arrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = new CustomViewGroup(getContext());
		}
		final SMS names = arrayList.get(position);
		if (names != null) {
			final TextView textView = (TextView) ((CustomViewGroup) view).title;
			textView.setText(names.getContent());
		}
		return view;
	}
}
