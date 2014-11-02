package biz.agbo.baccus.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup;
import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.activity.SettingsActivity;

public class SettingsFragment extends Fragment implements OnClickListener {
	
	public static final String ARG_WINE_IMAGE_SCALE_TYPE = "biz.agbo.baccus.fragment.WINE_IMAGE_SCALE_TYPE";
	
	private RadioGroup mOptions = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View root = inflater.inflate(R.layout.fragment_settings, container, false);
		
		mOptions = (RadioGroup)root.findViewById(R.id.scale_type_radios);
		
		if(getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ScaleType.FIT_XY)){
			mOptions.check(R.id.fit_radio);
		}else{
			mOptions.check(R.id.center_radio);
		}
		
		Button saveButton = (Button)root.findViewById(R.id.save_button);
		Button cancelButton = (Button)root.findViewById(R.id.cancel_button);
		
		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		
		return root;
	}
	
	public void save(View v){
		Intent config = new Intent();
		
		if (mOptions.getCheckedRadioButtonId() == R.id.fit_radio){
			config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, ScaleType.FIT_XY);
		}else{
			config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, ScaleType.FIT_CENTER);
		}
		
		getActivity().setResult(Activity.RESULT_OK, config);
		
		getActivity().finish();
	}
	
	public void cancel(View v){
		getActivity().setResult(Activity.RESULT_CANCELED);
		
		getActivity().finish();
	}

	@Override
	public void onClick(View button) {
		switch (button.getId()) {
		case R.id.save_button:
			save(button);
			break;
		case R.id.cancel_button:
			cancel(button);
			break;
		default:
			break;
		}
	}

}
