package com.sjsu.petsitter.domain;

import java.math.BigDecimal;

public class AddressLoc {

	private BigDecimal lon;
	
	private BigDecimal lat;

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}


}
