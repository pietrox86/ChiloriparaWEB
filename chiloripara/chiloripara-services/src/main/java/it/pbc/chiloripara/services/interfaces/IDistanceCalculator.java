package it.pbc.chiloripara.services.interfaces;

public interface IDistanceCalculator {

	public abstract float decimalToRadiant(float angle);

	/**
	 * 
	 * @param latA
	 *            latitudine del punto A in gradi decimale
	 * @param lngA
	 *            longitudine del punto A in gradi decimali
	 * @param latB
	 *            latitudine del punto B in gradi decimali
	 * @param lngB
	 *            longitudine del punto B in gradi decimali
	 * @return Distanza Kilometrica in linea d'aria.
	 */
	public abstract float calculateDistance(float latA, float lngA, float latB, float lngB);

}