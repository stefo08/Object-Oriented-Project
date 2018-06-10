package Model.VO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

public class User implements Comparable<User> {

	private String Nome;
	private String Cognome;
	private String User;
	private String Password;
	private String Ruolo;
	private String Email;
	private String stato;
	private int IDdb;
	
	
	public User() {
	}
		
	public User(String n, String c, String us, String pass, String r, String mail, int db) {
		this.Cognome = c;
		this.Nome = n;
		this.User = us;
		this.Password = pass;
		this.Ruolo = r;
		this.Email = mail;
		this.IDdb = db;
	}

	public int getIDdb() {
		return IDdb;
	}

	public void setIDdb(int iDdb) {
		IDdb = iDdb;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRuolo() {
		return Ruolo;
	}

	public void setRuolo(String ruolo) {
		Ruolo = ruolo;
	}
	
	public String getMail() {
		return this.Email;
	}
	
	public void setMail(String email) {
		Email = email;
	}
	
	public void setStato(String s) {
		this.stato = s;
	}
	
	public String getStato() {
		return this.stato;
	}
	
	@Override
	public int compareTo(User b) {
		if (Nome.equals(b.Nome)) return 0;
		else return Nome.compareTo(b.Nome);
	}
}
