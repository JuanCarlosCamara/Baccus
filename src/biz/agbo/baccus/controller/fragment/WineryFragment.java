package biz.agbo.baccus.controller.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import biz.agbo.baccus.R;
import biz.agbo.baccus.adapter.WineryPageAdapter;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;

public class WineryFragment extends Fragment implements OnPageChangeListener, TabListener{
	
	public static final String ARG_WINE_INDEX = "biz.agbo.baccus.controller.fragment.WINE_INDEX";
	public static final String ARG_SHOW_TABS = "biz.agbo.baccus.controller.fragment.SHOW_TABS";
	public static final String PREF_LAST_WINE = "last_wine";
	
	private static final int MENU_NEXT = 1;
	private static final int MENU_PREVIOUS = 2;
	
	private ViewPager mPager = null;
	private ActionBar mActionBar = null;
	private Winery mWinery = null;
	private ProgressDialog mProgressDialog = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		if(mPager != null){
			super.onCreateOptionsMenu(menu, inflater);
			
			MenuItem nextMenuItem = menu.add(Menu.NONE, MENU_NEXT, 1, R.string.next);
			MenuItemCompat.setShowAsAction(nextMenuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
			
			MenuItem previousMenuItem = menu.add(Menu.NONE, MENU_PREVIOUS, 0, R.string.previous);
			MenuItemCompat.setShowAsAction(previousMenuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
			
			int index = mPager.getCurrentItem();
			nextMenuItem.setEnabled(index < mWinery.getCount() - 1);
			previousMenuItem.setEnabled(index > 0);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == MENU_NEXT){
			if(mPager.getCurrentItem() < mWinery.getCount() - 1){
				mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			}
			
			return true;
		}else if(item.getItemId() == MENU_PREVIOUS){
			if(mPager.getCurrentItem() > 0){
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
			
			return true;
		}else if(item.getItemId() == android.R.id.home){
			getActivity().finish();
			return true;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View root = inflater.inflate(R.layout.fragment_winery, container, false);
		
		AsyncTask<Object, Integer, Winery> wineryDownloader = new AsyncTask<Object, Integer, Winery>(){

			@Override
			protected Winery doInBackground(Object... params) {
				return Winery.getInstance();
			}
			
			@Override
			protected void onPostExecute(Winery winery) {
				loadUI(winery);
				mProgressDialog.dismiss();
			}
			
		};
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(R.string.downloading);
		if(!Winery.isInstanceAvailable()){
			mProgressDialog.show();
		}
		wineryDownloader.execute();
		
		return root;
	}
	
	private void loadUI(Winery winery) {
		mWinery = winery;
		
		mPager = (ViewPager)getView().findViewById(R.id.pager);
		mPager.setAdapter(new WineryPageAdapter(getFragmentManager()));
		mPager.setOnPageChangeListener(this);
		
		mActionBar = (ActionBar)((ActionBarActivity)getActivity()).getSupportActionBar();
		if(getArguments().getBoolean(ARG_SHOW_TABS)){
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			mActionBar.setDisplayHomeAsUpEnabled(true);
			
			for(int i = 0; i< mWinery.getCount(); i++){
				mActionBar.addTab(mActionBar.newTab()
				.setText(mWinery.getWine(i).getName())
				.setTabListener(this));
				
			}
		}
	
		int wineIndex = getArguments().getInt(ARG_WINE_INDEX);
		mPager.setCurrentItem(wineIndex);
		updateActionBar(wineIndex);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		updateActionBar(arg0);
		if(getArguments().getBoolean(ARG_SHOW_TABS)){
			mActionBar.setSelectedNavigationItem(arg0);
		}
		
		PreferenceManager.getDefaultSharedPreferences(getActivity())
		.edit()
		.putInt(PREF_LAST_WINE, arg0)
		.commit();
	}
	
	private void updateActionBar(int index){
		Wine currentWine = mWinery.getWine(index);
		mActionBar.setTitle(currentWine.getName());
		mActionBar.setIcon(new BitmapDrawable(getResources(),currentWine.getPhoto(getActivity())));
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		mPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void selectWine(int index){
		mPager.setCurrentItem(index);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getArguments().putInt(ARG_WINE_INDEX, mPager.getCurrentItem());
	}

}
