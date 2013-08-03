package com.brand.applicationname.android.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandVertilcalAnimation extends Animation{

	private View view;
	
	private int targetHeight;
	
	private boolean expand;

	public ExpandVertilcalAnimation(final View view, int targetHeight, boolean expand){
		this.view = view;
		this.targetHeight = targetHeight;
		this.expand = expand;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		int newheight;

		if(expand){
			newheight = (int)(interpolatedTime * targetHeight);
		}
		else{
			newheight = (int)((1- interpolatedTime) * targetHeight);
		}
		view.getLayoutParams().height = newheight;
		view.requestLayout();
		super.applyTransformation(interpolatedTime, t);
	}
	
	@Override
	public boolean willChangeBounds() {
		return true;
	}
}
