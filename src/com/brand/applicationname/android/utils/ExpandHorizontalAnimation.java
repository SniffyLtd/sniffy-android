package com.brand.applicationname.android.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandHorizontalAnimation extends Animation{

	private View view;
	
	private int targetWidth;
	
	private boolean expand;

	public ExpandHorizontalAnimation(final View view, int targetWidth, boolean expand){
		this.view = view;
		this.targetWidth = targetWidth;
		this.expand = expand;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		int newWidth;

		if(expand){
			newWidth = (int)(interpolatedTime * targetWidth);
		}
		else{
			newWidth = (int)((1- interpolatedTime) * targetWidth);
		}
		view.getLayoutParams().width = newWidth;
		view.requestLayout();
		super.applyTransformation(interpolatedTime, t);
	}
	
	@Override
	public boolean willChangeBounds() {
		return true;
	}
}

