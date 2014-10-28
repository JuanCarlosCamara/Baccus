package biz.agbo.baccus.controller.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.fragment.WebFragment;
import biz.agbo.baccus.model.Wine;
import biz.agbo.controller.activity.FragmentContainerActivity;

public class WebActivity extends FragmentContainerActivity {

	public final static String EXTRA_WINE = "biz.agbo.baccus.extra.activity.WINE";
	
	@Override
	public Fragment createFragment() {
		Bundle arguments = new Bundle();
		arguments.putSerializable(WebFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));
		WebFragment fragment = new WebFragment();
		fragment.setArguments(arguments);
		
		return fragment;
	}
	
	
}
