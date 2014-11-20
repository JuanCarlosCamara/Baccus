package biz.agbo.baccus.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import biz.agbo.baccus.R;

@TargetApi(11)
public class Winery {
	
	private static Winery sInstance = null;
	private static final String sWinesURL = "http://baccusapp.herokuapp.com/wines";
	
	public static enum WineType{
		RED, WHITE, ROSE, OTHER
	}
	
	private List<Wine> mListWines = null;
	private HashMap<WineType, List<Wine>> mHashWines = null;
	
	public static Winery getInstance(){
		if(sInstance == null){
			try{
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				sInstance = downloadWines();
			}catch(Exception e){
				Log.d("BACCUS", "Error downloading wines", e);
				return null;
			}
			
		}
		
		return sInstance;
	}
	
	public static boolean isInstanceAvailable(){
		return (sInstance != null);
	}
	
	private Winery(){
		mListWines = new LinkedList<Wine>();
		mHashWines = new HashMap<Winery.WineType, List<Wine>>();
		for(WineType type : WineType.values()){
			mHashWines.put(type, new LinkedList<Wine>());
		}
	}
	
	private static Winery downloadWines() throws MalformedURLException, IOException, JSONException{
		Winery winery =  new Winery();
		URLConnection connection = new URL(sWinesURL).openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		
		String line = null;
		while((line = br.readLine()) != null){
			response.append(line);
		}
		
		br.close();
		
		JSONArray wines = new JSONArray(response.toString());
		
		for(int i = 0; i< wines.length(); i++){
			String id = null;
			String name = null;
			String type = null;
			String company = null;
			String companyWebsite = null;
			String notes = null;
			String picture = null;
			int rating = 0;
			String origin = null;
			List<String> grapes = new LinkedList<String>();
			
			JSONObject jSONWine = wines.getJSONObject(i);
			
			if(jSONWine.has("name")){
				id = jSONWine.getString("_id");
				name = jSONWine.getString("name");
				type = jSONWine.getString("type");
				company = jSONWine.getString("company");
				origin = jSONWine.getString("origin");
				companyWebsite = jSONWine.getString("company_web");
				notes = jSONWine.getString("notes");
				rating = jSONWine.getInt("rating");
				picture = jSONWine.getString("picture");
				
				JSONArray jSONGrapes = jSONWine.getJSONArray("grapes");
						
				for(int j = 0; j< jSONGrapes.length(); j++){
					grapes.add(jSONGrapes.getJSONObject(j).getString("grape"));
				}
				
				Wine newWine = new Wine(id, name, type, picture, companyWebsite, notes, origin, rating, company, grapes);
				
				if(type.equalsIgnoreCase("tinto")){
					winery.mHashWines.get(WineType.RED).add(newWine);
				}else if(type.equalsIgnoreCase("blanco")){
					winery.mHashWines.get(WineType.WHITE).add(newWine);
				}else if(type.equalsIgnoreCase("rosado")){
					winery.mHashWines.get(WineType.ROSE).add(newWine);
				}else{
					winery.mHashWines.get(WineType.OTHER).add(newWine);
				}
			}
			
		}
		
		for(WineType type : WineType.values()){
			List<Wine> wineList = winery.mHashWines.get(type);
			winery.mListWines.addAll(wineList);
		}
		
		return winery;
	}
	
//	public Winery() {
//		
//		Wine bembibre = new Wine("Bembibre", "Tinto", R.drawable.bembibre, "http://www.dominiodetares.com/index.php/es/vinos/baltos/74-bembibrevinos", 
//				"Este vino muestra toda la complejidad y la elegancia de la variedad Mencía", "El bierzo", 5, "Dominio de tares",
//				Arrays.<String>asList(new String[]{"Mencía"}));
//		
//		Wine vegaval = new Wine("Vegaval", "Tinto", R.drawable.vegaval, "http://www.vegaval.com/es", 
//				"Este vino muestra toda la complejidad y la elegancia de la variedad Tempranillo", "Valdepeñas", 5, "Miguel Calatayud",
//				Arrays.<String>asList(new String[]{"Tempranillo"}));
//		
//		Wine zarate = new Wine("Zárate", "Blanco", R.drawable.zarate, "http://www.albarino-zarate.com", 
//				"Este vino muestra toda la complejidad y la elegancia de la variedad Albariño", "Rías Bajas", 4, "Miguel Calatayud",
//				Arrays.<String>asList(new String[]{"Albariño"}));
//		
//		Wine champagne = new Wine("Comtes de Champagne", "Otro", R.drawable.champagne, "http://www.taittinger.fr", 
//				"Este vino muestra toda la complejidad y la elegancia de la variedad Chardonnay", "Champagne", 5, "Champagne taittinger",
//				Arrays.<String>asList(new String[]{"Chardonnay"}));
//		
//		mWines = Arrays.asList(new Wine[]{bembibre, vegaval, zarate, champagne});
//		
//	}
	
	public Wine getWine(WineType type, int index){
		return mHashWines.get(type).get(index);
	}
	
	public int getWineCount(WineType type){
		return mHashWines.get(type).size();
	}
	
	public int getAbsolutePosition(WineType type, int relativePosition){
		Wine wine = getWine(type,relativePosition);
		return mListWines.indexOf(wine);
		
	}
	
	public Wine getWine(int index){
		return mListWines.get(index);
	}
	
	public int getCount(){
		return mListWines.size();
	}
	
	public List<Wine> getWineList(){
		return mListWines;
	}

}
