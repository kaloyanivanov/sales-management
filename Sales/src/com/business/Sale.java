package com.business;

public class Sale {
	private int id;
	private String agentEmail;
	private String clientName;
	private String date;
	private double sum;
	private int productId;
	private int quantitySold;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int product_id) {
		this.productId = product_id;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantity_sold) {
		this.quantitySold = quantity_sold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAgentEmail() {
		return agentEmail;
	}

	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}

}
