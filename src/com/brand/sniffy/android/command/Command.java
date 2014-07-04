package com.brand.sniffy.android.command;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface Command {

	Drawable getIcon();
	
	String getHeader();
	
	boolean execute(Context context);
}
