package biz.agbo.baccus.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup;
import biz.agbo.baccus.R;

public class SettingsActivity extends Activity {
	
	public static final String EXTRA_WINE_IMAGE_SCALE_TYPE = "biz.agbo.baccus.extra.WINE_IMAGE_SCALE_TYPE";
	
	private RadioGroup mOptions = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		mOptions = (RadioGroup)findViewById(R.id.scale_type_radios);
		
		if(getIntent().getSerializableExtra(EXTRA_WINE_IMAGE_SCALE_TYPE).equals(ScaleType.FIT_XY)){
			mOptions.check(R.id.fit_radio);
		}else{
			mOptions.check(R.id.center_radio);
		}
	}
	
	public void save(View v){
		Intent config = new Intent();
		
		if (mOptions.getCheckedRadioButtonId() == R.id.fit_radio){
			config.putExtra(EXTRA_WINE_IMAGE_SCALE_TYPE, ScaleType.FIT_XY);
		}else{
			config.putExtra(EXTRA_WINE_IMAGE_SCALE_TYPE, ScaleType.FIT_CENTER);
		}
		
		setResult(RESULT_OK, config);
		
		finish();
	}
	
	public void cancel(View v){
		setResult(RESULT_CANCELED);
		
		finish();
	}

	
	
}
