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

import Model.VO.Page;
import Model.VO.Transcription;

public class MySQLTranscriptionDAOImpl implements TranscriptionDAO {
	
	private Connection con = null;
	PreparedStatement prep = null;
	Statement stm = null;
	ResultSet res = null;
	
	private static final String GETASSIGNEDPAGE = "SELECT p.directorytrascrpath, p.directorypagepath, p.numero_pagina, p.ID, o.titolo\r\n" + 
			"FROM pagina p\r\n" + 
			"INNER JOIN trascrizione t ON p.ID = t.IDPagina AND t.IDuploader = ?\r\n" +
			"INNER JOIN opera o ON p.IDopera = o.ID";
	
	@Override
	public List<Page> getAssignedPages(int id){
		List<Page> lista = new ArrayList<Page>();
		try {
			List<Page> temp = new ArrayList<Page>();
			con = MySQLDAOFactory.createConnection();
			prep = (PreparedStatement) con.prepareStatement(GETASSIGNEDPAGE);
			prep.setInt(1, id);
			res = prep.executeQuery();
			while(res.next()) {
				Page ris = new Page();
				ris.setID(res.getInt("ID")); ris.setNumeropagina(res.getInt("numero_pagina"));
				ris.setPath(res.getString("directorypagepath"));
				ris.setTitoloop(res.getString("titolo"));
				Transcription tra = new Transcription();
				tra.setPath(res.getString("directorytrascrpath"));
				ris.setTrascr(tra);
				temp.add(ris);
			}
			lista = temp;
		} catch (SQLException exc) {exc.printStackTrace(); System.out.println("Errore ricezione pagine assegnate");}
		finally {
			   closeDbConnection(res, prep, con);
		   }
		return lista;
	}
	
		
	public void closeDbConnection(ResultSet rs,  Statement stmt,  Connection conn){
		MySQLDAOFactory.closeDbConnection(rs, stmt, conn);
}
	
	

}
