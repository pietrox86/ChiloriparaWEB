package it.pbc.chiloripara.web.model.notPersisted;

import it.pbc.chiloripara.web.model.entities.Artigiano;

public class ArtigianoDistanza implements Comparable<ArtigianoDistanza> {

	public Artigiano getArt() {
		return art;
	}

	public void setArt(Artigiano art) {
		this.art = art;
	}

	public float getDistanza() {
		return distanza;
	}

	public void setDistanza(float distanza) {
		this.distanza = distanza;
	}

	private Artigiano art;
	private float distanza;

	@Override
	public int compareTo(ArtigianoDistanza o) {
		if (this.distanza == o.distanza)
			return 0;
		else if (this.distanza > o.distanza)
			return 1;
		return -1;
	}

}
