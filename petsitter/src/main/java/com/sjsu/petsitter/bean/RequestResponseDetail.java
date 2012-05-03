package com.sjsu.petsitter.bean;

import java.math.BigInteger;
import java.util.Date;

public class RequestResponseDetail {
    public static final String IREQ="ireq";
	public static final String IRES="ires";
	public static final String OREQ="oreq";
	public static final String ORES="ores";

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
	private String type;
    private String contentType;
    private String fileName;



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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
