package br.com.fiap.scj.rest.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SummaryRoute extends RouteBuilder {
	
	@Value("${fila.input.trabalho}")
	private String endpointInput;
	
	public static final String ROUTE_SUMMARY = "direct:routeSummary";

	@Override
	public void configure() throws Exception {
		
		from(ROUTE_SUMMARY)
			.doTry()
				.process(ex -> {
					String uf = ex.getIn().getHeader("uf", String.class);
					if (uf.length() == 2) {
						ex.getIn().setBody(uf);
						ex.setProperty("fila", true);
					} else {
						ex.getIn().setBody("Parametro de consulta inválido");
						ex.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
						ex.setProperty("fila", false);
					}
				})
				.choice()
					.when(simple("${property.fila} == true"))
						.to("rabbitmq://localhost:5672/fiap.scp.input.exchange?username=guest&password=guest&queue=fiap.scp.input.fila")
						.endChoice()
			.endDoTry()
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "EXCEPTION: ${exception.stacktrace}")
				.log("ERRO!")
		.end();
		
	}

}
