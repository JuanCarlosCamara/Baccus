package biz.agbo.baccus.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RatingBar;
import android.widget.TextView;
import biz.agbo.baccus.R;
import biz.agbo.baccus.controller.activity.SettingsActivity;
import biz.agbo.baccus.controller.activity.WebActivity;
import biz.agbo.baccus.model.Wine;

public class WineFragment extends Fragment {
	/**
	 * Atributos estáticos
	 */
	public static final int SETTINGS_REQUEST = 1;
	public static final String ARG_WINE = "biz.agbo.baccus.controller.fragment.WINE";
	private static final String STATE_WINE_IMAGE_SCALE_TYPE = "wineImageScaleType";
	
	/**
	 * Vistas
	 */
	private ImageView mWineImage = null;
	private TextView mWineNameText = null;
	private TextView mWineCompanyText = null;
	private TextView mWineTypeText = null;
	private TextView mWineOriginText = null;
	private ViewGroup mWineGrapesContainer = null;
	private TextView mWineNotesText = null;
	private RatingBar mWineRatingBar = null;
	
	/**
	 * Modelo
	 */
	private Wine mWine = null; 

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Aunque devuelve null por higiene llamamos a super.onCreateView
		super.onCreateView(inflater, container, savedInstanceState);

		// Recreamos las vistas desde el XML
		View root = inflater.inflate(R.layout.fragment_wine, container, false);
		
		mWine = (Wine)getArguments().getSerializable(ARG_WINE);
		
		// Asociamos controlador con vistas
		mWineImage = (ImageView)root.findViewById(R.id.wine_image);
		mWineNameText = (TextView)root.findViewById(R.id.wine_name);
		mWineTypeText = (TextView)root.findViewById(R.id.wine_type);
		mWineCompanyText = (TextView)root.findViewById(R.id.wine_company);
		mWineOriginText = (TextView)root.findViewById(R.id.wine_origin);
		mWineGrapesContainer = (ViewGroup)root.findViewById(R.id.grapes_container);
		mWineNotesText = (TextView)root.findViewById(R.id.wine_notes);
		mWineRatingBar = (RatingBar)root.findViewById(R.id.wine_rating);
		ImageButton webButton = (ImageButton)root.findViewById(R.id.go_to_web_button);
		
		mWineCompanyText = (TextView)root.findViewById(R.id.wine_company);
		mWineOriginText = (TextView)root.findViewById(R.id.wine_origin);
		mWineGrapesContainer = (ViewGroup)root.findViewById(R.id.grapes_container);
		
		// Aplicamos gomina al texto
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Valentina-Regular.otf");
		mWineNameText.setTypeface(font);
		mWineTypeText.setTypeface(font);
		mWineCompanyText.setTypeface(font);
		mWineOriginText.setTypeface(font);
		mWineNotesText.setTypeface(font);
		((TextView)root.findViewById(R.id.wine_type_subtitle)).setTypeface(font);
		((TextView)root.findViewById(R.id.wine_company_subtitle)).setTypeface(font);
		((TextView)root.findViewById(R.id.wine_origin_subtitle)).setTypeface(font);
		((TextView)root.findViewById(R.id.wine_grape_subtitle)).setTypeface(font);
		
		
		// Actualizamos la vista con el modelo
		mWineImage.setImageBitmap(mWine.getPhoto(getActivity()));
		
		// Si tenemos información previa del scaleType de la imagen del vino, lo actualizamos
		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_WINE_IMAGE_SCALE_TYPE)) {
			mWineImage.setScaleType((ScaleType)savedInstanceState.getSerializable(STATE_WINE_IMAGE_SCALE_TYPE));
		}
		
		mWineNameText.setText(mWine.getName());
		mWineTypeText.setText(mWine.getType());
		mWineCompanyText.setText(mWine.getWineCompanyName());
		mWineOriginText.setText(mWine.getOrigin());
		for (int i = 0; i < mWine.getGrapesCount(); i++) {
			TextView grapeText = new TextView(getActivity());
			grapeText.setText(mWine.getGrape(i));
			grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			grapeText.setTypeface(font);
			mWineGrapesContainer.addView(grapeText);
		}
		mWineNotesText.setText(mWine.getNotes());
		mWineRatingBar.setProgress(mWine.getRating());
		
		// Asociamos acciones a los botones
		webButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Creamos el intent que cargará la actividad WebActivity
				Intent webIntent = new Intent(getActivity(), WebActivity.class);
				
				// Le pasamos el modelo
				webIntent.putExtra(WebActivity.EXTRA_WINE, mWine);
				
				// Solicitamos cargar la actividad sin esperar datos de retorno
				startActivity(webIntent);
			}
		});
		
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Comprobamos las preferencias
		if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(SettingsFragment.PREF_IMAGE_SCALE_TYPE, null) != null) {
			mWineImage.setScaleType(ScaleType.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(SettingsFragment.PREF_IMAGE_SCALE_TYPE, null)));
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.wine, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_settings) {
			// Creamos el diálogo
			SettingsFragment settings = new SettingsFragment();
			
			// Le damos los valores iniciales
			Bundle arguments = new Bundle();
			arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
			settings.setArguments(arguments);
			
			// Indicamos quién es el listener para cuando el usuario seleccione una opción
			settings.setTargetFragment(this, SETTINGS_REQUEST);
			
			// Mostramos el diálogo
			settings.show(getFragmentManager(), null);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		/**
		 *  Sólo estamos interesados en resultados de la pantalla de ajustes
		 *  y sólo si el usuario pulsó "Guardar"
		 */
		if (requestCode == SETTINGS_REQUEST && resultCode == Activity.RESULT_OK) {
			// Actualizamos el valor de scaleType para la imagen del vino
			ScaleType scaleType = (ScaleType)data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
			mWineImage.setScaleType(scaleType);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mWineImage != null) {
			outState.putSerializable(STATE_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
		}
	}
}
