package com.brand.sniffy.android;

import java.util.Locale;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.API.MainActivityNavigation;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.service.ProductService;
import com.brand.sniffy.android.service.ScanningService;
import com.brand.sniffy.android.service.SynchronizationService;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, MainActivityNavigation {

    static final int PRODUCT_REQUEST_CODE = 1;

	/**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    
    private ProductService productService;
    
    private ScanningService scanningService;
    
    private SynchronizationService synchronizationService;
    
    private ProductService getProductService(){
    	if(productService == null){
    		productService = new ProductService(this);
    	}
    	return productService;
    }
    
    private ScanningService getScanningService() {
    	if (scanningService == null){
    		scanningService= new ScanningService(this);
    	}
		return scanningService;
	}
    
    private SynchronizationService getSynchronizationService(){
    	if(synchronizationService == null){
    		synchronizationService = new SynchronizationService(this);
    	}
    	
    	return synchronizationService;
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        getSynchronizationService().requestSynchronization();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    	
    	private static final int SCANER_TAB = 0;
    	
    	private static final int HISTORY_TAB = 1;
    	
    	private static final int DASHBOARD_TAB = 2;
    	

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
            case SCANER_TAB:
            	fragment = new ScanerFragment();
            	break;
            case HISTORY_TAB:
            	fragment = new HistoryFragment();
            	break;
            case DASHBOARD_TAB:
            	fragment = new DashboardFragment();
            	break;
            default:
            	throw new IllegalStateException("Unknow tab position.");
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case SCANER_TAB:
                    return getString(R.string.title_scaner_tab).toUpperCase(l);
                case HISTORY_TAB:
                    return getString(R.string.title_history_tab).toUpperCase(l);
                case DASHBOARD_TAB:
                    return getString(R.string.title_dashboard_tab).toUpperCase(l);
            }
            return null;
        }
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == ScanerFragment.SCAN_REQUEST_CODE){
			if(resultCode == RESULT_OK){
				String contents = data.getStringExtra("SCAN_RESULT");
				this.search(contents);
			}
		}
	/*	else if(requestCode == PRODUCT_REQUEST_CODE){
			ScanerFragment scanerFragment = (ScanerFragment)getSupportFragmentManager().findFragmentById(R.layout.fragment_scaner);
			if(scanerFragment != null ){
				scanerFragment.clean();
			}
			
			HistoryFragment historyFragment =(HistoryFragment)getSupportFragmentManager().findFragmentById(R.layout.fragment_history);
			if(historyFragment != null && historyFragment.isInLayout()){
				historyFragment.refresh();
			}
		}*/
	}

	@Override
	public void search(String baredoce) {
		Product product = getProductService().findProduct(baredoce);
		if(product == null){
			product = getProductService().createLocalProduct(baredoce);
		}
		getScanningService().create(baredoce, product);
		openProductDetails(product);
	}

	@Override
	public void openProductDetails(Product product) {
		Intent intent = new Intent(this, ProductDetailsActivity.class);
		intent.putExtra(ProductDetailsActivity.PRODUCT_PARAMETER, product);
		startActivityForResult(intent, PRODUCT_REQUEST_CODE);
	}
}
