package br.com.fiap.scj.rest;



import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import br.com.fiap.scj.rest.route.SummaryRoute;

@Component
public class SummaryPost extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		restConfiguration().component("restlet")
		.host("localhost").port(10000)
        .bindingMode(RestBindingMode.auto);
		
		rest("/{uf}/summary").id("sumarryPost")
			.post()
			.consumes("application/json")
			.produces("application/json")
			.to(SummaryRoute.ROUTE_SUMMARY);
	}
	
	

}
