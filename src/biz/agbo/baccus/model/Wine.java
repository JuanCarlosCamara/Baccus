package biz.agbo.baccus.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

public class Wine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributos
	 */
	private String mId = null;
	private String mName = null;
	private String mType = null;
	private String mPhotoURL = null;
	private String mWineCompanyWeb = null;
	private String mNotes = null;
	private String mOrigin = null;
	private int mRating = 0; // 0 to 5
	private String mWineCompanyName = null;
	private List<String> mGrapes = new LinkedList<String>();

	public Wine(String id, String name, String type, String photoURL, String wineCompanyWeb,
			String notes, String origin, int rating, String wineCompanyName,
			List<String> grapes) {
		super();
		mId = id;
		mName = name;
		mType = type;
		mPhotoURL = photoURL;
		mWineCompanyWeb = wineCompanyWeb;
		mNotes = notes;
		mOrigin = origin;
		mRating = rating;
		mWineCompanyName = wineCompanyName;
		mGrapes = grapes;
	}

	public String getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}


	public void setName(String name) {
		mName = name;
	}


	public String getType() {
		return mType;
	}


	public void setType(String type) {
		mType = type;
	}

	public String getPhotoURL(){
		return mPhotoURL;
	}

	public Bitmap getPhoto(Context context) {
		// Nos bajamos la photo
		return getBitmapFromURL(getPhotoURL(), context);
	}
	@TargetApi(11)
	public Bitmap getBitmapFromURL(String photoURL, Context context){
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		File imageFile = new File(context.getCacheDir(), mId);
		
		if(imageFile.exists()){
			return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		}
		InputStream in = null;
		try{
			in = new URL(photoURL).openStream();
			Bitmap bmp = BitmapFactory.decodeStream(in);
			FileOutputStream fos = new FileOutputStream(imageFile);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.close();
			return bmp;
		}catch(Exception e){
			Log.e("BACCUS", "Error downloading image", e);
			return null;
		}finally{
			try{
				if(in != null){
					in.close();
				}
			}catch(Exception ex){}
		}
	}


	public void setPhotoURL(String photoURL) {
		mPhotoURL = photoURL;
	}


	public String getWineCompanyWeb() {
		return mWineCompanyWeb;
	}


	public void setWineCompanyWeb(String wineCompanyWeb) {
		mWineCompanyWeb = wineCompanyWeb;
	}


	public String getNotes() {
		return mNotes;
	}


	public void setNotes(String notes) {
		mNotes = notes;
	}


	public String getOrigin() {
		return mOrigin;
	}


	public void setOrigin(String origin) {
		mOrigin = origin;
	}


	public int getRating() {
		return mRating;
	}


	public void setRating(int rating) {
		mRating = rating;
	}


	public String getWineCompanyName() {
		return mWineCompanyName;
	}


	public void setWineCompanyName(String wineCompanyName) {
		mWineCompanyName = wineCompanyName;
	}
	
	public String getGrape(int index){
		return mGrapes.get(index);
	}
	
	public int getGrapesCount(){
		return mGrapes.size();
	}
	
	/*
	 * Método para añadir una uva
	 */
	public void addGrape(String grape){
		mGrapes.add(grape);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
