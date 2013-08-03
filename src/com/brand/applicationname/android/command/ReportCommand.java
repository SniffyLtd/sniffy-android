package com.brand.applicationname.android.command;


import com.brand.applicationname.android.ProductDetailsActivity;
import com.brand.applicationname.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ReportCommand implements Command{
	
	private Context context;

	public ReportCommand(Context context){
		this.context = context;
	}

	@Override
	public Drawable getIcon() {
		return context.getResources().getDrawable(R.drawable.report_icon);
	}

	@Override
	public String getHeader() {
		return context.getString(R.string.report_product_menu_item);
	}

	@Override
	public boolean execute(Object param) {
		if(param instanceof ProductDetailsActivity){
			ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			detailsActivity.hideAwsomeMenu();
		}
		else{
			throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		}
		return false;
	}

}
