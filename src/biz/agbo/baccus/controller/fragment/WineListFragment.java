package biz.agbo.baccus.controller.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;
import biz.agbo.baccus.model.Winery;

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
				ListView listView = (ListView)getView().findViewById(android.R.id.list);
				ArrayAdapter<Wine> arrayAdapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());
				listView.setAdapter(arrayAdapter);
				listView.setOnItemClickListener((OnItemClickListener)getActivity());
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
}
