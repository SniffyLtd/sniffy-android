package com.brand.sniffy.android.command;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.ProductDetailsActivity;
import com.brand.sniffy.android.adapter.DetailsPagerAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class ShowDescriptionCommand implements Command {

	private final Handler executor = new Handler();
	
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
			final ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			detailsActivity.hideAwsomeMenu();
			Runnable runable = new Runnable() {
				@Override
				public void run() {
					detailsActivity.showDetailsFragment(DetailsPagerAdapter.DESCRIPTION_DETAILS);
				}
			};
			executor.postDelayed(runable, 300);
		}
		else{
			throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		}
		return false;
	}

}
