package com.brand.applicationname.android;

import com.brand.applicationname.android.adapter.ComponentsListAdapter;
import com.brand.applicationname.android.model.Product;
import com.brand.applicationname.android.service.ComponentsService;
import com.brand.applicationname.android.utils.ExpandHorizontalAnimation;
import com.brand.applicationname.android.utils.ExpandVertilcalAnimation;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProductDetailsActivity extends FragmentActivity {

	public static final String PRODUCT_PARAMETER = "product";

	public static final int COMPONENTS_DETAILS = 0;
	
	public static final int DESCRIPTION_DETAILS = 1;
	
	private Button expandButton;
	
	private View menu;
	
	//private Button descriptionItemButton;
	
	//private Button componentsItemButton;
	
	//private View descriptionItemContent;
	
	//private RelativeLayout componentsItemContent;
	
	private TextView categoryLabel;
	
	private TextView nameLabel;
	
	private TextView barecodeLabel;
	
	//private TextView descriptionLabel;
	
	//private ListView componentsList;
	
	//private ComponentsListAdapter componentsListAdapter;
	
	private boolean menuOpened = false;
	
	private ViewPager detailsPager;
	
	private DetailsPagerAdapter detailsPagerAdapter;
	
	//private int itemContentHeight;
	
	//private ComponentsService componentsService = new ComponentsService();

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
		//descriptionItemButton = (Button)findViewById(R.id.description_item_button);
		//componentsItemButton = (Button)findViewById(R.id.components_item_button);
		//descriptionItemContent = findViewById(R.id.description_item_content);
		//componentsItemContent = (RelativeLayout)findViewById(R.id.components_item_content);
		
		categoryLabel = (TextView)findViewById(R.id.category_path_label);
		nameLabel = (TextView)findViewById(R.id.product_name_label);
		barecodeLabel = (TextView)findViewById(R.id.product_barecode_label);
		detailsPager = (ViewPager)findViewById(R.id.details_pager);
		//descriptionLabel = (TextView)findViewById(R.id.product_description_label);
		//componentsList = new ListView(this);
		//componentsList.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		//componentsItemContent.addView(componentsList);
		
		//componentsItemContent.measure(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		//itemContentHeight = componentsItemContent.getMeasuredHeight();
        
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
		
		expandButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				expandMenu();
			}
		});
		
//		descriptionItemButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				expandProductDescription();
//			}
//		});
//		componentsItemButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				expandProductComponents();
//			}
//		});
	}

	private void showProductInDisplayMode(Product product) {
		categoryLabel.setText("Kategoria produktu >> Podkategoria produktu >> itd");
		nameLabel.setText(product.getName());
		barecodeLabel.setText(product.getBarecode());
//		descriptionLabel.setText(product.getDescription());
//		componentsListAdapter = new ComponentsListAdapter(this, R.layout.item_component, componentsService.getProductComponents(product));
//		componentsList.setAdapter(componentsListAdapter);
	}

	private void showProductInEditMode(Product product) {
		// TODO Auto-generated method stub
		
	}

//	protected void expandProductComponents() {
//		
//		ExpandVertilcalAnimation expandAnimation = new ExpandVertilcalAnimation(componentsItemContent, itemContentHeight, true);
//		ExpandVertilcalAnimation hideAnimation = new ExpandVertilcalAnimation(descriptionItemContent, itemContentHeight, false);
//		expandAnimation.setDuration(200);
//		hideAnimation.setDuration(200);
//		descriptionItemContent.startAnimation(hideAnimation);
//		
//		componentsItemContent.startAnimation(expandAnimation);
//	}

//	protected void expandProductDescription() {
//		ExpandVertilcalAnimation expandAnimation = new ExpandVertilcalAnimation(componentsItemContent, itemContentHeight, false);
//		ExpandVertilcalAnimation hideAnimation = new ExpandVertilcalAnimation(descriptionItemContent, itemContentHeight, true);
//		expandAnimation.setDuration(200);
//		hideAnimation.setDuration(200);
//		descriptionItemContent.startAnimation(hideAnimation);
//		
//		componentsItemContent.startAnimation(expandAnimation);
//		
//	}

	protected void expandMenu() {
		if(menuOpened){
			menu.getLayoutParams().width = 10;
			menu.requestLayout();
		}
		else{
			ExpandHorizontalAnimation animation =  new ExpandHorizontalAnimation(menu, 250, !menuOpened);
			animation.setDuration(500);
			menu.startAnimation(animation);
		}
		
		//menu.setVisibility(menuOpened ? View.VISIBLE : View.GONE);
		menuOpened= !menuOpened;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

	
	public class DetailsPagerAdapter extends FragmentStatePagerAdapter{

		public DetailsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int item) {
			switch(item){
			case COMPONENTS_DETAILS:
				return new ComponentsFragment();
			case DESCRIPTION_DETAILS:
				return new ProductDescriptionFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
		
	}


	public void hideAwsomeMenu() {
		expandMenu();
		
	}

	public void showDetailsFragment(int item) {
		detailsPager.setCurrentItem(item);
	}
}
