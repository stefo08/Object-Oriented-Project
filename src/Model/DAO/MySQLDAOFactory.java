package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MySQLDAOFactory extends DAOFactory {
	
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/bibliotecaoose";
	
	 public static Connection createConnection() {
	        Connection conn = null;
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            conn = (Connection) DriverManager.getConnection(DBURL, "root", "Angelo1297");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	        	e.printStackTrace();
	        }
	        return conn;
	    } 
	 
	 public static void closeDbConnection(ResultSet rs,  Statement stmt,  Connection conn){
		 
	        try  {
	            if (rs != null)  {
	                rs.close();
	            }
	            if (stmt != null)  {
	                stmt.close();
	            }
	            if (conn != null)  {
	                conn.close();
	            }
	        } catch (SQLException se) {se.printStackTrace();} {
	            	            
	        }
	    }


	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAOImpl();
	}
	
	@Override
	public OperaDAO getOperaDAO() {
		return new MySQLOperaDAOImpl();
	}
	
	@Override 
	public PageDAO getPageDAO() {
		return new MySQLPageDAOImpl();
	}
	
	@Override
	public TranscriptionDAO getTranscriptionDAO() {
		return new MySQLTranscriptionDAOImpl();
	}
	
}
