package Model.VO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;

public class Opera implements Comparable<Opera> {
	
	private String Titolo, Autore, Genere, ISBN, Lingua, Uploader, pathCopertina;
	private int Annopubb, Paginanum, id;
	private List<Page> pagine;
		
	public Opera() {
		
	}
	
	public Opera(String t, String At, String gn, String In, String l, int ap, int n, int i, String pt) {
		Titolo = t;
		Autore = At;
		Genere = gn;
		ISBN = In;
		Annopubb = ap;
		Lingua = l;
		pagine = null;
		Paginanum = n;
		id = i;
		pathCopertina = pt;
	}
	
	public String getTitolo() {
		return Titolo;
	}

	public void setTitolo(String titolo) {
		Titolo = titolo;
	}

	public String getAutore() {
		return Autore;
	}

	public void setAutore(String autore) {
		Autore = autore;
	}

	public String getGenere() {
		return Genere;
	}

	public void setGenere(String genere) {
		Genere = genere;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public int getAnnopubb() {
		return Annopubb;
	}

	public void setAnnopubb(int annopubb) {
		Annopubb = annopubb;
	}
	
	public String getLingua() {
		return this.Lingua;
	}
	
	public void setLingua(String l) {
		this.Lingua = l;
	}
	
		
	public int getPaginanum() {
		return Paginanum;
	}

	public void setPaginanum(int paginanum) {
		Paginanum = paginanum;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getUploader() {
		return Uploader;
	}

	public void setUploader(String uploader) {
		this.Uploader = uploader;
	}
	
	
	public List<Page> getPagine() {
		return pagine;
	}

	public void setPagine(List<Page> pagine) {
		this.pagine = pagine;
	}
	
	
	public String getPathCopertina() {
		return pathCopertina;
	}

	public void setPathCopertina(String pathCopertina) {
		this.pathCopertina = pathCopertina;
	}

	@Override
	public int compareTo(Opera other) {
		return Titolo.compareTo(other.Titolo);
	}
	
	public boolean addPagina(Page p) {
		
		if (!(pagine.contains(p))) {
			pagine.add(p); 
			return true;
		}
		else return false;
		
	}
	
	public void addPagine(List<Page> lista) {
		pagine = lista;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof Page)) return false;
		Opera p = (Opera) obj;
		return this.Titolo.equals(p.Titolo);
		
	}

}
