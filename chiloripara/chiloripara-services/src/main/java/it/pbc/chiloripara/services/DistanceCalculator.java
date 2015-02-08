package it.pbc.chiloripara.services;

import it.pbc.chiloripara.services.interfaces.IDistanceCalculator;

import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator implements IDistanceCalculator {

	private static final float R = (float) 6372.795477598;

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IDistanceCalculator#decimalToRadiant(float)
	 */
	@Override
	public float decimalToRadiant(float angle) {
		return (float) (angle * Math.PI / 180);
	}

	/* (non-Javadoc)
	 * @see it.pbc.chiloripara.services.IDistanceCalculator#calculateDistance(float, float, float, float)
	 */
	@Override
	public float calculateDistance(float latA, float lngA, float latB,
			float lngB) {

		float latRA = decimalToRadiant(latA);
		float latRB = decimalToRadiant(latB);
		float lngRA = decimalToRadiant(lngA);
		float lngRB = decimalToRadiant(lngB);

		float distance = 0;

		distance = (float) (R
				* Math.acos(Math.sin(latRA) * Math.sin(latRB) + Math.cos(latRA)
						* Math.cos(latRB) * Math.cos(lngRA - lngRB)));

		return distance;
	}
}
