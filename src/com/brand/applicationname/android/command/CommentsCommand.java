package com.brand.applicationname.android.command;

import com.brand.applicationname.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class CommentsCommand implements Command{

	private Context context;

	public CommentsCommand(Context context){
		this.context = context;
	}

	@Override
	public Drawable getIcon() {
		return context.getResources().getDrawable(R.drawable.comment_icon);
	}

	@Override
	public String getHeader() {
		return context.getString(R.string.show_comments_menu_item);
	}

	@Override
	public boolean execute(Object param) {
		// TODO Auto-generated method stub
		return false;
	}

}
