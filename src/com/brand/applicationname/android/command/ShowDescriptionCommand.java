package com.brand.applicationname.android.command;

import com.brand.applicationname.android.ProductDetailsActivity;
import com.brand.applicationname.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ShowDescriptionCommand implements Command {

	private Context context;

	public ShowDescriptionCommand(Context context){
		this.context = context;
	}
	
	@Override
	public Drawable getIcon() {
		return context.getResources().getDrawable(R.drawable.comment_icon);
	}

	@Override
	public String getHeader() {
		return context.getString(R.string.show_description_menu_item);
	}

	@Override
	public boolean execute(Object param) {
		if(param instanceof ProductDetailsActivity){
			ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			detailsActivity.hideAwsomeMenu();
			detailsActivity.showDetailsFragment(ProductDetailsActivity.DESCRIPTION_DETAILS);
		}
		else{
			throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		}
		return false;
	}

}
