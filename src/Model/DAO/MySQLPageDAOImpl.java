package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import Model.VO.Opera;
import Model.VO.Page;

public class MySQLPageDAOImpl implements PageDAO {
	
	private Connection con = null;
	PreparedStatement prep = null;
	Statement stm = null;
	ResultSet res = null;
	ResultSet res2 = null;
	
	private static final String INSERT_PAGE = "INSERT INTO pagina(numero_pagina, IDopera, IDstatopag, directorypagepath, directorytrascrpath) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_OPERAPAGES = "SELECT p.numero_pagina, p.directorypagepath, p.ID, s.stato_pagina FROM pagina p, statopagina s WHERE p.IDopera = ? AND p.IDstatopag = s.ID";
	private static final String GET_OPERACLOSEPAGES = "SELECT p.numero_pagina, p.directorypagepath, p.ID FROM pagina p WHERE p.IDopera = ?";
	private static final String ACCEPT_PAGE = "UPDATE pagina SET IDstatopag = 2 WHERE ID = ?";
	private static final String REFUSE_PAGE = "UPDATE pagina SET IDstatopag = 3 WHERE ID = ?";
	private static final String SETPAGE_TRANSCR = "INSERT INTO trascrizione(IDuploader, IDpagina, stato_trascrizione) VALUES (?,?,1)";
		
	@Override
	public boolean insertPage(int op, String path, String num, String pathtra) {
		boolean ins = false;
		  int numero = Integer.parseInt(num);
		  
		  try {
			  boolean temp = false;
			  con = MySQLDAOFactory.createConnection();
			  prep  = (PreparedStatement) con.prepareStatement(INSERT_PAGE);		  
			  prep.setInt(1, numero );
			  prep.setInt(2, op);
			  prep.setInt(3, 1);
			  prep.setString(4, path);
			  prep.setString(5, pathtra);
			  int i = prep.executeUpdate();
			  prep.close();
			  if (i != 0) temp = true; 
			  ins = temp;
		  } catch (SQLException e) {e.printStackTrace();}
		  finally {
			   closeDbConnection(res, prep, con);
		   }
		  
		  return ins;
	}
	
	@Override
	public TreeSet<Opera> getOperetoReview() {
		
		TreeSet<Opera> opere = new TreeSet<Opera>();
		 try {
			  con = MySQLDAOFactory.createConnection();
			  TreeSet<Opera> temp = new TreeSet<Opera>();
			  List<Opera> list = new ArrayList<Opera>();
			  stm = (Statement) con.createStatement();
			  String query = "SELECT o.titolo, o.ID, u.username FROM opera o, utente u WHERE o.IDuploader = u.ID AND o.IDstato = 3";
			  ResultSet res = stm.executeQuery(query);
			  String titolo;
			  
			  while (res.next()) {
				  titolo = res.getString("titolo");  int op = res.getInt("ID"); String userup = res.getString("username");
				  Opera ope = new Opera();
				  ope.setTitolo(titolo); ope.setId(op); ope.setUploader(userup);
				  list.add(ope);
			  }
			  		  
			  stm = (Statement) con.createStatement();
			  for (Opera opera : list) {
				  int idop = opera.getId();
				  String querypag = "SELECT p.numero_pagina, p.directorypagepath, p.ID FROM pagina p WHERE p.IDopera = '"+idop+"' AND p.IDstatopag = 1";
				  res2 = stm.executeQuery(querypag);
				  List<Page> listapage = new ArrayList<Page>();
				  while(res2.next()) {
					  Page p = new Page();
					  p.setID(res2.getInt("ID")); p.setNumeropagina(res2.getInt("numero_pagina")); 
					  p.setPath(res2.getString("directorypagepath"));
					  listapage.add(p);
					  }
				   int cont = 0;
				   cont = listapage.size();
				   if (cont != 0) {
					  opera.setPaginanum(cont);
					  opera.addPagine(listapage);
					  temp.add(opera);
					  }
				   else {
					   temp.add(opera);
				   }
				  }
			  			  
			  opere = temp;
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Erroe ricezione opere da revisonare");}
		 finally {
			  closeDbConnection(res, stm, con); 
			  closeDbConnection(res2,stm, con);
		   }
	  return opere;
	  
	}
	@Override
	public List<Page> getOperaPages(Opera p) {
		
		List<Page> lista = new ArrayList<Page>();
		 try {
			 con = MySQLDAOFactory.createConnection();
			 prep = (PreparedStatement) con.prepareStatement(GET_OPERAPAGES);
			 prep.setInt(1, p.getId());
			 List<Page> temp = new ArrayList<Page>();
			 ResultSet res = prep.executeQuery();
			 while(res.next()) {
				 int id = res.getInt("ID"), npage = res.getInt("numero_pagina");
				 String path = res.getString("directorypagepath"), stato = res.getString("stato_pagina");
				 Page pag = new Page();
				 pag.setID(id); pag.setNumeropagina(npage); pag.setPath(path); pag.setStato_pagina(stato);
				 temp.add(pag);
			 }
			 lista = temp;
		 } catch (SQLException e) {System.out.println("Errore ricezione pagine opera : " + p.getTitolo()); e.printStackTrace();}
		 finally {
			   closeDbConnection(res, prep, con);
		   }
		 
		 return lista;
	}
	
	@Override
	public List<Page> getOperaClosePage(Opera p){
		
		List<Page> pagine = new ArrayList<Page>();
		try {
			con = MySQLDAOFactory.createConnection();
			List<Page> temp = new ArrayList<Page>();
			prep = (PreparedStatement) con.prepareStatement(GET_OPERACLOSEPAGES);
			prep.setInt(1, p.getId());
			res = prep.executeQuery();
			while(res.next()) {
				Page page = new Page();
				page.setID(res.getInt("ID")); page.setNumeropagina(res.getInt("numero_pagina"));
				temp.add(page);
			}
			pagine = temp;
		} catch (SQLException exc) {exc.printStackTrace(); System.out.println("Errore ricezione pagine");}
		finally {
			   closeDbConnection(res, prep, con);
		   }
		return pagine;
		
	}
	
	@Override
	public void acceptPage(int ID) {
		
		try {
			 con = MySQLDAOFactory.createConnection();
			 prep = (PreparedStatement) con.prepareStatement(ACCEPT_PAGE);
			 prep.setInt(1, ID);
			 prep.executeUpdate();
		} catch (SQLException exc) {System.out.println("Errore modifica stato Pagina");}
		finally {
			   closeDbConnection(res, prep, con);
		   }
	}
	
	@Override
	public void refusePage(int ID) {
		
		 try {
			 con = MySQLDAOFactory.createConnection();
			 prep = (PreparedStatement) con.prepareStatement(REFUSE_PAGE);
			 prep.setInt(1, ID);
			 prep.executeUpdate();
		 } catch (SQLException exc) {System.out.println("Errore modifica stato Pagina");}
		 finally {
			   closeDbConnection(res, prep, con);
		   }
	}
	
	@Override
	public void setPageTranscriber(int idus, int idp) {
		
		try {
			con = MySQLDAOFactory.createConnection();
			prep = (PreparedStatement) con.prepareStatement(SETPAGE_TRANSCR);
			prep.setInt(1, idus);
			prep.setInt(2, idp);
			prep.executeUpdate();
		} catch (SQLException exc) {exc.printStackTrace(); System.out.println("Errore assegnazione opera");} 
		finally {
			   closeDbConnection(res, prep, con);
		   }
	}
	
		
	public void closeDbConnection(ResultSet rs,  Statement stmt,  Connection conn){
		MySQLDAOFactory.closeDbConnection(rs, stmt, conn);
}
	
	

}
