package com.brand.sniffy.android.command;

import android.graphics.drawable.Drawable;

public interface Command {

	Drawable getIcon();
	
	String getHeader();
	
	boolean execute(Object param);
}
