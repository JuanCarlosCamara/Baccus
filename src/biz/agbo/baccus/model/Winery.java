package biz.agbo.baccus.model;

import java.util.Arrays;
import java.util.List;

import biz.agbo.baccus.R;

public class Winery {
	
	private static Winery sInstance = null;
	
	private List<Wine> mWines = null;
	
	public static Winery getInstance(){
		if(sInstance == null){
			sInstance = new Winery();
		}
		
		return sInstance;
	}
	
	public Winery() {
		
		Wine bembibre = new Wine("Bembibre", "Tinto", R.drawable.bembibre, "http://www.dominiodetares.com/index.php/es/vinos/baltos/74-bembibrevinos", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Mencía", "El bierzo", 5, "Dominio de tares",
				Arrays.<String>asList(new String[]{"Mencía"}));
		
		Wine vegaval = new Wine("Vegaval", "Tinto", R.drawable.vegaval, "http://www.vegaval.com/es", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Tempranillo", "Valdepeñas", 5, "Miguel Calatayud",
				Arrays.<String>asList(new String[]{"Tempranillo"}));
		
		Wine zarate = new Wine("Zárate", "Blanco", R.drawable.zarate, "http://www.albarino-zarate.com", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Albariño", "Rías Bajas", 4, "Miguel Calatayud",
				Arrays.<String>asList(new String[]{"Albariño"}));
		
		Wine champagne = new Wine("Comtes de Champagne", "Otro", R.drawable.champagne, "http://www.taittinger.fr", 
				"Este vino muestra toda la complejidad y la elegancia de la variedad Chardonnay", "Champagne", 5, "Champagne taittinger",
				Arrays.<String>asList(new String[]{"Chardonnay"}));
		
		mWines = Arrays.asList(new Wine[]{bembibre, vegaval, zarate, champagne});
		
	}
	
	public Wine getWine(int index){
		return mWines.get(index);
	}
	
	public int getCount(){
		return mWines.size();
	}

}
