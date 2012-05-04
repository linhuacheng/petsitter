package com.android.petswitch.dto;

import com.android.petswitch.util.RestClient;

public class SearchRequestBean {

	private String petType;
	private String zip;
	private String city;
	private Integer page;
	private Integer size;
	public Double[] getNearLoc() {
		return nearLoc;
	}

	public void setNearLoc(Double[] nearLoc) {
		this.nearLoc = nearLoc;
	}


	private String loggedOnUserId;
	private Double[] nearLoc;

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getLoggedOnUserId() {
		return loggedOnUserId;
	}

	public void setLoggedOnUserId(String loggedOnUserId) {
		this.loggedOnUserId = loggedOnUserId;
	}

	public void addRestClientParam(RestClient client) {
		if (petType != null && petType.length() >0) {
			client.addParam("petType", petType);			
		}
		if (city != null && city.length() >0) {
			client.addParam("city", city);			
		}
		if (zip != null && zip.length() >0) {
			client.addParam("zip", zip);			
		}
		
		if (nearLoc != null && nearLoc.length >0) {
			client.addParam("nearLoc", nearLoc[0].toString());
			client.addParam("nearLoc", nearLoc[1].toString());
		}
	}
	
	
	public void addCriteria(String name, String value) {
		if (name == null || name.length() ==0) {
			return;
		}
		
		if ("PETTYPE".equalsIgnoreCase(name.replace(" ", ""))) {
			this.setPetType(value);
		}
		
		if ("ZIP".equalsIgnoreCase(name)) {
			this.setZip(value);
		}
		
		if ("CITY".equalsIgnoreCase(name)) {
			this.setCity(value);
		}

	}
	
}
