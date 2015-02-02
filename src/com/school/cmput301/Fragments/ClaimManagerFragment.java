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
package com.school.cmput301.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.school.cmput301.R;
import com.school.cmput301.Activities.MainActivity;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ClaimManagerFragment extends Fragment{
	/*
	 * ClaimManagerFragment works with the creation
	 * and editing of Claims. The layout is the same 
	 * between the two, but the fields are filled
	 * when you edit.
	 * If you create a claim, the submit button will
	 * take you to create expenses for it, while if you're
	 * editing the claim, it will bring you back
	 * to the ClaimListFragment. 
	 */
	private EditText nameView;
	private EditText categoryView;
	private EditText descriptionView;
	private DatePicker dateStartView; 
	private DatePicker dateEndView;
	private Button submitButton;
	private int claimIndex;
	private boolean isEditing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claim_manager_layout, container,  false);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		nameView = (EditText) getView().findViewById(R.id.claimNameEditText);
		categoryView = (EditText) getView().findViewById(R.id.claimCategoryEditText);
		descriptionView  = (EditText) getView().findViewById(R.id.claimDescriptionEditText);
		dateStartView = (DatePicker) getView().findViewById(R.id.claimStartDatePicker);
		dateEndView = (DatePicker) getView().findViewById(R.id.claimEndDatePicker);
		submitButton = (Button) getView().findViewById(R.id.submitClaim);
		if(isEditing){
			populateEditingFields();
		}else{
			setCreateButton();
		}
	}

	public int createClaim(){
		String name,category,description, startDateString, endDateString;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //http://stackoverflow.com/questions/17674308/date-from-edittext Jan 18 2015
		Date startDate = null;
		
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
				
				Claim managingClaim = new Claim(name, category, description, startDateString, endDateString, startDate);
				ClaimListSingleton.getClaimList().addClaim(managingClaim);
				ClaimListSingleton.sortClaimList();
				
				return ClaimListSingleton.getClaimList().getIndex(managingClaim);
			} catch (ParseException e) {
				Toast.makeText(getActivity(), "Error reading date", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
		}
		return -1;		
	}
	
	public void setMode(boolean isEditing){
		this.isEditing = isEditing;
	}
	
	public void setCreateButton(){
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).startClaimEditor();
			}
		});
		nameView.setText("");
		categoryView.setText("");
		descriptionView.setText("");
	}
	
	public void populateEditingFields(){
		final Claim editClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimIndex);
		if(editClaim != null){
			this.isEditing = true;
			
			nameView.setText(editClaim.getName());
			categoryView.setText(editClaim.getCategory());
			descriptionView.setText(editClaim.getDescription());
			final String originalStartDateValue = String.format("%d/%d/%d", 
					dateStartView.getDayOfMonth(), dateStartView.getMonth() + 1,dateStartView.getYear());

			
			submitButton.setOnClickListener(new View.OnClickListener() {							
				@Override
				public void onClick(View v) {
					int smonth = dateStartView.getMonth() + 1, sday = dateStartView.getDayOfMonth(), syear = dateStartView.getYear();
					int emonth = dateEndView.getMonth() + 1, eday = dateEndView.getDayOfMonth(), eyear = dateEndView.getYear();
					String startDateString, endDateString, name, category, description;
					Claim newClaim = editClaim;
					
					Date startDate;
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					startDateString = String.format("%d/%d/%d", sday,smonth, syear);
					endDateString = String.format("%d/%d/%d", eday,emonth, eyear);
					name = nameView.getText().toString();
					description = descriptionView.getText().toString();
					category = categoryView.getText().toString();
					
					if(!name.equals(editClaim.getName())){
						newClaim.setName(name);
					}
					if(!category.equals(editClaim.getCategory())){
						newClaim.setCategory(category);
					}
					if(!description.equals(editClaim.getDescription())){
						newClaim.setDescription(description);
					}
					if(!startDateString.equals(originalStartDateValue)){
						newClaim.setStartDate(startDateString);
						try {
							startDate = dateFormat.parse(startDateString);
							newClaim.setStartTime(startDate);
						} catch (ParseException e) {
							Toast.makeText(getActivity(),"Error reading date!",Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
					if(!endDateString.equals(editClaim.getEndDate())){
						editClaim.setEndDate(endDateString);
					}
					ClaimListSingleton.getClaimList().replaceClaim(claimIndex, newClaim);
					((MainActivity) getActivity()).changeToClaimList();

				}
			});
		}else{
			Toast.makeText(getActivity(), "Empty claim!", Toast.LENGTH_SHORT).show();
		}
	}

	public void setClaimIndex(int index) {
		this.claimIndex = index;
	}

}
