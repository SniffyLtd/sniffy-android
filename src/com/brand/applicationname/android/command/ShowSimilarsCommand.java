package com.brand.applicationname.android.command;

import com.brand.applicationname.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ShowSimilarsCommand implements Command{

	private Context context;

	public ShowSimilarsCommand(Context context){
		this.context = context;
	}

	@Override
	public Drawable getIcon() {
		return context.getResources().getDrawable(R.drawable.similar_icon);
	}

	@Override
	public String getHeader() {
		return context.getString(R.string.show_similars_menu_item);
	}

	@Override
	public boolean execute(Object param) {
		// TODO Auto-generated method stub
		return false;
	}

}
