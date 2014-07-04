package com.brand.sniffy.android.command;

import com.brand.sniffy.android.model.Product;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ShowComponentsCommand implements Command{

	private Drawable icon;
	
	private String header;

	public ShowComponentsCommand(Drawable icon, String header, Product product){
		this.icon = icon;
		this.header = header;
	}
	
	@Override
	public Drawable getIcon() {
		return icon;
	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public boolean execute(Context context) {
		
		//if(param instanceof ProductDetailsActivity){
			/*final ProductDetailsActivity detailsActivity = (ProductDetailsActivity)param;
			detailsActivity.hideAwsomeMenu();

			Runnable runable = new Runnable() {
				@Override
				public void run() {
					//detailsActivity.showDetailsFragment(DetailsPagerAdapter.COMPONENTS_DETAILS);
				}
			};
			executor.postDelayed(runable, 300);*/
		//}
		//else{
		//	throw new ClassCastException("Param shoulf be instance of ProductDetailsActivity.");
		//}
		return false;
	}

}
