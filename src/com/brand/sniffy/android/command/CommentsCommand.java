package com.brand.sniffy.android.command;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.activity.ProductDetailsActivity;

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
	public boolean execute(Context context) {
		//if(param instanceof ProductDetailsActivity){
		//	ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			//detailsActivity.hideAwsomeMenu();
		//}
		//else{
		//	throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		//}
		return false;
	}

}
