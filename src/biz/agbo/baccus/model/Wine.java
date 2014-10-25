package biz.agbo.baccus.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributos
	 */
	private String mName = null;
	private String mType = null;
	private int mPhoto = 0;
	private String mWineCompanyWeb = null;
	private String mNotes = null;
	private String mOrigin = null;
	private int mRating = 0; // 0 to 5
	private String mWineCompanyName = null;
	private List<String> mGrapes = new LinkedList<String>();

	public Wine(String name, String type, int photo, String wineCompanyWeb,
			String notes, String origin, int rating, String wineCompanyName,
			List<String> grapes) {
		super();
		mName = name;
		mType = type;
		mPhoto = photo;
		mWineCompanyWeb = wineCompanyWeb;
		mNotes = notes;
		mOrigin = origin;
		mRating = rating;
		mWineCompanyName = wineCompanyName;
		mGrapes = grapes;
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


	public int getPhoto() {
		return mPhoto;
	}


	public void setPhoto(int photo) {
		mPhoto = photo;
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
}
