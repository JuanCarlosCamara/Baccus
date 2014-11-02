package biz.agbo.baccus.controller.activity;

import android.support.v4.app.Fragment;
import biz.agbo.baccus.controller.fragment.WineryFragment;
import biz.agbo.controller.activity.FragmentContainerActivity;

public class WineryActivity extends FragmentContainerActivity {

	@Override
	public Fragment createFragment() {
		// TODO Auto-generated method stub
		return new WineryFragment();
	}
}
