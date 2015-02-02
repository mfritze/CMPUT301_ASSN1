/*
 * Copyright 2015 Matthew Fritze
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.school.cmput301.Activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.cmput301.R;
import com.school.cmput301.Controllers.Listener;
import com.school.cmput301.Fragments.ClaimListFragment;
import com.school.cmput301.Fragments.ClaimManagerFragment;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class MainActivity extends Activity {
	private FragmentManager fm;
	private FragmentTransaction ft;
	private ClaimListFragment claimListFragment;
	private ClaimManagerFragment claimManagerFragment;
	public final static String CLAIMINDEX = "com.school.cmput301.claimid";
	private static String FILENAME = "claimlist.sav";

	
	//Fragment management based on http://www.vogella.com/tutorials/AndroidFragments/article.html#usingfragments_layout Jan 30 2015
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
		setContentView(R.layout.activity_main);	
		fm = getFragmentManager();
		claimListFragment = new ClaimListFragment();
		claimManagerFragment = new ClaimManagerFragment();
		loadClaimList();
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				saveInFile();
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		changeToClaimList();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ClaimListSingleton.getClaimList().notifyListeners();
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
		
		ImageButton backButton = (ImageButton) findViewById(R.id.homeButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ft = fm.beginTransaction();
				ft.replace(R.id.mainFragmentHolder, new ClaimListFragment());
				ft.commit();
			}
		});
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
	
	public void changeToClaimList(){
		ClaimListSingleton.getClaimList().notifyListeners();
		
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder , this.claimListFragment);
		ft.commit();
	}
	
	public void createClaim(View v){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimManagerFragment);
		ft.commit();
		
		claimManagerFragment.setMode(false);
	}
	
	public void editClaim(int claimIndex){
		ft = fm.beginTransaction();
		ft.replace(R.id.mainFragmentHolder, this.claimManagerFragment);
		ft.commit();
		
		claimManagerFragment.setMode(true);
		claimManagerFragment.setClaimIndex(claimIndex);
	}
	
	public void startClaimEditor(){
		Intent intent = new Intent(this, ExpenseManagerActivity.class);
		int index = claimManagerFragment.createClaim();
		if(index != -1){
			intent.putExtra(CLAIMINDEX, index);
			startActivity(intent);
		}else{
			Toast.makeText(this, "Error creating claim", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void loadClaimList() {
		Gson gson = new Gson();
		ArrayList<Claim> claims = new ArrayList<Claim>();
		
		try {
			FileInputStream fis = openFileInput(FILENAME);
			//From http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan 22 2015
			Type listType = new TypeToken<ArrayList<Claim>>() {}.getType(); 
			InputStreamReader isr = new InputStreamReader(fis);
			claims = gson.fromJson(isr, listType);			
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(claims == null){
			claims = new ArrayList<Claim>();
		}
		ClaimListSingleton.getClaimList().setClaimArrayList(claims);
	}


	public void saveInFile() {
		Gson gson = new Gson();
		ArrayList<Claim> claims = ClaimListSingleton.getClaimList().getClaimArrayList();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
