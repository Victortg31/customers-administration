package com.victor.soap.webservices.customersadministration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.victor.soap.webservices.customersadministration.bean.Customer;
import com.victor.soap.webservices.customersadministration.helper.StatusEnum;

@Component
public class CustomerDetailService {
	
	
	//lista mantida em memoria
	private static List<Customer> customers = new ArrayList<>();
	
	static {
		Customer customer1 = new Customer(1,"Victor","9999-9999","vtamer@indracompany.com");
		customers.add(customer1);
		
		Customer customer2 = new Customer(2,"Lucas","1111-1111","lucas_fgz@gmail.com");
		customers.add(customer2);
		
		Customer customer3 = new Customer(3,"Rodrigo","2222-2222","rodrigosouza@hotmail.com");
		customers.add(customer3);
		
		Customer customer4 = new Customer(4,"Stephano","3333-3333","staphanog@yahoo.com.br");
		customers.add(customer4);
	}
	
	
	public Customer findById(int id) {
		// pega o Customer c, pega o id dele e verifica se é igual ao ID passado por parametro do metodo
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		
		//caso seja encontrado o id desejado, ele retornara o objeto todo
		if(customerOptional.isPresent()) {
			return customerOptional.get();
		}
		
		return null;
	}
	
	public List<Customer> findAll(){
		return customers;
	}
	
	//deletendo por id
	public StatusEnum deleteById(int id) {
		// pega o Customer c, pega o id dele e verifica se é igual ao ID passado por parametro do metodo
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		
		//caso seja encontrado o id desejado, ele deletara o objeto da lista
		if(customerOptional.isPresent()) {
			customers.remove(customerOptional.get());
			
			return StatusEnum.SUCESSO;
		}
		
		return StatusEnum.FALHOU;
	}
}
