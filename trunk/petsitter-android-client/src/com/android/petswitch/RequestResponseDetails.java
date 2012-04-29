package com.android.petswitch;


public class RequestResponseDetails {

	private String petType;
	private String petDesc;
	private String reqStartDate;
	private String reqEndDate;
	private String message;
	private String status;
	private String type;
	
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public String getPetDesc() {
		return petDesc;
	}
	public void setPetDesc(String petDesc) {
		this.petDesc = petDesc;
	}
	public String getReqStartDate() {
		return reqStartDate;
	}
	public void setReqStartDate(String reqStartDate) {
		this.reqStartDate = reqStartDate;
	}
	public String getReqEndDate() {
		return reqEndDate;
	}
	public void setReqEndDate(String reqEndDate) {
		this.reqEndDate = reqEndDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRequest()
	{
		return type+" for "+petType;
	}
	
}
