package com.brand.sniffy.android.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandHorizontalAnimation extends Animation{

	private View leftView;
	
	private int targetWidth;
	
	private boolean expand;

	private View rightView;

	public ExpandHorizontalAnimation(final View leftView, final View rightView, int targetWidth, boolean expand){
		this.leftView = leftView;
		this.rightView = rightView;
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

		leftView.setTranslationX(newWidth - targetWidth);
		rightView.setTranslationX(newWidth);
		super.applyTransformation(interpolatedTime, t);
	}
	
	@Override
	public boolean willChangeBounds() {
		return true;
	}
}

