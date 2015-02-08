package it.pbc.chiloripara.services.interfaces;

import java.io.IOException;
import java.util.HashMap;

public interface IGoogleService {

	public abstract HashMap<String, Float> getCoordinate(String address) throws IOException;

}