package com.brand.sniffy.android.command;

import java.util.ArrayList;
import java.util.List;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.service.ProductService;
import com.brand.sniffy.android.service.SessionManager;

import android.content.Context;

public class ProductCommandFactory {

	private Context context;
	
	private ProductService productService;

	public ProductCommandFactory(Context context){
		this.context = context;
		productService = new ProductService(context, new SessionManager(context).getCurrentUser());
	}

	public List<Command> createComandsForProduct(Product product) {
		List<Command> commands = new ArrayList<Command>();
		//commands.add(new ShowDescriptionCommand(context));
		if(productService.hasComponents(product)){
			commands.add(new ShowComponentsCommand(
					context.getResources().getDrawable(R.drawable.comment_icon),
					context.getString(R.string.components_item_button_label),
					product));
		}
		//commands.add(new CommentsCommand(context));
		//commands.add(new ReportCommand(context));
		//commands.add(new ShowSimilarsCommand(context));
		return commands;
	}
}
