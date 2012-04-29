package com.android.petswitch;

import java.util.ArrayList;
import java.util.List;

public class SetRequestDetails {

	public List<RequestResponseDetails> setRequestDetails()
	{
		List<RequestResponseDetails> reqDetails = new ArrayList<RequestResponseDetails>();
		
		RequestResponseDetails rd = new RequestResponseDetails();
		RequestResponseDetails rd1 = new RequestResponseDetails();
		
		rd.setPetType("dog");
		rd.setPetDesc("black cute doggie");
		rd.setReqStartDate("05/15/2012, 6:00pm");
		rd.setReqEndDate("05/16/2012, 6:00pm");
		rd.setMessage("Can you please take care of my pet..");
		rd.setStatus("Accepted");
		rd.setType("Request");
		
		reqDetails.add(rd);
		
		
		rd1.setPetType("cat");
		rd1.setPetDesc("whit with black spots");
		rd1.setReqStartDate("05/20/2012, 10:00am");
		rd1.setReqEndDate("05/20/2012, 6:00pm");
		rd1.setMessage("Can you please take care of my pet..");
		rd1.setStatus("Rejected");
		rd1.setType("Response");
		
		reqDetails.add(rd1);
		
		return reqDetails;
	}
}
