package it.pbc.chiloripara.test.rest;

import it.pbc.chiloripara.rest.model.RicercaRestResponse;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {
	private static  Logger logger = LoggerFactory.getLogger(RestClient.class);
	public static void main(String[] args) {
		logger.info("START RESTFUL WS");
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(getBaseURI());
		RicercaRestResponse resp = target.path("rest").path("ricerca/artigiano/1/via vailate 43").request().accept(MediaType.APPLICATION_JSON).get(RicercaRestResponse.class);
		

	}

	private static URI getBaseURI() {

		return UriBuilder.fromUri("http://localhost:8080/chiloripara-web").build();

	}
}
