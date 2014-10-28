package biz.agbo.baccus.controller.activity;

import java.util.Arrays;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

@SuppressWarnings("deprecation")
public class WineryActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winery);
		
		Wine bembibre = new Wine("Bembibre", "Tinto", R.drawable.bembibre, "http://www.dominiodetares.com/index.php/es/vinos/baltos/74-bembibrevinos", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Mencía", "El bierzo", 5, "Dominio de tares",
				Arrays.<String>asList(new String[]{"Mencía"}));
		
		Wine vegaval = new Wine("Vegaval", "Tinto", R.drawable.vegaval, "http://www.vegaval.com/es", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Tempranillo", "Valdepeñas", 5, "Miguel Calatayud",
				Arrays.<String>asList(new String[]{"Tempranillo"}));
		
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, WineActivity.class);
		intent.putExtra(WineActivity.EXTRA_WINE, bembibre);
		TabHost.TabSpec spec = tabHost.newTabSpec(bembibre.getName())
				.setIndicator(bembibre.getName())
				.setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent(this, WineActivity.class);
		intent.putExtra(WineActivity.EXTRA_WINE, vegaval);
		spec = tabHost.newTabSpec(vegaval.getName())
				.setIndicator(vegaval.getName())
				.setContent(intent);
		tabHost.addTab(spec);
	}
}
