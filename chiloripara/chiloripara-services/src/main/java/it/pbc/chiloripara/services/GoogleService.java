package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.interfaces.IGoogleService;
import it.pbc.chiloripara.services.interfaces.IPropertiesService;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderStatus;

@Component
public class GoogleService implements IGoogleService {

	final private Geocoder geocoder = new Geocoder();

	@Autowired
	private IPropertiesService properties;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IGoogleService#getCoordinate(java.lang.String)
	 */
	@Override
	public HashMap<String, Float> getCoordinate(String address) throws IOException {
		HashMap<String, Float> result = new HashMap<String, Float>();

		//abilito il fake delle coordinate
		if (properties.getValore("enableCoordinate").equals("0")) {
			result.put("LAT", new Float(45.4246));
			result.put("LNG", new Float(9.61444));
			result.put("STATUS", new Float(1));
			return result;
		}

		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

		if (geocoderResponse.getStatus().equals(GeocoderStatus.OK)) {
			result.put("STATUS", new Float(1));
			result.put("LAT", geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().floatValue());
			result.put("LNG", geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().floatValue());
		} else
			result.put("STATUS", new Float(0));
		return result;
	}

}
