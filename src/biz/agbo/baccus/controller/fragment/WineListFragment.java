package biz.agbo.baccus.controller.fragment;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;
import biz.agbo.baccus.model.Winery.WineType;

public class WineListFragment extends Fragment{
	
	private ProgressDialog mProgressDialog = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View root = inflater.inflate(R.layout.fragment_wine_list, container, false);
		
		
		AsyncTask<Object, Integer, Winery> wineryDownloader = new AsyncTask<Object, Integer, Winery>(){

			@Override
			protected Winery doInBackground(Object... params) {
				return Winery.getInstance();
			}
			
			@Override
			protected void onPostExecute(Winery winery) {
				ExpandableListView listView = (ExpandableListView)getView().findViewById(android.R.id.list);
				//ArrayAdapter<Wine> arrayAdapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());
				WineListAdapter adapter = new WineListAdapter(getActivity(), winery);
				listView.setAdapter(adapter);
				listView.setOnChildClickListener((OnChildClickListener)getActivity());
				mProgressDialog.dismiss();
			}
			
		};
		
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle(R.string.downloading);
		
		if(!Winery.isInstanceAvailable()){
			mProgressDialog.show();
		}
		
		wineryDownloader.execute();
		
		return root;
	}
	
	class WineListAdapter extends BaseExpandableListAdapter{
		
		private Context mContext = null;
		private Winery mWinery = null;
		
		public WineListAdapter(Context context, Winery winery){
			mContext = context;
			mWinery = winery;
		}
		
		@Override
		public View getChildView(int groupPosition, int position, boolean arg2, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View wineRow = inflater.inflate(R.layout.wine_list_item, parent, false);
			TextView wineNameText = (TextView)wineRow.findViewById(R.id.wine_name);
			TextView wineCompanyText = (TextView)wineRow.findViewById(R.id.wine_company);
			ImageView wineImage = (ImageView)wineRow.findViewById(R.id.wine_image);
			
			Wine currentWine = getChild(groupPosition, position);
			
			wineNameText.setText(currentWine.getName());
			wineCompanyText.setText(currentWine.getWineCompanyName());
			wineImage.setImageBitmap(currentWine.getPhoto(mContext));
			
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Valentina-Regular.otf");
			wineNameText.setTypeface(tf);
			wineCompanyText.setTypeface(tf);
			
			return wineRow;
		}

		@Override
		public int getGroupCount() {
			return Winery.WineType.values().length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mWinery.getWineCount(getGroup(groupPosition));
		}

		@Override
		public Winery.WineType getGroup(int groupPosition) {
			return WineType.values()[groupPosition];
		}

		@Override
		public Wine getChild(int groupPosition, int childPosition) {
			return mWinery.getWine(getGroup(groupPosition),childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View wineHeader = inflater.inflate(R.layout.wine_list_header, parent, false);
			
			TextView headerText = (TextView)wineHeader.findViewById(R.id.wine_type);
			
			if(getGroup(groupPosition) == WineType.RED){
				headerText.setText("Tinto");
			}else if(getGroup(groupPosition) == WineType.WHITE){
				headerText.setText("Blanco");
			}else if(getGroup(groupPosition) == WineType.ROSE){
				headerText.setText("Rosado");
			}else if(getGroup(groupPosition) == WineType.OTHER){
				headerText.setText("Otro");
			}
			
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Valentina-Regular.otf");
			headerText.setTypeface(tf);
			
			return wineHeader;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
