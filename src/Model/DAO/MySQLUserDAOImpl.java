package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import Model.VO.User;

public class MySQLUserDAOImpl implements UserDAO{
	
	private Connection con = null;
	PreparedStatement prep = null;
	Statement stm = null;
	ResultSet res = null;
	
	private static final String CORRECT_INPUT = "SELECT username, psswrd FROM utente";
	private static final String VERIFICA_USER = "SELECT u.username FROM utente u WHERE u.username LIKE ?";
	private static final String GET_USER = "SELECT u.nome, u.cognome, u.username, u.email, u.psswrd, u.ID, r.tipo_ruolo FROM utente u, ruolo r WHERE u.username = ? AND u.IDruolo = r.ID";
	private static final String ADD_COLLABORATOR_REQUEST = "UPDATE utente SET IDrichiesta = 1 WHERE username = ?";
    private static final String MAKE_PREMIUM = "UPDATE utente SET IDrichiesta = 2, IDruolo = 7 WHERE username = ?";
	private static final String REGISTER_USER = "INSERT INTO utente(nome, cognome, username, email, psswrd, IDruolo, IDrichiesta) VALUES (?,?,?,?,?, 1, 3 )";
    private static final String LIST_TRABSCRIBE = "SELECT ID, username FROM utente WHERE IDruolo = 3";
	
	@Override
	public boolean CorrectLogInput(String us, String pass) {
				
		boolean find = false;
		  try {
		  con = MySQLDAOFactory.createConnection();
		  prep = (PreparedStatement) con.prepareStatement(CORRECT_INPUT);
		  res = prep.executeQuery(); 
		  
		  while(res.next()) {
			  String usr = res.getString("username");
			  String passw = res.getString("psswrd");
			  if (usr.equals(us) && passw.equals(pass)) find = true;;
		  }
		  
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore Query");}	
		   finally {
			   closeDbConnection(res, prep, con);
		   }
		  
		  return find;
	}
	
	public void registraUtente(String n, String c, String em, String us, String pass) {
		
		try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(REGISTER_USER);
			  prep.setString(1, n);
			  prep.setString(2, c);
			  prep.setString(3, us);
			  prep.setString(4, em);
			  prep.setString(5, pass);
			  prep.executeUpdate();
		} catch (SQLException e) {e.printStackTrace(); System.out.println("Problema registrazione utente nel DB");}
		finally {
			closeDbConnection(res, prep, con);
		   }
		
	}

	@Override
	public boolean VerificaUser(String us) {
		
		boolean esiste = false;
		  
		  try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(VERIFICA_USER);
			  prep.setString(1, us);
			  res = prep.executeQuery();
			  res = prep.executeQuery();	
			  if(res.next()) esiste = true;
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Problema verifica utenza DB");}
		  finally {
			  closeDbConnection(res, prep, con);
		   }
		  
		  return esiste;
	}

	@Override
	public User getUser(String us) {
		
		User log = new User();
		  try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(GET_USER);
			  prep.setString(1, us);
			  res = prep.executeQuery();
			  if (res.next()) {
			  User prov = new User(res.getString("nome"), res.getString("cognome"), res.getString("username"), res.getString("psswrd"), res.getString("tipo_ruolo"), res.getString("email"), res.getInt("ID"));
			  log = prov;
			  }
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore ricezione dati utente");}
		  finally {
			  closeDbConnection(res, prep, con);
		   }
		  
		  return log;
	}

	@Override
	public void AddCollabrequest(String user) {
		
		  try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(ADD_COLLABORATOR_REQUEST);
			  prep.setString(1, user);
			  prep.executeUpdate();
			  closeDbConnection(res, prep, con);
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Problema aggiornamento richiesta DB");}
		  finally {
			  closeDbConnection(res, prep, con);
		   }
		  
	}

	@Override
	public List<User> getRequest() {
		
		List<User> lista = new ArrayList<User>();
		  try {
			  con = MySQLDAOFactory.createConnection();
			  stm = (Statement) con.createStatement();
			  String query = "SELECT u.username, r.stato_richiesta FROM utente u, richiesta r WHERE u.IDrichiesta = r.ID AND ( r.ID = 1 OR r.ID = 2)";
			  ResultSet res = stm.executeQuery(query);
			  List<User> temp = new ArrayList<User>();
			  while(res.next()) {
				  User p = new User();
				  String us = res.getString("username"), stato = res.getString("stato_richiesta");
				  p.setUser(us); p.setStato(stato);
				  if (!(temp.contains(p))) temp.add(p);
			  }
			  lista = temp;
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore dati richiesta");}
		  finally {
			  closeDbConnection(res, prep, con);
		   }
		  
		  return lista;
		  
	}

	@Override
	public void changeStatoReq(String user) {
		
		try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(MAKE_PREMIUM);
			  prep.setString(1, user);
			  prep.executeUpdate();
		} catch (SQLException e) {e.printStackTrace(); System.out.println("Errore modifica stato richiesta");}
		finally {
			  closeDbConnection(res, prep, con);
		   }	
	}
	
	@Override
	public List<User> getTrabscribelist(){
		
		List<User> trab = new ArrayList<User>();
		try {
			  List<User> temp = new ArrayList<User>();	
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(LIST_TRABSCRIBE);
			  res = prep.executeQuery();
			  while(res.next()) {
				  User u = new User();
				  String user = res.getString("username");
				  int id = res.getInt("ID");
				  u.setUser(user);
				  u.setIDdb(id);
				  temp.add(u);
			  }
			  trab = temp;
		} catch (SQLException e) {e.printStackTrace(); System.out.println("Errore modifica stato richiesta");}
		finally {
			  closeDbConnection(res, prep, con);
		   }	
		return trab;
	}
	
	public void closeDbConnection(ResultSet rs,  Statement stmt,  Connection conn){
		MySQLDAOFactory.closeDbConnection(rs, stmt, conn);
}

}
