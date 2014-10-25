package biz.agbo.baccus.controller;

import java.util.Arrays;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import biz.agbo.baccus.R;
import biz.agbo.baccus.model.Wine;

public class WebActivity extends Activity {
	
	public final static String EXTRA_WINE = "biz.agbo.baccus.extra.WINE";
	
	private WebView mBrowser = null;
	private ProgressBar mLoading = null;
	private Wine mWine = null;
	private static String STATE_URL;
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(STATE_URL, mBrowser.getUrl());
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.web, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_reload){
			mBrowser.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
	
		
		// Accedo a las vistas
		mBrowser = (WebView)findViewById(R.id.browser);
		mLoading = (ProgressBar)findViewById(R.id.loading);
		
		// Creo el modelo
		
		mWine = (Wine)getIntent().getSerializableExtra(EXTRA_WINE);
		
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
	};
}
