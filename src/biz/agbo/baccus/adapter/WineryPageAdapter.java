package biz.agbo.baccus.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import biz.agbo.baccus.controller.fragment.WineFragment;
import biz.agbo.baccus.model.Winery;

public class WineryPageAdapter extends FragmentPagerAdapter {
	
	private Winery mWinery = null;
	
	public WineryPageAdapter(FragmentManager manager){
		super(manager);
		mWinery = Winery.getInstance();
	}

	@Override
	public Fragment getItem(int index) {
		WineFragment fragment = new WineFragment();
		
		Bundle arguments = new Bundle();
		arguments.putSerializable(WineFragment.ARG_WINE, mWinery.getWine(index));
		fragment.setArguments(arguments);
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mWinery.getCount();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		super.getPageTitle(position);
		return mWinery.getWine(position).getName();
	}

}
