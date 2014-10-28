package biz.agbo.baccus.controller.fragment;

import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.activity.SettingsActivity;
import biz.agbo.baccus.controller.activity.WebActivity;
import biz.agbo.baccus.model.Wine;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class WineFragment extends Fragment {

	public static final int SETTINGS_REQUEST = 1;
	public static final String ARG_WINE = "biz.agbo.baccus.controller.fragment.WINE";
	private static final String STATE_WINE_IMAGE_SCALE_TYPE = "wineImageScaleType";
	
	private Wine mWine = null;
	
	//Vistas
	private ImageView mWineImage = null;
	private TextView mWineNameText = null;
	private TextView mWineTypeText = null;
	private TextView mWineOriginText = null;
	private RatingBar mWineRatingBar = null;
	private TextView mWineCompanyText = null;
	private TextView mWineNotesText = null;
	private ViewGroup mWineGrapesContainer = null;
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.settings, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_settings){
			Intent settings = new Intent(getActivity(), SettingsActivity.class);
			settings.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
			startActivityForResult(settings, SETTINGS_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		
		View root = inflater.inflate(R.layout.fragment_wine, container, false);
		// Creamos el modelo
		
		mWine = (Wine) getArguments().getSerializable(ARG_WINE);
		
		
		// Accedemos a las vistas desde este controlador
		mWineImage = (ImageView)root.findViewById(R.id.wine_image);
		mWineNameText = (TextView)root.findViewById(R.id.wine_name);
		mWineTypeText = (TextView)root.findViewById(R.id.wine_type);
		mWineOriginText = (TextView)root.findViewById(R.id.wine_origin);
		mWineRatingBar = (RatingBar)root.findViewById(R.id.wine_rating);
		mWineCompanyText = (TextView)root.findViewById(R.id.wine_company);
		mWineNotesText = (TextView)root.findViewById(R.id.wine_notes);
		mWineGrapesContainer = (ViewGroup)root.findViewById(R.id.grapes_container);
		
		// Damos valor a las vistas con el modelo
		
		mWineImage.setImageResource(mWine.getPhoto());
		mWineNameText.setText(mWine.getName());
		mWineTypeText.setText(mWine.getType());
		mWineOriginText.setText(mWine.getOrigin());
		mWineRatingBar.setRating(mWine.getRating());
		mWineCompanyText.setText(mWine.getWineCompanyName());
		mWineNotesText.setText(mWine.getNotes());
		
		// Creamos la lista de uvas
		for(int i = 0; i<mWine.getGrapesCount(); i++){
			TextView grapeText = new TextView(getActivity());
			grapeText.setText(mWine.getGrape(i));
			grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			mWineGrapesContainer.addView(grapeText);
		}
		
		if(savedInstanceState != null && savedInstanceState.containsKey(STATE_WINE_IMAGE_SCALE_TYPE)){
			mWineImage.setScaleType((ScaleType) savedInstanceState.getSerializable(STATE_WINE_IMAGE_SCALE_TYPE));
		}
		
		return root;
	}
	
	public void changeImage(View v){
		mWineImage.setImageResource(mWine.getPhoto());
	}
	
	public void goToWeb(View v){
		Intent webIntent = new Intent(getActivity(), WebActivity.class);
		webIntent.putExtra(WebActivity.EXTRA_WINE, mWine);
		startActivity(webIntent);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SETTINGS_REQUEST && resultCode == Activity.RESULT_OK){
			ScaleType scaleType = (ScaleType)data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
			mWineImage.setScaleType(scaleType);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(STATE_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
	}
	
}
