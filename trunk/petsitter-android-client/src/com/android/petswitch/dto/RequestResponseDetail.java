package com.android.petswitch.dto;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.petswitch.util.ApplicationConstants;

public class RequestResponseDetail {

	
	private String comment;
	private String status;
	private String requestorUserName;
	private String approverUserName;
	private String requestorAddress;  
	private String approverAddress; 
	private String requestStartDate;
	private String requestEndDate;
	private String petType;
    private String requesterPhoneNumber;
    private String approverPhoneNumber;
    private BigInteger requestId;
        
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestorUserName() {
		return requestorUserName;
	}
	public void setRequestorUserName(String requestorUserName) {
		this.requestorUserName = requestorUserName;
	}
	public String getApproverUserName() {
		return approverUserName;
	}
	public void setApproverUserName(String approverUserName) {
		this.approverUserName = approverUserName;
	}
	public String getRequestorAddress() {
		return requestorAddress;
	}
	public void setRequestorAddress(String requestorAddress) {
		this.requestorAddress = requestorAddress;
	}
	public String getApproverAddress() {
		return approverAddress;
	}
	public void setApproverAddress(String approverAddress) {
		this.approverAddress = approverAddress;
	}
	public String getRequestStartDate() {
		return requestStartDate;
	}
	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}
	public String getRequestEndDate() {
		return requestEndDate;
	}
	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}


    public String getRequesterPhoneNumber() {
        return requesterPhoneNumber;
    }

    public void setRequesterPhoneNumber(String requesterPhoneNumber) {
        this.requesterPhoneNumber = requesterPhoneNumber;
    }

    public String getApproverPhoneNumber() {
        return approverPhoneNumber;
    }

    public void setApproverPhoneNumber(String approverPhoneNumber) {
        this.approverPhoneNumber = approverPhoneNumber;
    }

    public BigInteger getRequestId() {
        return requestId;
    }

    public void setRequestId(BigInteger requestId) {
        this.requestId = requestId;
    }
	
	
    private SimpleDateFormat formatter = new SimpleDateFormat (ApplicationConstants.DATE_FORMAT);
	
    public Date getRequestStartDateD() {
    	Date date = new Date();    	
    	try {
			date = formatter.parse(requestStartDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date; 
	}

    public Date getRequestEndDateD() {
    	Date date = new Date();    	
    	try {
			date = formatter.parse(requestEndDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;    			
	}
}
