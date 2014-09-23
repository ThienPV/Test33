package Adapter;

import sms.birthday.chucmung.chucmungsinhnhat.smstinhyeu.happybirthday.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewGroup extends LinearLayout{

	public TextView title;
	public CustomViewGroup(Context context){
		super(context);
		LayoutInflater layin = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layin.inflate(R.layout.listmusic, this, true);
		title = (TextView) findViewById(R.id.item);
	}
}
