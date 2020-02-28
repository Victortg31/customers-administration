package com.victor.soap.webservices.customersadministration.bean;

public class Customer {
	
	private int id;
	private String nome;
	private String phone;
	private String email;
	
	
	public Customer(int id, String nome, String phone, String email) {
		
		super();
		this.id = id;
		this.nome= nome;
		this.phone=phone;
		this.email=email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", nome=" + nome + ", phone=" + phone + ", email=" + email + "]";
	}
	

}
