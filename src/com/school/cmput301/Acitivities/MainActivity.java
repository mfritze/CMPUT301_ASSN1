package com.school.cmput301.Acitivities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.school.cmput301.ClaimEditorActivity;
import com.school.cmput301.R;
import com.school.cmput301.Fragments.ClaimCreatorFragment;
import com.school.cmput301.Fragments.ClaimManagerFragment;

public class MainActivity extends Activity {
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClaimManagerFragment claimManagerFragment;
	private ClaimCreatorFragment claimCreatorFragment;
	private final static String CLAIMINDEX = "com.school.cmput301.claimid";
	
	
	//Fragment management based on http://www.vogella.com/tutorials/AndroidFragments/article.html#usingfragments_layout Jan 30 2015
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
		setContentView(R.layout.activity_main);	

		fm = getFragmentManager();
		claimManagerFragment = new ClaimManagerFragment();
		claimCreatorFragment = new ClaimCreatorFragment();
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		ft = fm.beginTransaction();
		ft.add(R.id.mainFragmentHolder , this.claimManagerFragment);
		ft.commit();
	}

	
	private void setActionBar(){
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.actionbar_layout, null);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		//Based on http://stackoverflow.com/questions/6746665/accessing-a-font-under-assets-folder-from-xml-file-in-android Jan 25 2015
		TextView menuTitle = (TextView) findViewById(R.id.menuTitle);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto/Roboto-Light.ttf");
		menuTitle.setTypeface(tf);
	}
	
	@Override
 	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void launchClaimEditor(View v){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimCreatorFragment);
		ft.commit();
	}
	
	public void startExpenseManager(View v){
		Intent intent = new Intent(this, ClaimEditorActivity.class);
		int index = claimCreatorFragment.startClaimEditor();
		intent.putExtra(CLAIMINDEX, index);
		startActivity(intent);
	}

}
