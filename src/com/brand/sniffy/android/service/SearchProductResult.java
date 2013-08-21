package com.brand.sniffy.android.service;

import com.brand.sniffy.android.model.Product;

public class SearchProductResult {

	
	public enum ResultStatus {
		OK,
		NOT_FOUND,
		OFFLINE, 
		INVALID_RESULT, 
		SERVER_ERROR
	}
	
	private ResultStatus status;
	
	private Product result;

	public SearchProductResult(ResultStatus status, Product result) {
		this.status = status;
		this.result = result;
	}

	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	public Product getResult() {
		return result;
	}

	public void setResult(Product result) {
		this.result = result;
	}
	
	
}
