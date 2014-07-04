package com.brand.sniffy.android.command;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.activity.ProductDetailsActivity;

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
	public boolean execute(Context param) {
		//if(param instanceof ProductDetailsActivity){
//			ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			//detailsActivity.hideAwsomeMenu();
	//	}
	//	else{
	//		throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
	//	}
		return false;
	}

}
