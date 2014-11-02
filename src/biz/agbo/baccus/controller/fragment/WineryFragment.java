package biz.agbo.baccus.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import biz.agbo.baccus.R;
import biz.agbo.baccus.adapter.WineryPageAdapter;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;

public class WineryFragment extends Fragment {
	
	private ViewPager mPager = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		Winery winery = Winery.getInstance();
		
		View root = inflater.inflate(R.layout.fragment_winery, container, false);
		
		mPager = (ViewPager)root.findViewById(R.id.pager);
		mPager.setAdapter(new WineryPageAdapter(getFragmentManager()));
		
		return root;
	}

}
