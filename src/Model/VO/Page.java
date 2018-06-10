package Model.VO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

public class Page implements Comparable<Page> {
	
	private int ID, numeropagina;
	private String path;
	private String stato_pagina;
	private Transcription trascr;
	private String titoloop;
	
	public Page() {
		
	}
	
	public Page(int i, int num, String url, String tit) {
		ID = i;
		numeropagina = num;
		path = url;
		titoloop = tit;
	}

	public int getNumeropagina() {
		return numeropagina;
	}

	public void setNumeropagina(int numeropagina) {
		this.numeropagina = numeropagina;
	}
	
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	public String getStato_pagina() {
		return stato_pagina;
	}

	public void setStato_pagina(String stato_pagina) {
		this.stato_pagina = stato_pagina;
	}
	
	
	public Transcription getTrascr() {
		return trascr;
	}

	public void setTrascr(Transcription trascr) {
		this.trascr = trascr;
	}
	
	
	public String getTitoloop() {
		return titoloop;
	}

	public void setTitoloop(String titoloop) {
		this.titoloop = titoloop;
	}

	@Override
	public int compareTo(Page Other) {
		return numeropagina - Other.numeropagina;
	}
		
}
