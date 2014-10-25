package biz.agbo.baccus.controller;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

public class WineActivity extends ActionBarActivity {
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine);
		
		// Creamos el modelo
		mWine = new Wine("Bembibre", "Tinto", R.drawable.bembibre, "http://www.dominiodetares.com/web/esp/vino.php?id=4", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Mencía", "El bierzo", 5, "Dominio de tares",
				Arrays.<String>asList(new String[]{"Mencía"}));
		
		
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
	}
	
	public void changeImage(View v){
		mWineImage.setImageResource(mWine.getPhoto());
	}
}
