package com.school.cmput301.Fragments;

import java.util.ArrayList;
import java.util.Collection;

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

import com.school.cmput301.R;
import com.school.cmput301.Activities.ExpenseManagerActivity;
import com.school.cmput301.Activities.MainActivity;
import com.school.cmput301.Controllers.ClaimAdapter;
import com.school.cmput301.Controllers.Listener;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimListSingleton;
import com.school.cmput301.Models.ClaimStatus;

public class ClaimListFragment extends Fragment {
	private ClaimAdapter claimAdapter;
	private ArrayList<Claim> claims;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.claim_list_layout, container, false);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		addListeners();
		manageListAdapter();
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
		
		ClaimListSingleton.getClaimList().addListener(new Listener(){

			@Override
			public void update() {
				ClaimListSingleton.sortClaimList();
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
				Claim c = ClaimListSingleton.getClaimList().getClaimAtIndex(position);
				if(c.getStatus() == ClaimStatus.INPROGRESS){
					Intent intent = new Intent(getActivity(), ExpenseManagerActivity.class);
					intent.putExtra(MainActivity.CLAIMINDEX, position);
					startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "Cannot edit this claim", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		claimView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View popupView = inflater.inflate(R.layout.claim_edit_popup, null);

				final PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				final View elementView = view;
				ImageButton deleteClaim = (ImageButton) popupView.findViewById(R.id.removeClaimButton);
				ImageButton sendClaim = (ImageButton) popupView.findViewById(R.id.setSentButton);
				ImageButton approveClaim = (ImageButton) popupView.findViewById(R.id.setApprovedButton);
				ImageButton editClaim = (ImageButton) popupView.findViewById(R.id.setInProgressButton);
				
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
						Claim claim = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
						if(claim.getStatus() == ClaimStatus.SENT){
							Toast.makeText(getActivity(), "Already sent this claim", Toast.LENGTH_SHORT).show();
						} else if(claim.getStatus() == ClaimStatus.APPROVED){
							Toast.makeText(getActivity(), "Claim is already approved", Toast.LENGTH_SHORT).show();
						} else{ // in progress
							claim.setStatus(ClaimStatus.SENT);
							ClaimListSingleton.getClaimList().notifyListeners();
							//Email intent based on https://stackoverflow.com/questions/8284706/send-email-via-gmail Feb 1 2015
							Intent i = new Intent(Intent.ACTION_SEND);
					        i.setType("text/plain");
					        i.putExtra(Intent.EXTRA_EMAIL,
					                        new String[] {"mfritze@ualberta.ca"});
					        i.putExtra(Intent.EXTRA_SUBJECT, "Claim Proposal");
					        i.putExtra(Intent.EXTRA_TEXT, claim.getEmailText());
					        try {
					            startActivity(Intent.createChooser(i, "Email Claim"));
					        } catch (android.content.ActivityNotFoundException ex) {
					            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					        }
						}
						window.dismiss();
					}
				});
				
				approveClaim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Claim c = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
						if(c.getStatus() != ClaimStatus.SENT){
							Toast.makeText(getActivity(), "The Claim must be sent to be approved", Toast.LENGTH_SHORT).show();
						}else{
							// background color based on http://stackoverflow.com/questions/23517879/set-background-color-programmatically Feb 1 2015
							//elementView.setBackgroundColor(Color.parseColor("#dddddd")); //TODO if you want to set the background colol
							c.setStatus(ClaimStatus.APPROVED);
							ClaimListSingleton.getClaimList().notifyListeners();
						}
						window.dismiss();
					}
				});

				editClaim.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Claim c = ClaimListSingleton.getClaimList().getClaimAtIndex(claimPosition);
						window.dismiss();
						if(c.isEditable()){
								c.setStatus(ClaimStatus.INPROGRESS);
								((MainActivity) getActivity()).editClaim(claimPosition);
						} else{
							Toast.makeText(getActivity(), "This claim can't be edited", Toast.LENGTH_SHORT).show();
						}
					}
				});
				window.setBackgroundDrawable(new BitmapDrawable());
		        window.setOutsideTouchable(true);
				window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
				return true;
			}
		
		});
	}

}
