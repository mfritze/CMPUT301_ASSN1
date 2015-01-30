package com.school.cmput301.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.school.cmput301.ClaimEditorActivity;
import com.school.cmput301.R;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ClaimCreatorFragment extends Fragment{
	private EditText nameView;
	private EditText categoryView;
	private EditText descriptionView;
	private DatePicker dateStartView; 
	private DatePicker dateEndView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		nameView = (EditText) getView().findViewById(R.id.claimNameEditText);
		categoryView = (EditText) getView().findViewById(R.id.claimCategoryEditText);
		descriptionView  = (EditText) getView().findViewById(R.id.claimDescriptionEditText);
		dateStartView = (DatePicker) getView().findViewById(R.id.claimStartDatePicker);
		dateEndView = (DatePicker) getView().findViewById(R.id.claimEndDatePicker);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.create_claim, container,  false);
		return v;
	}
	
	
//	private void setFonts(){
//		TextView startDate = (TextView) getView().findViewById(R.id.startDate);
//		TextView endDate = (TextView) getView().findViewById(R.id.endDate);
//		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto/Roboto-Medium.ttf");
//		startDate.setTypeface(tf);
//		endDate.setTypeface(tf);
//	}
	
	public int startClaimEditor(){
		String name,category,description, startDateString, endDateString;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //http://stackoverflow.com/questions/17674308/date-from-edittext Jan 18 2015
		Date startDate = null;
		Claim claim = null;
		
		//dateString = dateView.getText().toString();
		name = nameView.getText().toString();
		category = categoryView.getText().toString();
		description = descriptionView.getText().toString();
	
		
		if((name.equals("")) || (category.equals("")) || (description.equals("")) ){
			Toast.makeText(getActivity(), "Fill in all of the fields!", Toast.LENGTH_SHORT).show();
		}else{
		
			try {
				//Month is the only one indexed from 0
				int smonth = dateStartView.getMonth() + 1, sday = dateStartView.getDayOfMonth(), syear = dateStartView.getYear();
				int emonth = dateEndView.getMonth() + 1, eday = dateEndView.getDayOfMonth(), eyear = dateEndView.getYear();
				startDateString = String.format("%d/%d/%d", sday,smonth, syear);
				endDateString = String.format("%d/%d/%d", eday,emonth, eyear);
				startDate = dateFormat.parse(startDateString);
				//endDate = dateFormat.parse(endDateString);
				//date = dateFormat.parse(dateString);
				
				claim = new Claim(name, category, description, startDateString, endDateString, startDate);
				ClaimListSingleton.getClaimList().addClaim(claim);
				ClaimListSingleton.sortClaimList();
				ClaimListSingleton.getClaimList().notifyListeners();
				return ClaimListSingleton.getClaimList().getIndex(claim);
			} catch (ParseException e) {
				Toast.makeText(getActivity(), "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
		}
		return -1;		

	}


}
