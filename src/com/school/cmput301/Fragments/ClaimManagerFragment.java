package com.school.cmput301.Fragments;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.school.cmput301.ClaimEditorActivity;
import com.school.cmput301.R;
import com.school.cmput301.Controllers.ClaimAdapter;
import com.school.cmput301.Controllers.Listener;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;

public class ClaimManagerFragment extends Fragment {
	private ClaimAdapter claimAdapter;
	private ArrayList<Claim> claims;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claim_manager, container, false);
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Collection<Claim> claimCollection = ClaimListSingleton.getClaimList().getClaims();
		claims = new ArrayList<Claim>(claimCollection);
		this.claimAdapter = new ClaimAdapter(getActivity(), R.layout.claim_adapter, this.claims);
	}
	
	private void addListeners(){
		ClaimListSingleton.getClaimList().addListener(new Listener(){
			@Override
			public void update(){
				claims.clear();
				Collection<Claim> newClaims = ClaimListSingleton.getClaimList().getClaims();
				claims.addAll(newClaims);
				claimAdapter.notifyDataSetChanged();
			}
		});
	}
	

	private void manageListAdapter(){
		final ListView claimView = (ListView) getView().findViewById(R.id.claimListView);

		claimView.setAdapter(claimAdapter);

		claimView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Toast.makeText(getActivity(),"On Click!", Toast.LENGTH_SHORT).show();
			}
		});
		
		claimView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View popupView = inflater.inflate(R.layout.edit_claim_popup, null);

				final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				
				ImageButton deleteClaim = (ImageButton) popupView.findViewById(R.id.deleteClaim);
				ImageButton sendClaim = (ImageButton) popupView.findViewById(R.id.sendClaim);
				ImageButton approveClaim = (ImageButton) popupView.findViewById(R.id.approvedClaim);
				ImageButton editClaim = (ImageButton) popupView.findViewById(R.id.editClaim);
				
				final int claimPosition = position;
				
				deleteClaim.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ClaimListSingleton.getClaimList().removeClaimAtIndex(claimPosition);
						ClaimListSingleton.getClaimList().notifyListeners();
						window.dismiss();
					}
				});
				
				sendClaim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//TODO open email client
						Claim c = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
						ClaimListSingleton.getClaimList().submitClaim(c);
						ClaimListSingleton.getClaimList().notifyListeners();
						window.dismiss();
					}
				});
				
				approveClaim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Claim c = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
						ClaimListSingleton.getClaimList().approveClaim(c);
						ClaimListSingleton.getClaimList().notifyListeners();
						window.dismiss();
					}
				});
				//TODO
//				editClaim.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						final Claim editingClaim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
//						window.dismiss();
//						if(editingClaim.isEditable()){
//							LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//							View popupView = inflater.inflate(R.layout.create_claim, null);
//							final PopupWindow editClaimWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 
//							final EditText nameView = (EditText) popupView.findViewById(R.id.claimNameEditText);
//							final EditText categoryView = (EditText) popupView.findViewById(R.id.claimCategoryEditText);
//							final EditText descriptionView = (EditText) popupView.findViewById(R.id.claimDescriptionEditText);
//							final DatePicker dateStartView = (DatePicker) popupView.findViewById(R.id.claimStartDatePicker);
//							final DatePicker dateEndView = (DatePicker) popupView.findViewById(R.id.claimEndDatePicker);
//							Button submitButton = (Button) popupView.findViewById(R.id.submitNewClaim);
//							
//							nameView.setText(editingClaim.getName());
//							categoryView.setText(editingClaim.getCategory());
//							descriptionView.setText(editingClaim.getDescription());
//							
//							submitButton.setOnClickListener(new View.OnClickListener() {							
//								@Override
//								public void onClick(View v) {
//									Toast.makeText(getBaseContext(), "Submit", Toast.LENGTH_SHORT).show();
//								}
//							});
//							editClaimWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//						} else{
//							Toast.makeText(getBaseContext(), "This claim cant' be edited", Toast.LENGTH_SHORT).show();
//						}
//						
//					}
//				});
				//TODO make background darker
				window.setBackgroundDrawable(new BitmapDrawable());
		        window.setOutsideTouchable(true);
				window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
				return false;
			}
		
		});
	}

}
