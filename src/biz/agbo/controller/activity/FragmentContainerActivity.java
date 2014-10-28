package biz.agbo.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import biz.agbo.baccus.R;

public abstract class FragmentContainerActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment_container);
		
		FragmentManager fm = getSupportFragmentManager();
		
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if(fragment == null){
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
		}
	}

	public abstract Fragment createFragment();
	
	

}
