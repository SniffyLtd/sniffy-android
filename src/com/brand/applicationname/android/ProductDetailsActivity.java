package com.brand.applicationname.android;

import com.brand.applicationname.android.adapter.DetailsPagerAdapter;
import com.brand.applicationname.android.model.Product;
import com.brand.applicationname.android.utils.ExpandHorizontalAnimation;
import com.brand.applicationname.android.utils.ZoomOutPageTransformer;

import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProductDetailsActivity extends FragmentActivity {

	public static final String PRODUCT_PARAMETER = "product";
	
	private Button expandButton;
	
	private View menu;
	
	private View productDetailsFrame;
	
	private TextView categoryLabel;
	
	private TextView nameLabel;
	
	private TextView barecodeLabel;
	
	private boolean menuOpened = false;
	
	private ViewPager detailsPager;
	
	private DetailsPagerAdapter detailsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.hide();
        
		expandButton = (Button)findViewById(R.id.expand_button);
		
		categoryLabel = (TextView)findViewById(R.id.category_path_label);
		nameLabel = (TextView)findViewById(R.id.product_name_label);
		barecodeLabel = (TextView)findViewById(R.id.product_barecode_label);
		detailsPager = (ViewPager)findViewById(R.id.details_pager);
		detailsPager.setPageTransformer(true, new ZoomOutPageTransformer());
		detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager());
		
		detailsPager.setAdapter(detailsPagerAdapter);
		
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(PRODUCT_PARAMETER)) {
        	Product product =  (Product)extras.get(PRODUCT_PARAMETER);
        	if(product.isLocal()){
        		showProductInEditMode(product);
        	}
        	else{
        		showProductInDisplayMode(product);
        	}
        }
		
		
		
		menu = findViewById(R.id.fragment_product_menu);
		productDetailsFrame = findViewById(R.id.product_details_frame);
		
		expandButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				expandMenu();
			}
		});
		
	}

	private void showProductInDisplayMode(Product product) {
		categoryLabel.setText("Kategoria produktu >> Podkategoria produktu >> itd");
		nameLabel.setText(product.getName());
		barecodeLabel.setText(product.getBarecode());
	}

	private void showProductInEditMode(Product product) {
		// TODO Auto-generated method stub
		
	}

	protected void expandMenu() {
		ExpandHorizontalAnimation animation =  new ExpandHorizontalAnimation(menu, productDetailsFrame, 250, !menuOpened);
		animation.setDuration(200);
		menu.startAnimation(animation);
		menuOpened= !menuOpened;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

	public void hideAwsomeMenu() {
		expandMenu();
		
	}

	public void showDetailsFragment(int item) {
		detailsPager.setCurrentItem(item);
	}
}
