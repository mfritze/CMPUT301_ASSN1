package com.school.cmput301;

public class ClaimStatus {
	// 0 = editable, 1 = sent, 2 = closed
	private final int INPROGRESS = 0, SENT = 1, CLOSED = 2;
	private int status;
	
	public ClaimStatus(int status){
		if((status < 3) && (status >= 0)){
			this.status = status;
		}else{
			this.status = INPROGRESS;
		}
	}
	public ClaimStatus(){
		this.status = INPROGRESS;
	}
	
	public boolean isEditable(){
		if(this.status < CLOSED){
			return true;
		}
		return false;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
