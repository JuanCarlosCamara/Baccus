package biz.agbo.baccus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.TextView;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

public class WineActivity extends ActionBarActivity {
	
	public static final int SETTINGS_REQUEST = 1;
	public static final String EXTRA_WINE = "biz.agbo.baccus.controller.WINE";
	private static final String STATE_WINE_IMAGE_SCALE_TYPE = "wineImageScaleType";
	private static final String TAG = "biz.agbo.baccus.log.WineActivity";
	
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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_settings){
			Intent settings = new Intent(this, SettingsActivity.class);
			settings.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
			startActivityForResult(settings, SETTINGS_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine);
		
		// Creamos el modelo
		mWine = (Wine) getIntent().getSerializableExtra(EXTRA_WINE);
		
		
		// Accedemos a las vistas desde este controlador
		mWineImage = (ImageView)findViewById(R.id.wine_image);
		mWineNameText = (TextView)findViewById(R.id.wine_name);
		mWineTypeText = (TextView)findViewById(R.id.wine_type);
		mWineOriginText = (TextView)findViewById(R.id.wine_origin);
		mWineRatingBar = (RatingBar)findViewById(R.id.wine_rating);
		mWineCompanyText = (TextView)findViewById(R.id.wine_company);
		mWineNotesText = (TextView)findViewById(R.id.wine_notes);
		mWineGrapesContainer = (ViewGroup)findViewById(R.id.grapes_container);
		
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
			TextView grapeText = new TextView(this);
			grapeText.setText(mWine.getGrape(i));
			grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			mWineGrapesContainer.addView(grapeText);
		}
		
		if(savedInstanceState != null && savedInstanceState.containsKey(STATE_WINE_IMAGE_SCALE_TYPE)){
			mWineImage.setScaleType((ScaleType) savedInstanceState.getSerializable(STATE_WINE_IMAGE_SCALE_TYPE));
		}
	}
	
	public void changeImage(View v){
		mWineImage.setImageResource(mWine.getPhoto());
	}
	
	public void goToWeb(View v){
		Intent webIntent = new Intent(this, WebActivity.class);
		webIntent.putExtra(WebActivity.EXTRA_WINE, mWine);
		startActivity(webIntent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SETTINGS_REQUEST && resultCode == RESULT_OK){
			ScaleType scaleType = (ScaleType)data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
			mWineImage.setScaleType(scaleType);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(STATE_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
	}
}
