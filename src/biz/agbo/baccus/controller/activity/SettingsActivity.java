package biz.agbo.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import biz.agbo.baccus.controller.fragment.SettingsFragment;

public class SettingsActivity extends FragmentContainerActivity {
	
	public static final String EXTRA_WINE_IMAGE_SCALE_TYPE = "biz.agbo.baccus.extra.WINE_IMAGE_SCALE_TYPE";
	
	@Override
	public Fragment createFragment() {
		Bundle arguments = new Bundle();
		arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE, getIntent().getSerializableExtra(EXTRA_WINE_IMAGE_SCALE_TYPE));
		SettingsFragment fragment = new SettingsFragment();
		fragment.setArguments(arguments);
		return fragment;
	}
}
