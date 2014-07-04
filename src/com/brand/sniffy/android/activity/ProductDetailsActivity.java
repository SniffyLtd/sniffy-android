package com.brand.sniffy.android.activity;
import com.brand.sniffy.android.R;
import com.brand.sniffy.android.command.ProductCommandFactory;
import com.brand.sniffy.android.fragment.ProductMenuFragment;
import com.brand.sniffy.android.model.Product;

import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ProductDetailsActivity extends FragmentActivity {

	public static final String PRODUCT_PARAMETER = "product";
	
	private Button expandButton;
	
	private View menu;
	
	private View productDetailsFrame;
	
	private TextView categoryLabel;
	
	private TextView nameLabel;
	
	private TextView barecodeLabel;
	
	private ViewFlipper viewFlipper;

	private ProductCommandFactory commandFactory;
	//private ViewPager detailsPager;
	
	//private DetailsPagerAdapter detailsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		
		commandFactory = new ProductCommandFactory(this);

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
		viewFlipper = (ViewFlipper)findViewById(R.id.details_view_flipper);
		//detailsPager = (ViewPager)findViewById(R.id.details_pager);
		//detailsPager.setPageTransformer(true, new ZoomOutPageTransformer());
		//detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager());
		
		//detailsPager.setAdapter(detailsPagerAdapter);
		
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(PRODUCT_PARAMETER)) {
        	Product product =  (Product)extras.get(PRODUCT_PARAMETER);
        	if(product == null){
        		showProductInEditMode(product);
        	}
        	else{
        		showProductInDisplayMode(product);
        	}
        }
		
		menu = findViewById(R.id.fragment_product_menu);
		productDetailsFrame = findViewById(R.id.product_details_frame);
		getMenuFragment().setMenuContainer(menu);
		getMenuFragment().setPageContainer(productDetailsFrame);
		expandButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ProductMenuFragment menuFragment = getMenuFragment();
				menuFragment.exand();
			}
		});
		
	}

	protected ProductMenuFragment getMenuFragment() {
		return (ProductMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_product_menu);
	}

	private void showProductInDisplayMode(Product product) {
		getMenuFragment().setCommands(commandFactory.createComandsForProduct(product));
		if(product.getCategory() != null){
			categoryLabel.setText(product.getCategory().getName());
		}
		nameLabel.setText(product.getName());
		barecodeLabel.setText(product.getBarcode());
	}

	private void showProductInEditMode(Product product) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}
	
}
