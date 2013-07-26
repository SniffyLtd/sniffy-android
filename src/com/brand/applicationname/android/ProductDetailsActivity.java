package com.brand.applicationname.android;

import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProductDetailsActivity extends Activity {

	public static final String PRODUCT_PARAMETER = "product";
	
	private Button expandButton;
	
	private View menu;
	
	private boolean menuOpened = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		expandButton = (Button)findViewById(R.id.expand_button);
		menu = findViewById(R.id.fragment_product_menu);
		expandButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				expandMenu();
			}
		});
	}

	protected void expandMenu() {
		if(menuOpened){
			menu.setVisibility(View.GONE);
		}
		else{
			menu.setVisibility(View.VISIBLE);
		}
		menuOpened= !menuOpened;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

}
