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
import com.school.cmput301.Acitivities.MainActivity;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ClaimManagerFragment extends Fragment{
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
	
	
//	private void setFonts(){
//		TextView startDate = (TextView) getView().findViewById(R.id.startDate);
//		TextView endDate = (TextView) getView().findViewById(R.id.endDate);
//		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto/Roboto-Medium.ttf");
//		startDate.setTypeface(tf);
//		endDate.setTypeface(tf);
//	}
	
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

	public int startClaimEditor(){
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

	public void setClaim(int index) {
		this.claimIndex = index;
	}

}
