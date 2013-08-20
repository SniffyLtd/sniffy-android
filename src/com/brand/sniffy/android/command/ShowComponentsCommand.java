package com.brand.sniffy.android.command;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.ProductDetailsActivity;
import com.brand.sniffy.android.adapter.DetailsPagerAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class ShowComponentsCommand implements Command{

	private final Handler executor = new Handler();
	
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

			Runnable runable = new Runnable() {
				@Override
				public void run() {
					detailsActivity.showDetailsFragment(DetailsPagerAdapter.COMPONENTS_DETAILS);
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
