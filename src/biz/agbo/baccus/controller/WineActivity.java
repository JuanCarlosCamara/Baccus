package biz.agbo.baccus.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import biz.agbo.baccus.R;

public class WineActivity extends ActionBarActivity {
	
	private ImageView mWineImage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine);
		
		mWineImage = (ImageView)findViewById(R.id.wine_image);
		mWineImage.setImageResource(R.drawable.vegaval);
	}
}
