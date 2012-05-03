package com.sjsu.petsitter.bean;

import java.util.Date;

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

	
	

}
