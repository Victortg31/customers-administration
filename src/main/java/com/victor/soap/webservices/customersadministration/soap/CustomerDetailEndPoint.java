package com.victor.soap.webservices.customersadministration.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.victor.soap.webservices.customersadministration.bean.Customer;
import com.victor.soap.webservices.customersadministration.helper.StatusEnum;
import com.victor.soap.webservices.customersadministration.service.CustomerDetailService;
import com.victor.soap.webservices.customersadministration.soap.exception.CustomerNotFoundException;

import br.com.victor.CustomerDetail;
import br.com.victor.GetAllCustomerDetailRequest;
import br.com.victor.GetAllCustomerDetailResponse;
import br.com.victor.GetCustomerDetailRequest;
import br.com.victor.GetCustomerDetailResponse;
import br.com.victor.RemoveCustomerDetailRequest;
import br.com.victor.RemoveCustomerDetailResponse;
import br.com.victor.StatusEnumXsd;

@Endpoint
public class CustomerDetailEndPoint {

	@Autowired
	CustomerDetailService customerDetailService;

	@PayloadRoot(namespace = "http://victor.com.br", localPart = "GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest request)
			throws Exception {

		Customer customer = customerDetailService.findById(request.getId());

		if (customer == null)  {
			throw new CustomerNotFoundException("O cliente id: " + request.getId() + " não foi encontrado");
		}

		return convertToGetCustomerDetailResponse(customer);
	}

	// convertando o objeto para um GetCustomerDetailResponse, utilizando o metodo
	// converteToCustomerDetail
	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse response = new GetCustomerDetailResponse();

		response.setCustomerDetail(converteToCustomerDetail(customer));
		return response;
	}

	// Converntando um objeto Customer para um CustomerDetail, para ser utlizado no
	// response
	private CustomerDetail converteToCustomerDetail(Customer customer) {

		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getNome());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());

		return customerDetail;
	}

	@PayloadRoot(namespace = "http://victor.com.br", localPart = "GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(
			@RequestPayload GetAllCustomerDetailRequest request) {
		List<Customer> customers = customerDetailService.findAll();
		return convertToGetAllCustomerDetail(customers);
	}

	// convertando um Customer para um GetAllCustomerDetail
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetail(List<Customer> customers) {
		GetAllCustomerDetailResponse response = new GetAllCustomerDetailResponse();

		// Pega todo cada objeto (c) da lista customers, e adiciona na lista do
		// GetAllCustomerDetailResponse, utilizandfo o metodo getCustomerDetail
		customers.stream().forEach(c -> response.getCustomerDetail().add(converteToCustomerDetail(c)));
		return response;
	}
	
	
	private StatusEnumXsd convertStatusSoap(StatusEnum statusService) {
		
		if(statusService == StatusEnum.FALHOU)
			return StatusEnumXsd.FALHOU;
		
		return StatusEnumXsd.SUCESSO;
	}
	
	@PayloadRoot(namespace = "http://victor.com.br", localPart = "RemoveCustomerDetailRequest")
	@ResponsePayload
	public RemoveCustomerDetailResponse processRemoveCustomerDetailRequest(@RequestPayload RemoveCustomerDetailRequest request){
		
		RemoveCustomerDetailResponse response = new RemoveCustomerDetailResponse();
		response.setStatus(convertStatusSoap(customerDetailService.deleteById(request.getId())));
		
		return response;

	}
	
	

}
