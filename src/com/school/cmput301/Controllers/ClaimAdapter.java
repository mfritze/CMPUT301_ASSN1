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
package com.school.cmput301.Controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.cmput301.R;
import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.ClaimStatus;

public class ClaimAdapter extends ArrayAdapter{
	/* 
	 * The custom array adapter used to 
	 * display the necessary info for a claim
	 */
	
	//based on http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view Jan 24th 2015
	// and http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter Jan 25 2015
	ArrayList<Claim> claims;
	public ClaimAdapter(Context context, int textViewResourceId, List<Claim> claims) {
		super(context, textViewResourceId,claims);
		this.claims = (ArrayList<Claim>) claims;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.claim_adapter, null);
		}
		
		Claim claim = claims.get(position);
		
		if(claim != null){
			TextView titleView = (TextView) view.findViewById(R.id.titleText);
			//TextView secondView = (TextView) view.findViewById(R.id.secondaryText);
			TextView dateView = (TextView) view.findViewById(R.id.dateText);
			TextView costView = (TextView) view.findViewById(R.id.costText);
			ImageView statusView = (ImageView) view.findViewById(R.id.statusImage);
			
			titleView.setText(claim.getTitle());
			dateView.setText(claim.getDateText());
			costView.setText(claim.getCostText());
			
			int status = claim.getStatus();
			if(status == ClaimStatus.INPROGRESS){
				statusView.setImageResource(android.R.drawable.ic_menu_edit);
			}else if(status == ClaimStatus.SENT){
				statusView.setImageResource(android.R.drawable.ic_dialog_email);
			}else{ // CLOSED
				statusView.setImageResource(android.R.drawable.ic_menu_myplaces);
			}
		}
		
		return view;
	}
}
