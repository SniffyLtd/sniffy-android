package com.brand.applicationname.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brand.applicationname.android.ComponentsFragment;
import com.brand.applicationname.android.ProductDescriptionFragment;

public class DetailsPagerAdapter extends FragmentStatePagerAdapter{

	public static final int COMPONENTS_DETAILS = 0;
	public static final int DESCRIPTION_DETAILS = 1;

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
