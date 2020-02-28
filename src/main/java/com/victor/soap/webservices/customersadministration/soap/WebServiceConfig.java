package com.victor.soap.webservices.customersadministration.soap;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration // avisando para o spring que essa é uma classe de configuração
@EnableWs // habilitando o Spring Web Service
public class WebServiceConfig extends WsConfigurerAdapter {

	//criando o metodo do dispatch service, responsavel por gerenciar as mensagens e falar para qual end point ela via ir
	@Bean
	public ServletRegistrationBean messageDispatchServlet(ApplicationContext context) {
		
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		
		//configurando o contexto da aplicação
		messageDispatcherServlet.setApplicationContext(context);
		
		//configurando o transformeWsdlLocation, valor default é falso
		messageDispatcherServlet.setTransformWsdlLocations(true);
		
		// todas url que vierem WS/qualquer coisa, esse WebServiceConfig vai estar tratando
		return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
	}
	
	//metodo para definir o arquivo XSD
	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customer-information.xsd"));
	}
	
	//configurando a definição do wsdl
	@Bean(name="customers")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customerSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
				
		definition.setPortTypeName("CustomerPort");//definindo o tipo de portaq
		definition.setTargetNamespace("http://victor.com.br");//setando o namespace do xsd
		definition.setLocationUri("/ws");
		definition.setSchema(customerSchema);
		return definition;
	}
	
	//Primeiro passo para ter uma segurança
	//Ele intercepta toda as solicitações, e verifica se a solitação está segura
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		
		//criando uma politica de segurança
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		
		return securityInterceptor;
	}
	
	//definindo o manipulador de retorno, com base se o usuário e senha estiver errado Call Back Handler
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("klay", "123"));
		return handler;
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}
	
}
