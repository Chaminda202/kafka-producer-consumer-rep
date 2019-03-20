package com.spring.kafka.model;

import com.spring.kafka.util.JacksonUtil;

public class Bank {
	private String bankName;
	private String bankCode;
	private int numberOfBranch;
	private String startedDate;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public int getNumberOfBranch() {
		return numberOfBranch;
	}
	public void setNumberOfBranch(int numberOfBranch) {
		this.numberOfBranch = numberOfBranch;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	@Override
	public String toString() {
		return JacksonUtil.getToString(this);
	}
	
}
