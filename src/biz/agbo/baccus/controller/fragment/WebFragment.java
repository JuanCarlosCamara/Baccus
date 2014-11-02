package biz.agbo.baccus.controller.fragment;

import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebFragment extends Fragment {
	
	public final static String ARG_WINE = "biz.agbo.baccus.extra.fragment.WINE";
	
	private WebView mBrowser = null;
	private ProgressBar mLoading = null;
	private Wine mWine = null;
	private static String STATE_URL;
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(STATE_URL, mBrowser.getUrl());
	}

	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.web, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_reload){
			mBrowser.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}



	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		
		View root = inflater.inflate(R.layout.fragment_web, container, false);
		
		// Creo el modelo
		
		mWine = (Wine)getArguments().getSerializable(ARG_WINE);
		
		// Accedo a las vistas
		mBrowser = (WebView)root.findViewById(R.id.browser);
		mLoading = (ProgressBar)root.findViewById(R.id.loading);
		
		
		
		// Configurar el browser
		
		mBrowser.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mLoading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mLoading.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				mLoading.setVisibility(View.GONE);
			}
			
		});
		
		mBrowser.getSettings().setBuiltInZoomControls(true);
		
		if(savedInstanceState == null || !savedInstanceState.containsKey(STATE_URL)){
			mBrowser.loadUrl(mWine.getWineCompanyWeb());
		}else{
			mBrowser.loadUrl(savedInstanceState.getString(STATE_URL));
		}
		
		return root;
	}
}
