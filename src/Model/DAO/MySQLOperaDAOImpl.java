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

import Model.VO.Opera;
import Model.VO.Page;
import Model.VO.Transcription;

public class MySQLOperaDAOImpl implements OperaDAO{
	
	private Connection con = null;
	PreparedStatement prep = null;
	Statement stm = null;
	ResultSet res = null;
	Statement stm2 = null;
	ResultSet res2 = null;
	
	private static final String INSERT_OP = "INSERT INTO opera(titolo, autore, genere, ISBN, anno_pubb, lingua, IDstato, IDuploader, pathCopertina) VALUES (?,?,?,?,?,?,1,?,?)";
    private static final String OPERE_UPLOADER = "SELECT o.ID, o.titolo, o.autore, o.genere, o.ISBN, o.lingua, o.anno_pubb, o.pathCopertina FROM opera o WHERE o.IDuploader = ? AND o.IDstato = 1";
	private static final String OPERE_NUMPAGE = "SELECT Count(p.IDopera) AS npagina FROM opera o, pagina p WHERE o.titolo = ? AND p.IDOpera = o.ID";
    private static final String CLOSE_UPLOAD = "UPDATE opera SET IDstato = 3 WHERE ID = ?";
	private static final String OPERE_CLOSE = "SELECT ID, titolo, autore FROM opera WHERE IDstato = 2";
    private static final String PUBLIC_OPERA = "UPDATE opera SET IDstato = 2 WHERE ID = ?";
	
	@Override
	public List<Opera> getOpera(String titolo) {
		
		List<Opera> ricerca = new ArrayList<Opera>();
		  try {
			  con = MySQLDAOFactory.createConnection();
			  stm = (Statement) con.createStatement();
			  String query = "SELECT o.ID, o.titolo, o.autore, o.genere, o.ISBN, o.lingua, o.anno_pubb, o.pathCopertina FROM opera o WHERE o.IDstato = 2 AND o.titolo LIKE '%"+titolo+"%'";
			  ResultSet res = stm.executeQuery(query);
			  List<Opera> temp = new ArrayList<Opera>();
			  while(res.next()) {
				  String tit, autore, genere, ISBN, lingua, path;
				  int anno_pubb, id;
				  tit = res.getString("titolo"); autore = res.getString("autore"); genere = res.getString("genere"); ISBN = res.getString("ISBN"); lingua = res.getString("lingua");
				  anno_pubb = res.getInt("anno_pubb"); path = res.getString("pathCopertina"); id = res.getInt("ID");
				  Opera p = new Opera();
				  p.setTitolo(tit); p.setAutore(autore); p.setGenere(genere);
				  p.setISBN(ISBN); p.setLingua(lingua); p.setAnnopubb(anno_pubb);
				  p.setPathCopertina(path); p.setId(id);
				  if(!(temp.contains(p))) temp.add(p);
			  }
			  
			  for(Opera p : temp) {
				  
				  List<Page> operapage = new ArrayList<Page>();
				  stm2 = (Statement) con.createStatement();
				  String SQL2 = "SELECT p.numero_pagina, p.ID, p.directorypagepath, p.directorytrascrpath FROM pagina p WHERE p.IDopera = '"+p.getId()+"'";
				  res2 = stm2.executeQuery(SQL2);
				  while(res2.next()) {
					  
					  Transcription tra = new Transcription();
					  tra.setPath(res2.getString("directorytrascrpath"));
					  Page page1 = new Page(); page1.setID(res2.getInt("ID")); page1.setNumeropagina(res2.getInt("numero_pagina"));
					  page1.setPath(res2.getString("directorypagepath"));
					  page1.setTrascr(tra);
					  operapage.add(page1);
					  					  
				  }
				  p.addPagine(operapage);
			  }
			  
			  ricerca = temp;
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore ricerca opera nel DB");}
		  finally {
			   closeDbConnection(res, stm, con);
			   closeDbConnection(res2, stm2, con);
		   }
		  
		  return ricerca;
	}

	@Override
	public void InsertOP(String t, String aut, String gen, String ISBN, int ap, String ln, int id, String path) {
		 
		  try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(INSERT_OP);
			  prep.setString(1, t);
			  prep.setString(2, aut);
			  prep.setString(3, gen);
			  prep.setString(4, ISBN);
			  prep.setInt(5, ap);
			  prep.setString(6, ln);
			  prep.setInt(7, id);
			  prep.setString(8, path);
			  prep.executeUpdate();
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore inserimento opera nel db");}
		  finally {
			   closeDbConnection(res, prep, con);
		   }
	}

	@Override
	public List<Opera> getOperetoaddPage(int idUp) {

		  List<Opera> listaUp = new ArrayList<Opera>();
		  try {
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(OPERE_UPLOADER);
			  List<Opera> temp = new ArrayList<Opera>();
			  prep.setInt(1, idUp);
			  ResultSet res = prep.executeQuery();
			  String tit, autore, genere, ISBN, lingua, cop;
			  int anno_pubb, num, idop;
			  while (res.next()) {
				  tit = res.getString("titolo"); autore = res.getString("autore"); genere = res.getString("genere"); ISBN = res.getString("ISBN"); lingua = res.getString("lingua");
				  anno_pubb = res.getInt("anno_pubb"); num = getNumberPage(tit); idop = res.getInt("ID"); cop = res.getString("pathCopertina");
				  Opera p = new Opera();
				  p.setTitolo(tit); p.setAutore(autore); p.setGenere(genere);
				  p.setISBN(ISBN); p.setLingua(lingua); p.setAnnopubb(anno_pubb);
				  p.setId(idop); p.setPaginanum(num); p.setPathCopertina(cop);
				  if(!(temp.contains(p))) temp.add(p);
			  }
			  listaUp = temp;
		  } catch (SQLException e) {e.printStackTrace(); System.out.println("Errore ricezione Opere senza immagini");}
		  finally {
			   closeDbConnection(res, prep, con);
		   }
		  
		  return listaUp;
	}

	@Override
	public int getNumberPage(String t) {
		int n = 0;
		  try {
			  int p = 0;
			  con = MySQLDAOFactory.createConnection();
			  prep = (PreparedStatement) con.prepareStatement(OPERE_NUMPAGE);
			  prep.setString(1, t);
			  ResultSet res = prep.executeQuery();
			  if (res.next()) { p = res.getInt("npagina");
			  }
			  n = p;
		  } catch (SQLException e) {e.printStackTrace();}
		  finally {
			   closeDbConnection(res, prep, con);
		   }
		  
		  return n;
	}
	
	@Override
	public void CloseUpload(Opera p) {
		 try {
			 con = MySQLDAOFactory.createConnection();
			 prep = (PreparedStatement) con.prepareStatement(CLOSE_UPLOAD);
			 prep.setInt(1, p.getId());
			 prep.executeUpdate();
		 } catch (SQLException exc) {System.out.println("Errore modifica stato opera");}
		 finally {
			   closeDbConnection(res, prep, con);
		   }
	}
	
	@Override
	public List<Opera>getCloseOpera(){
		List<Opera> lista = new ArrayList<Opera>();
		try {
			 List<Opera> temp = new ArrayList<Opera>();
			 con = MySQLDAOFactory.createConnection();
			 prep = (PreparedStatement) con.prepareStatement(OPERE_CLOSE);
			 res = prep.executeQuery();
			 while(res.next()) {
				 Opera p = new Opera();
				 String tit = res.getString("titolo"); String aut = res.getString("autore");
				 int id = res.getInt("ID");
				 p.setTitolo(tit); p.setAutore(aut); p.setId(id);
				 temp.add(p);
			 }
			 lista = temp;
		 } catch (SQLException exc) {System.out.println("Errore modifica stato opera");}
		 finally {
			   closeDbConnection(res, prep, con);
		   }
		return lista;
	}
	
	@Override
	public void PublicOpera(Opera p) {
		
		try {
			con = MySQLDAOFactory.createConnection();
			prep = (PreparedStatement) con.prepareStatement(PUBLIC_OPERA);
			prep.setInt(1, p.getId());
			prep.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		System.out.println("Errore pubblicazione opera DB");}
		finally {
			   closeDbConnection(res, prep, con);
		   }
		
	}

	
		
	public void closeDbConnection(ResultSet rs,  Statement stmt,  Connection conn){
		MySQLDAOFactory.closeDbConnection(rs, stmt, conn);
}

}
