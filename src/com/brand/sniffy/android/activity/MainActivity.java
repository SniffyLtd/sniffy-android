package com.brand.sniffy.android.activity;

import java.util.Locale;

import com.brand.sniffy.android.fragment.DashboardFragment;
import com.brand.sniffy.android.fragment.HistoryFragment;
import com.brand.sniffy.android.fragment.HistoryFragment.OnOpenItemListener;
import com.brand.sniffy.android.fragment.HistoryFragment.OnRefreshItemListener;
import com.brand.sniffy.android.fragment.HistoryFragment.OnShareItemListener;
import com.brand.sniffy.android.fragment.HistoryFragmentImpl;
import com.brand.sniffy.android.fragment.ScanerFragmentImpl;
import com.brand.sniffy.android.fragment.ScanerFragment;
import com.brand.sniffy.android.fragment.ScanerFragment.OnSearchListener;
import com.brand.sniffy.android.model.Product;
import com.brand.sniffy.android.model.Scanning;
import com.brand.sniffy.android.service.ProductService;
import com.brand.sniffy.android.service.ScanningService;
import com.brand.sniffy.android.service.SessionManager;
import com.brand.sniffy.android.R;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    static final int PRODUCT_REQUEST_CODE = 1;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    
    private ProductService productService;
    
    private ScanningService scanningService;
    
    private SessionManager sessionManager;

    private HistoryFragmentController historyFragmentController = new HistoryFragmentController();
    
    private ScanerFragmentController scanerFragmentController = new ScanerFragmentController();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        scanningService= new ScanningService(this, sessionManager.getCurrentUser());
        productService = new ProductService(this, sessionManager.getCurrentUser());
        
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
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				sessionManager.logoutUser();
				return true;
			}
		});
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
            	fragment = new ScanerFragmentImpl();
            	break;
            case HISTORY_TAB:
            	fragment = new HistoryFragmentImpl();
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
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	Fragment f = (Fragment)super.instantiateItem(container, position);
        	if(f instanceof HistoryFragment){
        		((HistoryFragment) f).addOnOpenItemListener(historyFragmentController);
        		((HistoryFragment) f).addOnRefreshItemListener(historyFragmentController);
        		((HistoryFragment) f).addOnShareItemListener(historyFragmentController);
        	}
        	else if(f instanceof ScanerFragment){
        		((ScanerFragment) f).addOnSearchListner(scanerFragmentController);
        	}
        	return f;
        }
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == ScanerFragmentImpl.SCAN_REQUEST_CODE){
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

	public void search(String barcode) {
		AsyncTask<String, Void, Product> task = new AsyncTask<String, Void, Product>(){

			@Override
			protected void onPreExecute() {
				showProgressBar();
			};
			
			@Override
			protected Product doInBackground(String... arguments) {
				if(arguments.length == 0){
					throw new IllegalArgumentException("Barcode argument required.");
				}

				String barcode = arguments[0];
				String status = null;
				Product product = null;
				
				try{
					product = productService.findProduct(barcode);
					if(product == null){
						status = Scanning.STATUS_NOT_FOUND;
					}
					else{
						status = Scanning.STATUS_FOUND;
					}
				}
				catch(IllegalArgumentException e){
					status = Scanning.STATUS_REJECTED;
				}
				catch(IllegalStateException e){
					status = Scanning.STATUS_PENDING;
				}
				catch (Exception e) {
					Log.e(MainActivity.class.getName(), "Error while searching for product. ", e);
					status = Scanning.STATUS_FAILED;
				}

				scanningService.create(barcode, product, status);
				return product;
			}
			
			@Override
			protected void onPostExecute(Product product) {
				hideProgressBar();
				openProductDetails(product);
				super.onPostExecute(product);
			}
			
		};

		task.execute(barcode);

	}
	
	private void openProductDetails(Product product) {
		Intent intent = new Intent(this, ProductDetailsActivity.class);
		if(product != null){
			intent.putExtra(ProductDetailsActivity.PRODUCT_PARAMETER, product);
		}
		
		startActivityForResult(intent, PRODUCT_REQUEST_CODE);
	}

	protected void hideProgressBar() {
		// if (progressDialog != null) {
         //    progressDialog.dismiss();
		 //}
	}

	private void showProgressBar() {
		//progressDialog = ProgressDialog.show(this,
		//		this.getString(R.string.search_product_title),
		//		this.getString(R.string.search_product_text),
         //       true);
	}
	
	private class ScanerFragmentController implements OnSearchListener{

		@Override
		public void onSearch(String barcode, ScanerFragment scanningFragment) {
			search(barcode);
		}
		
	}

	private class HistoryFragmentController implements OnOpenItemListener, OnRefreshItemListener, OnShareItemListener{

		@Override
		public void onShareItem(Scanning item, HistoryFragment historyFragment) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRefreshItem(final Scanning item,final HistoryFragment historyFragment) {
			AsyncTask<String, Void, Product> task = new AsyncTask<String, Void, Product>(){

				@Override
				protected void onPreExecute() {
					showProgressBar();
				};
				
				@Override
				protected Product doInBackground(String... arguments) {
					if(arguments.length == 0){
						throw new IllegalArgumentException("Barcode argument required.");
					}

					String barcode = arguments[0];
					String status = null;
					Product product = null;
					
					try{
						product = productService.findProduct(barcode);
						if(product == null){
							status = Scanning.STATUS_NOT_FOUND;
						}
						else{
							status = Scanning.STATUS_FOUND;
						}
					}
					catch(IllegalArgumentException e){
						status = Scanning.STATUS_REJECTED;
					}
					catch(IllegalStateException e){
						status = Scanning.STATUS_PENDING;
					}
					catch (Exception e) {
						Log.e(MainActivity.class.getName(), "Error while searching for product. ", e);
						status = Scanning.STATUS_FAILED;
					}

					item.setStatus(status);
					item.setFoundProduct(product);
					scanningService.update(item);
					return product;
				}
				
				@Override
				protected void onPostExecute(Product product) {
					historyFragment.refreshHistoryFragment();
					hideProgressBar();
					super.onPostExecute(product);
				}
				
			};

			task.execute(item.getBarecode());
		}

		@Override
		public void onOpenItem(Scanning item, HistoryFragment historyFragment) {
			openProductDetails(item.getFoundProduct());
		}
	}
	
}
