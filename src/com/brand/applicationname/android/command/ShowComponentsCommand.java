package com.brand.applicationname.android.command;

import com.brand.applicationname.android.ProductDetailsActivity;
import com.brand.applicationname.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class ShowComponentsCommand implements Command{

	private Context contex;

	public ShowComponentsCommand(Context contex){
		this.contex = contex;
	}
	
	@Override
	public Drawable getIcon() {
		return contex.getResources().getDrawable(R.drawable.comment_icon);
	}

	@Override
	public String getHeader() {
		return contex.getResources().getString(R.string.show_components_menu_item);
	}

	@Override
	public boolean execute(Object param) {
		if(param instanceof ProductDetailsActivity){
			final ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			detailsActivity.hideAwsomeMenu();
			detailsActivity.showDetailsFragment(ProductDetailsActivity.COMPONENTS_DETAILS);
		}
		else{
			throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		}
		return false;
	}

}
